package org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators;

import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.ArrayLiteralExpressionContext;
import org.jqassistant.contrib.plugin.javascript.api.model.ArrayDescriptor;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.FqnCreator;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * Manipulator for the {@link ArrayDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class ArrayStoreManipulator implements NodeStoreManipulator<ArrayDescriptor, ArrayLiteralExpressionContext> {

	@Override
	public ArrayDescriptor createNodeIn(Store store, ArrayLiteralExpressionContext ctx, FqnCreator fqnCreator) {
		ArrayDescriptor ecmaArray = store.create(ArrayDescriptor.class);
		ecmaArray.setFullQualifiedName(fqnCreator.createFqn(ArrayDescriptor.ARRAY));
		ecmaArray.setLine(ctx.getStart().getLine());
		return ecmaArray;
	}

}
