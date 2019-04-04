package org.jqassistant.contrib.plugin.ecmascript.scanner.visitor;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScripArrayDeclares;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;

public class ArrayDeclaresManipulator extends StoreRelationManipulator<ECMAScripArrayDeclares>{

	private int index = 0;

	public ArrayDeclaresManipulator(Store store) {
		super(store);
	}

	@Override
	public ECMAScripArrayDeclares createRelation(ParserRuleContext ctx, FullQualifiedNameDescriptor from, FullQualifiedNameDescriptor to) {
		ECMAScripArrayDeclares ret = store.create(from, ECMAScripArrayDeclares.class, to);
		ret.setIndex(index);
		index++;
		return ret;
	}

}
