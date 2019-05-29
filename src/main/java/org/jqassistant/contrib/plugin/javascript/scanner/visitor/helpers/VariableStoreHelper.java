package org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers;

import org.jqassistant.contrib.plugin.javascript.api.model.VariableDescriptor;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.VariableDeclarationContext;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * Manipulator for the {@link VariableDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class VariableStoreHelper implements NodeStoreHelper<VariableDescriptor, VariableDeclarationContext> {

	@Override
	public VariableDescriptor createNodeIn(Store store, VariableDeclarationContext ctx) {
		// step one: get data from AST
		String varName = ctx.children.get(0).getText();
		// step two: create Variable
		VariableDescriptor jsVar = createVar(store, varName, ctx.getStart().getLine());
		return jsVar;
	}

	public VariableDescriptor createVar(Store store, String varName, int line) {
		VariableDescriptor jsVar = store.create(VariableDescriptor.class);
		jsVar.setName(varName);
		jsVar.setLine(line);
		return jsVar;
	}

}
