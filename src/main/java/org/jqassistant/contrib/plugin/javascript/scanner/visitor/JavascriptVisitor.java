package org.jqassistant.contrib.plugin.javascript.scanner.visitor;

import java.util.Optional;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.ArrayLiteralExpressionContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.ArrowFunctionExpressionContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.AssignmentExpressionContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.ClassDeclarationContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.FormalParameterListContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.FunctionDeclarationContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.FunctionExpressionContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.LiteralContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.MethodDefinitionContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.ObjectLiteralContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.VariableDeclarationContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParserBaseVisitor;
import org.jqassistant.contrib.plugin.javascript.api.model.ArrayDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.ClassDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.CodeArtifact;
import org.jqassistant.contrib.plugin.javascript.api.model.FunctionDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.FunctionParameterDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.LiteralDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.ObjectDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.UndefinedDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.VariableDescriptor;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.ArrayDeclaresManipulator;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.LiteralStoreManipulator;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.ObjectDeclaresManipulator;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.StoreRelationManipulator;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.VariableStoreManipulator;

import com.buschmais.jqassistant.core.store.api.Store;

@SuppressWarnings("rawtypes")
public class JavascriptVisitor extends JavaScriptParserBaseVisitor<Void> {

	private static final String FQN_SEPARATOR = ":";

	// this is an alias for null to verbalize that a visitor does not go deeper in
	// hierarchy
	private static Void VISITOR_DOES_NOT_GO_DEEPER_INTO_AST = null;

	private Store store;
	private String fqnBase;
	private FqnCreator fqnCreator;
	private CodeArtifact rootComp;
	private Optional<StoreRelationManipulator<?>> relationCreator;

	public JavascriptVisitor(Store store, String fqnBase, CodeArtifact basicComp) {
		this(store, fqnBase, basicComp, null);
	}

	public JavascriptVisitor(Store store, String fqnBase, CodeArtifact basicComp, StoreRelationManipulator<?> relationCreator) {
		super();
		this.store = store;
		this.fqnBase = fqnBase;
		this.rootComp = basicComp;
		this.relationCreator = Optional.ofNullable(relationCreator);
		this.fqnCreator = new FqnCreator(fqnBase);
	}

	@Override
	public Void visitVariableDeclaration(VariableDeclarationContext ctx) {
		VariableDescriptor jsVar = new VariableStoreManipulator().createNodeIn(store, ctx, fqnCreator);
		rootComp.getVariables().add(jsVar);
		// step three: run new visitor in sub-AST
		ParseTree rightHandSide = ctx.getChild(2);
		boolean variableIsOnlyDeclared = rightHandSide == null;
		if (variableIsOnlyDeclared) { 
			jsVar.getLiterals().add(store.create(UndefinedDescriptor.class));
		}  else {
			rightHandSide.accept(new JavascriptVisitor(store, fqnCreator.createFqn(jsVar.getName()), jsVar));
			 
		}
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}


	String createFqn(String varName) {
		String fqn = String.format("%s%s%s", fqnBase, FQN_SEPARATOR, varName);
		return fqn;
	}

	@Override
	public Void visitFunctionExpression(FunctionExpressionContext ctx) {
		ctx.functionBody().accept(visitAnonymousFunction(ctx));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}

