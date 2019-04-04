package org.jqassistant.contrib.plugin.ecmascript.scanner.visitor;

import java.util.Optional;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.ArrayLiteralExpressionContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.ArrowFunctionExpressionContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.AssignmentExpressionContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.ClassDeclarationContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.FormalParameterListContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.FunctionDeclarationContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.FunctionExpressionContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.LiteralContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.MethodDefinitionContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.NumericLiteralContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.ObjectLiteralContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.VariableDeclarationContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParserBaseVisitor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptArrayDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptBasicComponents;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptBooleanDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptClassDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptFunctionDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptFunctionParameterDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptLiteralDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptNullDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptNumberDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptObjectDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptStringDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptUndefinedDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptVariableDescriptor;

import com.buschmais.jqassistant.core.store.api.Store;

@SuppressWarnings("rawtypes")
public class JQAssistantECMAScriptVistor extends JavaScriptParserBaseVisitor<JQAssistantECMAScriptVistor> {

	private Store store;
	private String fqnBase;
	private ECMAScriptBasicComponents basicComp;
	private Optional<StoreRelationManipulator<?>> relationCreator;

	public JQAssistantECMAScriptVistor(Store store, String fqnBase, ECMAScriptBasicComponents basicComp) {
		this(store, fqnBase, basicComp, null);
	}

	public JQAssistantECMAScriptVistor(Store store, String fqnBase, ECMAScriptBasicComponents basicComp,
			StoreRelationManipulator<?> relationCreator) {
		super();
		this.store = store;
		this.fqnBase = fqnBase;
		this.basicComp = basicComp;
		this.relationCreator = Optional.ofNullable(relationCreator);
	}

	@Override
	public JQAssistantECMAScriptVistor visitVariableDeclaration(VariableDeclarationContext ctx) {
		// step one: get data from AST
		String varName = ctx.children.get(0).getText();
		// step two: create Variable
		ECMAScriptVariableDescriptor ecmaSVD = createVar(varName, ctx.getStart().getLine());
		// step three: run new visitor in sub-AST
		ParseTree rightHandSide = ctx.getChild(2);
		JQAssistantECMAScriptVistor subTreeVistor = new JQAssistantECMAScriptVistor(store, fqnBase + "::" + varName,
				ecmaSVD);
		if (rightHandSide == null) {
			ecmaSVD.getLiterals().add(store.create(ECMAScriptUndefinedDescriptor.class));
		} else {
			rightHandSide.accept(subTreeVistor);
		}
		return null;
	}

	private ECMAScriptVariableDescriptor createVar(String varName, int line) {
		String fqn = createFqn(varName);
		ECMAScriptVariableDescriptor ecmaVar = store.create(ECMAScriptVariableDescriptor.class);
		ecmaVar.setFullQualifiedName(fqn);
		ecmaVar.setName(varName);
		ecmaVar.setLine(line);
		basicComp.getVariables().add(ecmaVar);
		return ecmaVar;
	}

	String createFqn(String varName) {
		String fqn = String.format("%s::%s", fqnBase, varName);
		return fqn;
	}

	@Override
	public JQAssistantECMAScriptVistor visitFunctionExpression(FunctionExpressionContext ctx) {
		ctx.functionBody().accept(visitAnonymousFunction(ctx));
		return null;
	}

	private JQAssistantECMAScriptVistor visitAnonymousFunction(ParserRuleContext ctx) {
		ECMAScriptFunctionDescriptor ecmaFunc = store.create(ECMAScriptFunctionDescriptor.class);
		String funcName = "<anonymous>";
		ecmaFunc.setFullQualifiedName(createFqn(funcName));
		ecmaFunc.setName(funcName);
		int line = ctx.start.getLine();
		ecmaFunc.setLine(line);
		basicComp.getFunctions().add(ecmaFunc);
		JQAssistantECMAScriptVistor subTreeVistor = new JQAssistantECMAScriptVistor(store, fqnBase + "::" + funcName,
				ecmaFunc);
		relationCreator.map((a) -> a.createRelation(ctx, basicComp, ecmaFunc));
		return subTreeVistor;
	}

