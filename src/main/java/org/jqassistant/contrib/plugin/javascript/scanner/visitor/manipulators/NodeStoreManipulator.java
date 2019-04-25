package org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators;

import org.antlr.v4.runtime.RuleContext;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.FqnCreator;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

/**
 * Manipulator for all possible nodes to prepare the interaction with the store for this type of descriptor. Serves as a super class for all other manipulators.
 * 
 * @author sh20xyqi
 */

public interface NodeStoreManipulator<S extends Descriptor,T extends RuleContext> {
	
	S createNodeIn(Store store, T ctx, FqnCreator fqnCreator);
	
}
