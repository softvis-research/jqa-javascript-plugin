package org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators;

import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.ObjectLiteralContext;
import org.jqassistant.contrib.plugin.javascript.api.model.LiteralDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.ObjectDescriptor;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.FqnCreator;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * Manipulator for the {@link ObjectDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class ObjectStoreManipulator implements NodeStoreManipulator<ObjectDescriptor, ObjectLiteralContext> {

	@Override
	public ObjectDescriptor createNodeIn(Store store, ObjectLiteralContext ctx, FqnCreator fqnCreator) {
		ObjectDescriptor ecmaObj = store.create(ObjectDescriptor.class);
		String objName = ObjectDescriptor.OBJECT;
		ecmaObj.setFullQualifiedName(fqnCreator.createFqn(objName));
		ecmaObj.setLine(ctx.getStart().getLine());
		return ecmaObj;
	}

}
