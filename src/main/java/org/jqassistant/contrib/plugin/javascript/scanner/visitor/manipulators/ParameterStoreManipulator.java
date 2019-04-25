package org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.FormalParameterListContext;
import org.jqassistant.contrib.plugin.javascript.api.model.FunctionParameterDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.LiteralDescriptor;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.FqnCreator;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * Manipulator for the {@link ParameterDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class ParameterStoreManipulator implements NodeStoreManipulator<FunctionParameterDescriptor, FormalParameterListContext> {

	private int paramIndex;
	
	public ParameterStoreManipulator(int paramIndex) {
		super();
		this.paramIndex = paramIndex;
	}

	@Override
	public FunctionParameterDescriptor createNodeIn(Store store, FormalParameterListContext ctx, FqnCreator fqnCreator) {
		ParseTree child = ctx.children.get(paramIndex);
		FunctionParameterDescriptor ecmaParam = store.create(FunctionParameterDescriptor.class);
		String funcName = child.getChild(0).getText();
		ecmaParam.setFullQualifiedName(fqnCreator.createFqn(funcName));
		ecmaParam.setName(funcName);
		int indexOfParam = paramIndex / 2;
		ecmaParam.setIndex(indexOfParam);
		ecmaParam.setLine(ctx.getStart().getLine());
		return ecmaParam;
	}


}
