package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

@Label(value = "Null", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface NullDescriptor extends LiteralDescriptor<String>{
		@Override
		default String getValue() {
			return "<null>";
		}
		
		@Override
		default void setValue(String value) {}
}
