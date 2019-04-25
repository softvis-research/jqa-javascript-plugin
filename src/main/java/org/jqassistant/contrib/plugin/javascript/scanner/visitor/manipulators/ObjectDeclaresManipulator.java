package org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.PropertyExpressionAssignmentContext;
import org.jqassistant.contrib.plugin.javascript.api.model.LiteralDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.ObjectDeclaresRelationshipDescriptor;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;

/**
 * Manipulator for the {@link ObjectDeclaresRelationshipDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class ObjectDeclaresManipulator extends StoreRelationManipulator<ObjectDeclaresRelationshipDescriptor>{

	public ObjectDeclaresManipulator(Store store) {
		super(store);
	}

	@Override
	public ObjectDeclaresRelationshipDescriptor createRelation(ParserRuleContext ctx, FullQualifiedNameDescriptor from, FullQualifiedNameDescriptor to) {
		if(ctx instanceof PropertyExpressionAssignmentContext) {
			ObjectDeclaresRelationshipDescriptor ret = store.create(from, ObjectDeclaresRelationshipDescriptor.class, to);
			String key = ctx.getChild(0).getChild(0).getText();
			ret.setKey(key);
			return ret;
		} else {
			return createRelation(ctx.getParent(), from, to);
		}
	}
	

}
