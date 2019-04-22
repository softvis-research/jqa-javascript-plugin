package org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.PropertyExpressionAssignmentContext;
import org.jqassistant.contrib.plugin.javascript.api.model.ObjectDeclares;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;

public class ObjectDeclaresManipulator extends StoreRelationManipulator<ObjectDeclares>{

	public ObjectDeclaresManipulator(Store store) {
		super(store);
	}

	@Override
	public ObjectDeclares createRelation(ParserRuleContext ctx, FullQualifiedNameDescriptor from, FullQualifiedNameDescriptor to) {
		if(ctx instanceof PropertyExpressionAssignmentContext) {
			ObjectDeclares ret = store.create(from, ObjectDeclares.class, to);
			String key = ctx.getChild(0).getChild(0).getText();
			ret.setKey(key);
			return ret;
		} else {
			return createRelation(ctx.getParent(), from, to);
		}
	}
	

}
