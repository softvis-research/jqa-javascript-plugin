package org.jqassistant.contrib.plugin.javascript.scanner.listener;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jqassistant.contrib.plugin.javascript.api.model.ArrayDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.ClassDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.CodeArtifact;
import org.jqassistant.contrib.plugin.javascript.api.model.ComplexDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.ContainsPrimitiveDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.FunctionDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.FunctionParameterDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.JavaScriptDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.ObjectDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.PrimitiveDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.UndefinedDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.VariableDescriptor;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.ArgumentsExpressionContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.ArrayLiteralExpressionContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.ArrowFunctionExpressionContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.AssignmentExpressionContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.ClassDeclarationContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.ElementListContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.FormalParameterArgContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.FunctionDeclarationContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.FunctionExpressionContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.LiteralContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.MethodDefinitionContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.ObjectLiteralContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.PropertyExpressionAssignmentContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.VariableDeclarationContext;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParserBaseListener;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers.ArrayStoreHelper;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers.ClassStoreHelper;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers.FunctionStoreHelper;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers.InvokesHelper;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers.ObjectStoreHelper;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers.ParameterStoreHelper;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers.PrimitiveStoreHelper;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers.VariableStoreHelper;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * Sources:
 * 	http://jakubdziworski.github.io/java/2016/04/01/antlr_visitor_vs_listener.html
 *	https://saumitra.me/blog/antlr4-visitor-vs-listener-pattern/
 *
 * @author sh20xyqi
 */

public class JavaScriptListener extends JavaScriptParserBaseListener {

	private Store store;
	private Deque<CodeArtifact> artifactStack = new ArrayDeque<>();
	private Deque<String> keyOrArrayIndex = new ArrayDeque<>();
	private List<JavaScriptDescriptor> artifacts = new ArrayList<>();

	public JavaScriptListener(Store store, CodeArtifact basicComp) {
		super();
		this.store = store;
		artifactStack.push(basicComp);
		artifacts.add(basicComp);
	}
	
	private CodeArtifact amendStack(CodeArtifact c) {
		CodeArtifact belongingComp = artifactStack.getFirst();
		artifactStack.push(c);
		artifacts.add(c);
		return belongingComp;
	}
	
	private void setQualifiedNameFromStacksRootComp(CodeArtifact c, String artifactName) {
		CodeArtifact belongingComp = artifactStack.getFirst();
		setQualifiedName(belongingComp, c, artifactName);
	}
	
	private void setQualifiedName(CodeArtifact rootComp, CodeArtifact c, String artifactName) {
		String fullQualifiedNameRootComp = rootComp.getFullQualifiedName();
		String fqn = new FqnCreator(fullQualifiedNameRootComp).createFqn(artifactName);
		c.setFullQualifiedName(fqn);
	}
	
	@Override
	public void enterVariableDeclaration(VariableDeclarationContext ctx) {
		VariableDescriptor jsVar = new VariableStoreHelper().createNodeIn(store, ctx);
		setQualifiedNameFromStacksRootComp(jsVar, jsVar.getName());
		amendStack(jsVar).getVariables().add(jsVar);
		// check for empty right hand side
		ParseTree rightHandSide = ctx.getChild(2);
		boolean variableIsOnlyDeclared = rightHandSide == null;
		if (variableIsOnlyDeclared) { 
			jsVar.getPrimitives().add(store.create(UndefinedDescriptor.class));
		} 
	}
	
	@Override
	public void exitVariableDeclaration(VariableDeclarationContext ctx) {
		artifactStack.pop();
	}

	@Override
	public void enterFunctionExpression(FunctionExpressionContext ctx) {
		FunctionDescriptor ecmaFunc = new FunctionStoreHelper(Optional.ofNullable(keyOrArrayIndex.poll())).createNodeIn(store, ctx);
		setQualifiedNameFromStacksRootComp(ecmaFunc, ecmaFunc.getName());
		amendStack(ecmaFunc).getFunctions().add(ecmaFunc);
	}
	
	@Override
	public void exitFunctionExpression(FunctionExpressionContext ctx) {
		artifactStack.pop();
	}
	
	@Override
	public void enterArrowFunctionExpression(ArrowFunctionExpressionContext ctx) {
		FunctionDescriptor ecmaFunc = new FunctionStoreHelper(Optional.ofNullable(keyOrArrayIndex.poll())).createNodeIn(store, ctx);
		setQualifiedNameFromStacksRootComp(ecmaFunc, ecmaFunc.getName());
		amendStack(ecmaFunc).getFunctions().add(ecmaFunc);
	}
	
	@Override
	public void exitArrowFunctionExpression(ArrowFunctionExpressionContext ctx) {
		artifactStack.pop();
	}
	
	@Override
	public void enterFunctionDeclaration(FunctionDeclarationContext ctx) {
		String funcName = ctx.children.get(1).getText();
		FunctionDescriptor ecmaFunc = new FunctionStoreHelper(funcName).createNodeIn(store, ctx);
		setQualifiedNameFromStacksRootComp(ecmaFunc, funcName);
		amendStack(ecmaFunc).getFunctions().add(ecmaFunc);
	}
	