	@Override
	public JQAssistantECMAScriptVistor visitArrowFunctionExpression(ArrowFunctionExpressionContext ctx) {
		ctx.arrowFunctionBody().accept(visitAnonymousFunction(ctx));
		return null;
	}

	@Override
	public JQAssistantECMAScriptVistor visitFormalParameterList(FormalParameterListContext ctx) {
		for (int i = 0; i < ctx.children.size(); i++) {
			if (0 == i % 2) {
				ParseTree child = ctx.children.get(i);
				ECMAScriptFunctionParameterDescriptor ecmaParam = store
						.create(ECMAScriptFunctionParameterDescriptor.class);
				String funcName = child.getChild(0).getText();
				ecmaParam.setFullQualifiedName(createFqn(funcName));
				ecmaParam.setName(funcName);
				ecmaParam.setIndex(i / 2);
				ecmaParam.setLine(ctx.getStart().getLine());
				((ECMAScriptFunctionDescriptor) basicComp).getParameters().add(ecmaParam);
				JQAssistantECMAScriptVistor subTreeVistor = new JQAssistantECMAScriptVistor(store,
						fqnBase + "::" + funcName, ecmaParam);
				child.accept(subTreeVistor);
			}
		}
		return null;
	}

	@Override
	public JQAssistantECMAScriptVistor visitLiteral(LiteralContext ctx) {
		ParseTree literal = ctx.getChild(0);
		String text = ctx.getText();
		Optional<ECMAScriptLiteralDescriptor<?>> to = Optional.empty();
		if (literal instanceof NumericLiteralContext) {
			ECMAScriptNumberDescriptor number = store.create(ECMAScriptNumberDescriptor.class);
			number.setValue(Double.parseDouble(text));
			basicComp.getLiterals().add(number);
			to = Optional.of(number);
		} else if (literal instanceof TerminalNodeImpl) {
			if ("true".equals(text)) {
				ECMAScriptBooleanDescriptor t = store.create(ECMAScriptBooleanDescriptor.class);
				t.setValue(false);
				basicComp.getLiterals().add(t);
				to = Optional.of(t);
			} else if ("false".equals(text)) {
				ECMAScriptBooleanDescriptor f = store.create(ECMAScriptBooleanDescriptor.class);
				f.setValue(false);
				basicComp.getLiterals().add(f);
				to = Optional.of(f);
			} else if ("null".equals(text)) {
				ECMAScriptNullDescriptor f = store.create(ECMAScriptNullDescriptor.class);
				basicComp.getLiterals().add(f);
				to = Optional.of(f);
			} else {
				ECMAScriptStringDescriptor string = store.create(ECMAScriptStringDescriptor.class);
				String value = text.substring(1, text.length() - 1);
				string.setValue(value);
				basicComp.getLiterals().add(string);
				to = Optional.of(string);
			}
		} else {
			System.out.println(literal.getClass());

		}
		to.map((ECMAScriptLiteralDescriptor d) -> {
			d.setLine(ctx.getStart().getLine());
			d.setFullQualifiedName(createFqn(Integer.toString(d.hashCode())));
			return relationCreator.map((a) -> a.createRelation(ctx, basicComp, d));
		});

		return super.visitLiteral(ctx);
	}

	@Override
	public JQAssistantECMAScriptVistor visitObjectLiteral(ObjectLiteralContext ctx) {
		ECMAScriptObjectDescriptor ecmaObj = store.create(ECMAScriptObjectDescriptor.class);
		String funcName = ECMAScriptObjectDescriptor.OBJECT;
		ecmaObj.setFullQualifiedName(createFqn(funcName));
		basicComp.getObjects().add(ecmaObj);
		ecmaObj.setLine(ctx.getStart().getLine());
		relationCreator.map((a) -> a.createRelation(ctx, basicComp, ecmaObj));
		JQAssistantECMAScriptVistor subTreeVistor = new JQAssistantECMAScriptVistor(store, fqnBase + "::" + funcName,
				ecmaObj, new ObjectDeclaresManipulator(store));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return null;
	}

