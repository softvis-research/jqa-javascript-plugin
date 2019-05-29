package org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers;

import org.jqassistant.contrib.plugin.javascript.api.model.ArrayDescriptor;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.ArrayLiteralExpressionContext;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * Manipulator for the {@link ArrayDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class ArrayStoreHelper implements NodeStoreHelper<ArrayDescriptor, ArrayLiteralExpressionContext> {

	@Override
	public ArrayDescriptor createNodeIn(Store store, ArrayLiteralExpressionContext ctx) {
		ArrayDescriptor ecmaArray = store.create(ArrayDescriptor.class);
		ecmaArray.setLine(ctx.getStart().getLine());
		return ecmaArray;
	}

}
