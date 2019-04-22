package org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.VariableDeclarationContext;
import org.jqassistant.contrib.plugin.javascript.api.model.UndefinedDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.VariableDescriptor;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.FqnCreator;

import com.buschmais.jqassistant.core.store.api.Store;

public class FunctionStoreManipulator implements NodeStoreManipulator<VariableDescriptor, VariableDeclarationContext> {

	@Override
	public VariableDescriptor createNodeIn(Store store, VariableDeclarationContext ctx, FqnCreator fqnCreator) {
		// step one: get data from AST
		String varName = ctx.children.get(0).getText();
		// step two: create Variable
		VariableDescriptor jsVar = createVar(store, varName, ctx.getStart().getLine(), fqnCreator);
		// step three: run new visitor in sub-AST
		ParseTree rightHandSide = ctx.getChild(2);
		boolean variableIsOnlyDeclared = rightHandSide == null;
		if (variableIsOnlyDeclared) { 
			jsVar.getLiterals().add(store.create(UndefinedDescriptor.class));
		}
		return jsVar;
	}

	public VariableDescriptor createVar(Store store, String varName, int line, FqnCreator fqn) {
		VariableDescriptor jsVar = store.create(VariableDescriptor.class);
		jsVar.setFullQualifiedName(fqn.createFQNFor(varName));
		jsVar.setName(varName);
		jsVar.setLine(line);
		return jsVar;
	}

}