	@Override
	public JQAssistantECMAScriptVistor visitArrayLiteralExpression(ArrayLiteralExpressionContext ctx) {
		ECMAScriptArrayDescriptor ecmaArray = store.create(ECMAScriptArrayDescriptor.class);
		String funcName = ECMAScriptArrayDescriptor.ARRAY;
		ecmaArray.setFullQualifiedName(createFqn(funcName));
		basicComp.getObjects().add(ecmaArray);
		ecmaArray.setLine(ctx.getStart().getLine());
		relationCreator.map((a) -> a.createRelation(ctx, basicComp, ecmaArray));

		JQAssistantECMAScriptVistor subTreeVistor = new JQAssistantECMAScriptVistor(store, fqnBase + "::" + funcName,
				ecmaArray, new ArrayDeclaresManipulator(store));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return null;
	}

	@Override
	public JQAssistantECMAScriptVistor visitFunctionDeclaration(FunctionDeclarationContext ctx) {
		ECMAScriptFunctionDescriptor ecmaFunc = store.create(ECMAScriptFunctionDescriptor.class);
		String funcName = ctx.children.get(1).getText();
		ecmaFunc.setFullQualifiedName(createFqn(funcName));
		ecmaFunc.setName(funcName);
		ecmaFunc.setLine(ctx.getStart().getLine());
		basicComp.getFunctions().add(ecmaFunc);
		JQAssistantECMAScriptVistor subTreeVistor = new JQAssistantECMAScriptVistor(store, fqnBase + "::" + funcName,
				ecmaFunc);
		relationCreator.map((a) -> a.createRelation(ctx, basicComp, ecmaFunc));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return null;
	}

	@Override
	public JQAssistantECMAScriptVistor visitClassDeclaration(ClassDeclarationContext ctx) {
		ECMAScriptClassDescriptor ecmaClass = store.create(ECMAScriptClassDescriptor.class);
		String className = ctx.children.get(1).getText();
		ecmaClass.setFullQualifiedName(createFqn(className));
		ecmaClass.setName(className);
		ecmaClass.setLine(ctx.getStart().getLine());
		basicComp.getClasses().add(ecmaClass);
		JQAssistantECMAScriptVistor subTreeVistor = new JQAssistantECMAScriptVistor(store, fqnBase + "::" + className,
				ecmaClass);
		relationCreator.map((a) -> a.createRelation(ctx, basicComp, ecmaClass));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));

		ParseTree classBody = ctx.children.get(2);
		if ("extends".equals(classBody.getChild(0).getText())) {
			String superClassName = classBody.getChild(1).getChild(0).getText();
			ecmaClass.setSuperClass(superClassName);
		}
		return null;
	}

	@Override
	public JQAssistantECMAScriptVistor visitAssignmentExpression(AssignmentExpressionContext ctx) {
		if (basicComp instanceof ECMAScriptFunctionDescriptor) {
			String methodName = ((ECMAScriptFunctionDescriptor) basicComp).getName();

			if ("constructor".equals(methodName)) {
				// if rhs
				// if MemberDotExpressionContxt

				String memberVar = ctx.getChild(0).getChild(2).getChild(0).getText();
				createVar(memberVar, ctx.getStart().getLine());
				return null;
			} else {
				return super.visitAssignmentExpression(ctx);
			}
		} else {
			return super.visitAssignmentExpression(ctx);
		}

	}

	@Override
	public JQAssistantECMAScriptVistor visitMethodDefinition(MethodDefinitionContext ctx) {
		ECMAScriptFunctionDescriptor ecmaFunc = store.create(ECMAScriptFunctionDescriptor.class);
		String funcName = ctx.children.get(0).getChild(0).getText();
		ecmaFunc.setFullQualifiedName(createFqn(funcName));
		ecmaFunc.setName(funcName);
		ecmaFunc.setLine(ctx.getStart().getLine());
		basicComp.getFunctions().add(ecmaFunc);
		JQAssistantECMAScriptVistor subTreeVistor = new JQAssistantECMAScriptVistor(store, fqnBase + "::" + funcName,
				ecmaFunc);
		relationCreator.map((a) -> a.createRelation(ctx, basicComp, ecmaFunc));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return null;
	}

}
