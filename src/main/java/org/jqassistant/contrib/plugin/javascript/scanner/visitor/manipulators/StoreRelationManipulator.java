package org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators;

import org.antlr.v4.runtime.ParserRuleContext;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;

/**
 * Manipulator for all possible relationships to prepare the interaction with the store for this type of descriptor. Serves as a super class for all other manipulators.
 * 
 * @author sh20xyqi
 */

public abstract class StoreRelationManipulator <T extends Descriptor> {

	protected Store store;
	
	public StoreRelationManipulator(Store store) {
		super();
		this.store = store;
	}

	public abstract T createRelation(ParserRuleContext ctx, FullQualifiedNameDescriptor from, FullQualifiedNameDescriptor to);
}