	@Override
	public void exitFunctionDeclaration(FunctionDeclarationContext ctx) {
		artifactStack.pop();
	}
	
	
	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		ClassDescriptor ecmaClass = new ClassStoreHelper().createNodeIn(store, ctx);
		setQualifiedNameFromStacksRootComp(ecmaClass, ecmaClass.getName());
		amendStack(ecmaClass).getClasses().add(ecmaClass);
	}
	
	@Override
	public void exitClassDeclaration(ClassDeclarationContext ctx) {
		artifactStack.pop();
	}
	
	@Override
	public void enterMethodDefinition(MethodDefinitionContext ctx) {
		String funcName = ctx.children.get(0).getChild(0).getText();
		FunctionDescriptor ecmaFunc = new FunctionStoreHelper(funcName).createNodeIn(store, ctx);
		setQualifiedNameFromStacksRootComp(ecmaFunc, ecmaFunc.getName());
		amendStack(ecmaFunc).getFunctions().add(ecmaFunc);
	}
	
	@Override
	public void exitMethodDefinition(MethodDefinitionContext ctx) {
		artifactStack.pop();
	}
	
	private FunctionDescriptor getLastDefinedFunction() {
		// https://stackoverflow.com/questions/24010109/java-8-stream-reverse-order
		ListIterator<JavaScriptDescriptor> listIterator = artifacts.listIterator(artifacts.size());
		return Stream.generate(listIterator::previous)
			      .limit(artifacts.size())
			      .filter((e) -> e instanceof FunctionDescriptor)
			      .map((e) -> (FunctionDescriptor)e)
			      .findFirst()
			      .orElseThrow();
		
			      
	}
	
	private Long countLastParameters() {
		// https://stackoverflow.com/questions/24010109/java-8-stream-reverse-order
		ListIterator<JavaScriptDescriptor> listIterator = artifacts.listIterator(artifacts.size());
		return Stream.generate(listIterator::previous)
			      .limit(artifacts.size())
			      .takeWhile((desc) -> desc instanceof FunctionParameterDescriptor) // JAVA 9
			      .collect(Collectors.counting());
		
			      
	}
	
	@Override
	public void enterFormalParameterArg(FormalParameterArgContext ctx) {
		int countParamtersBefore = countLastParameters().intValue();
		FunctionDescriptor function = getLastDefinedFunction();
		FunctionParameterDescriptor ecmaParam = new ParameterStoreHelper(countParamtersBefore).createNodeIn(store, ctx);
		setQualifiedName(function, ecmaParam, ecmaParam.getName());
		amendStack(ecmaParam);
		function.getParameters().add(ecmaParam);
	}
	
	@Override
	public void exitFormalParameterArg(FormalParameterArgContext ctx) {
		artifactStack.pop();
	}
	
	@Override
	public void enterLiteral(LiteralContext ctx) {
		CodeArtifact first = artifactStack.getFirst();
		if(first instanceof VariableDescriptor) {
			VariableDescriptor varDesc = (VariableDescriptor) first;
			createPrimitiveDescriptor(ctx, first.getName(), varDesc);
		} else if(first instanceof ComplexDescriptor) {
			createPrimitiveDescriptor(ctx, keyOrArrayIndex.pop(), (ContainsPrimitiveDescriptor) first);
		}
	}

	private void createPrimitiveDescriptor(LiteralContext ctx, String name, ContainsPrimitiveDescriptor varDesc) {
		PrimitiveDescriptor<?> to = new PrimitiveStoreHelper().createNodeIn(store, ctx);
		Optional.of(to).map((PrimitiveDescriptor<?> d) -> {
			// add literal if available and set stuff on it globally
			varDesc.getPrimitives().add(d);
			d.setLine(ctx.getStart().getLine());
			d.setName(name);
			d.setFullQualifiedName(new FqnCreator(name).createFqn(Integer.toString(d.hashCode())));
			return d;
		});
	}
	
	@Override
	public void enterObjectLiteral(ObjectLiteralContext ctx) {
		ObjectDescriptor ecmaObj = new ObjectStoreHelper().createNodeIn(store, ctx);
		CodeArtifact first = artifactStack.getFirst();
		if(first instanceof VariableDescriptor) {
			VariableDescriptor var = (VariableDescriptor) first;
			ecmaObj.setName(var.getName());
		} else if(first instanceof ComplexDescriptor) {
			ecmaObj.setName(keyOrArrayIndex.pop());
		}
		setQualifiedNameFromStacksRootComp(ecmaObj, ecmaObj.getName());
		amendStack(ecmaObj).getObjects().add(ecmaObj);
	}
	
	@Override
	public void enterPropertyExpressionAssignment(PropertyExpressionAssignmentContext ctx) {
		String proName = ctx.getChild(0).getText();
		keyOrArrayIndex.push(proName);
	}
	
	@Override
	public void exitPropertyExpressionAssignment(PropertyExpressionAssignmentContext ctx) {
		//keyOrArrayIndex.();
	}
	
	@Override
	public void exitObjectLiteral(ObjectLiteralContext ctx) {
		artifactStack.pop();
	}
	
	public void enterArrayLiteralExpression(ArrayLiteralExpressionContext ctx) {
		ArrayDescriptor ecmaArray = new ArrayStoreHelper().createNodeIn(store, ctx);
		CodeArtifact first = artifactStack.getFirst();
		if(first instanceof VariableDescriptor) {
			VariableDescriptor var = (VariableDescriptor) first;
			ecmaArray.setName(var.getName());
		} else if(first instanceof ComplexDescriptor) {
			ecmaArray.setName(keyOrArrayIndex.pop());
		}
		setQualifiedNameFromStacksRootComp(ecmaArray, ecmaArray.getName());
		amendStack(ecmaArray).getObjects().add(ecmaArray);

	}
	
	@Override
	public void enterElementList(ElementListContext ctx) {
		int countChildren = ctx.getChildCount() / 2;
		countChildren = countChildren == 0 ? ctx.getChildCount() : countChildren;
		IntStream.range(0, countChildren + 1)
				 .mapToObj(Integer::toString)
				 .forEach(keyOrArrayIndex::push);
	}
	
	@Override
	public void exitArrayLiteralExpression(ArrayLiteralExpressionContext ctx) {
		artifactStack.pop();
	}
	
	@Override
	public void enterAssignmentExpression(AssignmentExpressionContext ctx) {
		CodeArtifact rootComp = artifactStack.getFirst();
		if (rootComp instanceof FunctionDescriptor) {
			String methodName = ((FunctionDescriptor) rootComp).getName();
			if (FunctionStoreHelper.CONSTRUCTOR.equals(methodName)) {
				String memberVar = ctx.getChild(0).getChild(2).getChild(0).getText();
				VariableDescriptor field = new VariableStoreHelper().createVar(store, memberVar, ctx.getStart().getLine());
				amendStack(field).getVariables().add(field);
			} 
		} 
	}
	
	@Override
	public void exitAssignmentExpression(AssignmentExpressionContext ctx) {
		Iterator<CodeArtifact> it = artifactStack.iterator();
		if(it.hasNext()) {
			CodeArtifact possibleVar = it.next();
			if(it.hasNext()) {
				CodeArtifact possibleConstructor = it.next();
				boolean assignedFields = possibleConstructor.getFullQualifiedName().endsWith(FunctionStoreHelper.CONSTRUCTOR) && possibleVar instanceof VariableDescriptor;
				if(assignedFields) {
					artifactStack.pop();
				}
			}
		}
	}
	
	public void enterArgumentsExpression(ArgumentsExpressionContext ctx) {
		ParseTree subAst = ctx.getChild(0).getChild(0);
		if(subAst instanceof TerminalNode) {
			String funcName = subAst.toString();
			switch(funcName) {
				// anonymous function gets invoked
				case "(":
				case "function":
					break;
				default:
				createRelationInvokes(ctx, funcName);
			}
		}
	
	}
	
	
	private void createRelationInvokes(ArgumentsExpressionContext ctx, String funcName) {
		// normal function from var or global name has been invoked
			Optional<FunctionDescriptor> funcInStore = findFunctioninStore(funcName);
			FunctionDescriptor func = funcInStore.orElseGet(() -> {
				FunctionDescriptor fd = store.create(FunctionDescriptor.class);
				fd.setFullQualifiedName(funcName);
				fd.setLine(ctx.getStart().getLine());
				fd.setName(funcName);
				return fd;
			});
			CodeArtifact rootComp = artifactStack.getFirst();
			new InvokesHelper(store).createRelation(ctx, rootComp , func);
	}

	public Optional<FunctionDescriptor> findFunctioninStore(String funcName) {
		FqnCreator fqnCreator = new FqnCreator(artifactStack.getFirst().getFullQualifiedName());
		Stream<String> simpleFuncNames = fqnCreator.getAllFqnsFrom(funcName).stream();
		Stream<String> constructorFuncNames = fqnCreator.getAllFqnsFrom(fqnCreator.createFqnFrom(funcName, FunctionStoreHelper.CONSTRUCTOR)).stream();
		Stream<String> anonymousFuncNames = fqnCreator.getAllFqnsFrom(fqnCreator.createFqnFrom(funcName, FunctionStoreHelper.FUNC_ANONYMOUS_NAME)).stream();
		Stream<String> fqnTries = Stream.concat(Stream.concat(simpleFuncNames, anonymousFuncNames), constructorFuncNames);
		return fqnTries
				  .map((String s) ->  Optional.ofNullable(store.find(FunctionDescriptor.class, s)))
				  .filter(Optional::isPresent)
				  .findFirst().orElse(Optional.empty());
	}
	
}
