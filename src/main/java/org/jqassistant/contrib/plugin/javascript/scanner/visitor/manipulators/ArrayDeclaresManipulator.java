package org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jqassistant.contrib.plugin.javascript.api.model.ArrayDeclares;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;

public class ArrayDeclaresManipulator extends StoreRelationManipulator<ArrayDeclares>{

	private int index = 0;

	public ArrayDeclaresManipulator(Store store) {
		super(store);
	}

	@Override
	public ArrayDeclares createRelation(ParserRuleContext ctx, FullQualifiedNameDescriptor from, FullQualifiedNameDescriptor to) {
		ArrayDeclares ret = store.create(from, ArrayDeclares.class, to);
		ret.setIndex(index);
		index++;
		return ret;
	}

}
