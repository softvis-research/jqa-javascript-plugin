package org.jqassistant.contrib.plugin.ecmascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

@Label(value = "Undefined", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ECMAScriptUndefinedDescriptor extends ECMAScriptLiteralDescriptor<String>{
		@Override
		default String getValue() {
			return "<undefined>";
		}
		
		@Override
		default String getFullQualifiedName() {
			return "UNDEFINED";
		}
		
		@Override
		default void setValue(String value) {}
}
