package org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers;

import org.jqassistant.contrib.plugin.javascript.api.model.ObjectDescriptor;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.ObjectLiteralContext;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * Manipulator for the {@link ObjectDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class ObjectStoreHelper implements NodeStoreHelper<ObjectDescriptor, ObjectLiteralContext> {

	@Override
	public ObjectDescriptor createNodeIn(Store store, ObjectLiteralContext ctx) {
		ObjectDescriptor ecmaObj = store.create(ObjectDescriptor.class);
		ecmaObj.setLine(ctx.getStart().getLine());
		return ecmaObj;
	}

}
