package org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jqassistant.contrib.plugin.javascript.api.model.ArrayDeclaresRelationshipDescriptor;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;

/**
 * Manipulator for the {@link ArrayDesclaresRelationshipDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class ArrayDeclaresManipulator extends StoreRelationManipulator<ArrayDeclaresRelationshipDescriptor>{

	private int index = 0;

	public ArrayDeclaresManipulator(Store store) {
		super(store);
	}

	@Override
	public ArrayDeclaresRelationshipDescriptor createRelation(ParserRuleContext ctx, FullQualifiedNameDescriptor from, FullQualifiedNameDescriptor to) {
		ArrayDeclaresRelationshipDescriptor ret = store.create(from, ArrayDeclaresRelationshipDescriptor.class, to);
		ret.setIndex(index);
		index++;
		return ret;
	}

}
