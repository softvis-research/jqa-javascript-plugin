package org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers;


import org.jqassistant.contrib.plugin.javascript.api.model.FunctionParameterDescriptor;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.FormalParameterArgContext;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * Manipulator for the {@link ParameterDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class ParameterStoreHelper implements NodeStoreHelper<FunctionParameterDescriptor, FormalParameterArgContext> {

	private int paramIndex;
	
	public ParameterStoreHelper(int paramIndex) {
		super();
		this.paramIndex = paramIndex;
	}

	@Override
	public FunctionParameterDescriptor createNodeIn(Store store, FormalParameterArgContext ctx) {
		String paraName = ctx.getChild(0).getText();
		FunctionParameterDescriptor ecmaParam = store.create(FunctionParameterDescriptor.class);
		ecmaParam.setName(paraName);
		int indexOfParam = paramIndex;
		ecmaParam.setIndex(indexOfParam);
		ecmaParam.setLine(ctx.getStart().getLine());
		return ecmaParam;
	}


}
