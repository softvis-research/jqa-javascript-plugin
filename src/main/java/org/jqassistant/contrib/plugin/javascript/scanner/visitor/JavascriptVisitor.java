package org.jqassistant.contrib.plugin.javascript.scanner.visitor;

import java.util.Optional;
import java.util.stream.Stream;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.ArgumentsExpressionContext;
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
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.ArrayStoreManipulator;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.ClassStoreManipulator;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.FunctionStoreManipulator;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.LiteralStoreManipulator;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.ObjectDeclaresManipulator;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.ObjectStoreManipulator;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.ParameterStoreManipulator;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.StoreRelationManipulator;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators.VariableStoreManipulator;
import org.jruby.ext.ffi.FFIService;

import com.buschmais.jqassistant.core.store.api.Store;

@SuppressWarnings("rawtypes")
public class JavascriptVisitor extends JavaScriptParserBaseVisitor<Void> {

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
		System.out.println("FQN: " + fqnBase);
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
		// run new visitor in sub-AST
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
		return new FqnCreator(fqnBase).createFqn(varName);
	}

	@Override
	public Void visitFunctionExpression(FunctionExpressionContext ctx) {
		FunctionDescriptor ecmaFunc = new FunctionStoreManipulator().createNodeIn(store, ctx, fqnCreator);
		rootComp.getFunctions().add(ecmaFunc);
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaFunc));
		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store, createFqn(ecmaFunc.getName()), ecmaFunc);
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}

 	@Override
	public Void visitArrowFunctionExpression(ArrowFunctionExpressionContext ctx) {
		FunctionDescriptor ecmaFunc = new FunctionStoreManipulator().createNodeIn(store, ctx, fqnCreator);
		rootComp.getFunctions().add(ecmaFunc);
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaFunc));
		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store, createFqn(ecmaFunc.getName()), ecmaFunc);
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}
 	
	@Override
	public Void visitFunctionDeclaration(FunctionDeclarationContext ctx) {
		String funcName = ctx.children.get(1).getText();
		FunctionDescriptor ecmaFunc = new FunctionStoreManipulator(funcName).createNodeIn(store, ctx, fqnCreator);
		rootComp.getFunctions().add(ecmaFunc);
		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store, createFqn(funcName), ecmaFunc);
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaFunc));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}


	@Override
	public Void visitMethodDefinition(MethodDefinitionContext ctx) {
		String funcName = ctx.children.get(0).getChild(0).getText();
		FunctionDescriptor ecmaFunc = new FunctionStoreManipulator(funcName).createNodeIn(store, ctx, fqnCreator);
		rootComp.getFunctions().add(ecmaFunc);
		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store, createFqn(funcName), ecmaFunc);
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaFunc));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}
	
	@Override
	public Void visitFormalParameterList(FormalParameterListContext ctx) {
		for (int i = 0; i < ctx.children.size(); i++) {
			if (0 == i % 2) {
				ParseTree child = ctx.children.get(i);
				FunctionParameterDescriptor ecmaParam = new ParameterStoreManipulator(i).createNodeIn(store, ctx, fqnCreator);
				((FunctionDescriptor) rootComp).getParameters().add(ecmaParam);
				JavascriptVisitor subTreeVistor = new JavascriptVisitor(store,  fqnCreator.createFqn(ecmaParam.getName()), ecmaParam);
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
		ObjectDescriptor ecmaObj = new ObjectStoreManipulator().createNodeIn(store, ctx, fqnCreator);
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaObj));
		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store,  fqnCreator.createFqn(ObjectDescriptor.OBJECT), ecmaObj, new ObjectDeclaresManipulator(store));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}

	@Override
	public Void visitArrayLiteralExpression(ArrayLiteralExpressionContext ctx) {
		ArrayDescriptor ecmaArray = new ArrayStoreManipulator().createNodeIn(store, ctx, fqnCreator);
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaArray));
		rootComp.getObjects().add(ecmaArray);

		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store, fqnCreator.createFqn(ArrayDescriptor.ARRAY), ecmaArray, new ArrayDeclaresManipulator(store));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}

	@Override
	public Void visitClassDeclaration(ClassDeclarationContext ctx) {
		ClassDescriptor ecmaClass = new ClassStoreManipulator().createNodeIn(store, ctx, fqnCreator);
		rootComp.getClasses().add(ecmaClass);
		JavascriptVisitor subTreeVistor = new JavascriptVisitor(store, fqnCreator.createFqn(ecmaClass.getName()), ecmaClass);
		relationCreator.map((a) -> a.createRelation(ctx, rootComp, ecmaClass));
		ctx.children.forEach((c) -> c.accept(subTreeVistor));
		return VISITOR_DOES_NOT_GO_DEEPER_INTO_AST;
	}

	@Override
	public Void visitAssignmentExpression(AssignmentExpressionContext ctx) {
		if (rootComp instanceof FunctionDescriptor) {
			String methodName = ((FunctionDescriptor) rootComp).getName();
			if ("constructor".equals(methodName)) {
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
	public Void visitArgumentsExpression(ArgumentsExpressionContext ctx) {
		ParseTree subAst = ctx.getChild(0).getChild(0);
		if(subAst instanceof TerminalNode) {
			String funcName = subAst.toString();
			switch(funcName) {
				// anonymous function gets invoked
				case "(":
				case "function":
					break;
					
				default:
				// normal function from var or global name has been invoked
					Optional<FunctionDescriptor> funcInStore = findFunctioninStore(funcName);
					FunctionDescriptor func = funcInStore.orElseGet(() -> {
						FunctionDescriptor fd = store.create(FunctionDescriptor.class);
						fd.setLine(ctx.getStart().getLine());
						fd.setName(funcName);
						return fd;
					});
					rootComp.getInvokes().add(func);
					System.out.println(funcName);
			}
		}
	
		return super.visitArgumentsExpression(ctx);
	}

	public Optional<FunctionDescriptor> findFunctioninStore(String funcName) {
		Stream<String> simpleFuncNames = fqnCreator.getAllFqnsFrom(funcName).stream();
		Stream<String> anonymousFuncNames = fqnCreator.getAllFqnsFrom(fqnCreator.createFqnFrom(funcName, FunctionStoreManipulator.FUNC_ANONYMOUS_NAME)).stream();
		Stream<String> fqnTries = Stream.concat(simpleFuncNames, anonymousFuncNames );
		return fqnTries
				  .map((String s) ->  Optional.ofNullable(store.find(FunctionDescriptor.class, s)))
				  .filter((Optional<FunctionDescriptor> curFd) -> curFd.isPresent())
				  .findFirst().orElse(Optional.empty());
				 
		
	}
	

}
