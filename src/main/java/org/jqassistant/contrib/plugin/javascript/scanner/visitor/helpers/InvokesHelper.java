package org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jqassistant.contrib.plugin.javascript.api.model.CodeArtifact;
import org.jqassistant.contrib.plugin.javascript.api.model.FunctionDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.InvokesDescriptor;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * @author sh20xyqi
 */

public class InvokesHelper extends StoreRelationHelper<InvokesDescriptor, ParserRuleContext, CodeArtifact, FunctionDescriptor> {

	public InvokesHelper(Store store) {
		super(store);
	}

	@Override
	public InvokesDescriptor createRelation(ParserRuleContext ctx, CodeArtifact from, FunctionDescriptor to) {
		InvokesDescriptor ret = store.create(from, InvokesDescriptor.class, to);
		return ret;
	}

}
