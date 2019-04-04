package org.jqassistant.contrib.plugin.ecmascript.scanner.visitor;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser.PropertyExpressionAssignmentContext;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScripObjectDeclares;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;

public class ObjectDeclaresManipulator extends StoreRelationManipulator<ECMAScripObjectDeclares>{

	public ObjectDeclaresManipulator(Store store) {
		super(store);
	}

	@Override
	public ECMAScripObjectDeclares createRelation(ParserRuleContext ctx, FullQualifiedNameDescriptor from, FullQualifiedNameDescriptor to) {
		if(ctx instanceof PropertyExpressionAssignmentContext) {
			ECMAScripObjectDeclares ret = store.create(from, ECMAScripObjectDeclares.class, to);
			String key = ctx.getChild(0).getChild(0).getText();
			ret.setKey(key);
			return ret;
		} else {
			return createRelation(ctx.getParent(), from, to);
		}
	}
	

}