	private JavascriptVisitor visitAnonymousFunction(ParserRuleContext ctx) {
		FunctionDescriptor ecmaFunc = store.create(FunctionDescriptor.class);
		String funcName = "<anonymous>";
		ecmaFunc.setName(funcName);
		int line = ctx.start.getLine();
		ecmaFunc.setLine(line);
		rootComp.getFunctions().add(ecmaFunc);
		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store,  createFqn(funcName), ecmaFunc);
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaFunc));
		return subTreeVistor;
	}

	@Override
	public Void visitArrowFunctionExpression(ArrowFunctionExpressionContext ctx) {
		ctx.arrowFunctionBody().accept(visitAnonymousFunction(ctx));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}

	@Override
	public Void visitFormalParameterList(FormalParameterListContext ctx) {
		for (int i = 0; i < ctx.children.size(); i++) {
			if (0 == i % 2) {
				ParseTree child = ctx.children.get(i);
				FunctionParameterDescriptor ecmaParam = store.create(FunctionParameterDescriptor.class);
				String funcName = child.getChild(0).getText();
				ecmaParam.setFullQualifiedName(createFqn(funcName));
				ecmaParam.setName(funcName);
				ecmaParam.setIndex(i / 2);
				ecmaParam.setLine(ctx.getStart().getLine());
				((FunctionDescriptor) rootComp).getParameters().add(ecmaParam);
				JavascriptVisitor subTreeVistor = new JavascriptVisitor(store,  createFqn(funcName), ecmaParam);
				child.accept(subTreeVistor);
			}
		}
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}

	@Override
	public Void visitLiteral(LiteralContext ctx) {
		LiteralDescriptor<?> to = new LiteralStoreManipulator().createNodeIn(store, ctx, fqnCreator);
		Optional.of(to).map((LiteralDescriptor d) -> {
			// add literal if available and set stuff on it globally
			rootComp.getLiterals().add(d);
			d.setLine(ctx.getStart().getLine());
			d.setFullQualifiedName(createFqn(Integer.toString(d.hashCode())));
			// set relation
			return relationCreator.map((a) -> a.createRelation(ctx, rootComp, d));
		});

		return super.visitLiteral(ctx);
	}

	@Override
	public Void visitObjectLiteral(ObjectLiteralContext ctx) {
		ObjectDescriptor ecmaObj = store.create(ObjectDescriptor.class);
		String objName = ObjectDescriptor.OBJECT;
		ecmaObj.setFullQualifiedName(createFqn(objName));
		rootComp.getObjects().add(ecmaObj);
		ecmaObj.setLine(ctx.getStart().getLine());
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaObj));
		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store,  createFqn(objName), ecmaObj,
				new ObjectDeclaresManipulator(store));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}

	@Override
	public Void visitArrayLiteralExpression(ArrayLiteralExpressionContext ctx) {
		ArrayDescriptor ecmaArray = store.create(ArrayDescriptor.class);
		String funcName = ArrayDescriptor.ARRAY;
		ecmaArray.setFullQualifiedName(createFqn(funcName));
		rootComp.getObjects().add(ecmaArray);
		ecmaArray.setLine(ctx.getStart().getLine());
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaArray));

		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store, createFqn(funcName), ecmaArray,
				new ArrayDeclaresManipulator(store));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}

	@Override
	public Void visitFunctionDeclaration(FunctionDeclarationContext ctx) {
		FunctionDescriptor ecmaFunc = store.create(FunctionDescriptor.class);
		String funcName = ctx.children.get(1).getText();
		ecmaFunc.setFullQualifiedName(createFqn(funcName));
		ecmaFunc.setName(funcName);
		ecmaFunc.setLine(ctx.getStart().getLine());
		rootComp.getFunctions().add(ecmaFunc);
		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store, createFqn(funcName), ecmaFunc);
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaFunc));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}

	@Override
	public Void visitClassDeclaration(ClassDeclarationContext ctx) {
		ClassDescriptor ecmaClass = store.create(ClassDescriptor.class);
		String className = ctx.children.get(1).getText();
		ecmaClass.setFullQualifiedName(createFqn(className));
		ecmaClass.setName(className);
		ecmaClass.setLine(ctx.getStart().getLine());
		rootComp.getClasses().add(ecmaClass);
		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store, createFqn(className), ecmaClass);
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaClass));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));

		ParseTree classBody = ctx.children.get(2);
		if ("extends".equals(classBody.getChild(0).getText())) {
			String superClassName = classBody.getChild(1).getChild(0).getText();
			ecmaClass.setSuperClass(superClassName);
		}
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}

	@Override
	public Void visitAssignmentExpression(AssignmentExpressionContext ctx) {
		if (rootComp instanceof FunctionDescriptor) {
			String methodName = ((FunctionDescriptor) rootComp).getName();

			if ("constructor".equals(methodName)) {
				// if rhs
				// if MemberDotExpressionContxt

				String memberVar = ctx.getChild(0).getChild(2).getChild(0).getText();
				new VariableStoreManipulator().createVar(store, memberVar, ctx.getStart().getLine(), fqnCreator);
				return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
			} else {
				return super.visitAssignmentExpression(ctx);
			}
		} else {
			return super.visitAssignmentExpression(ctx);
		}

	}

	@Override
	public Void visitMethodDefinition(MethodDefinitionContext ctx) {
		FunctionDescriptor ecmaFunc = store.create(FunctionDescriptor.class);
		String funcName = ctx.children.get(0).getChild(0).getText();
		ecmaFunc.setFullQualifiedName(createFqn(funcName));
		ecmaFunc.setName(funcName);
		ecmaFunc.setLine(ctx.getStart().getLine());
		rootComp.getFunctions().add(ecmaFunc);
		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store, createFqn(funcName), ecmaFunc);
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaFunc));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}

}
