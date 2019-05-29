package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Interface for describing a NULL literal, a special kind of {@link PrimitiveDescriptor}. A NULL literal is used to describe that a value is intentionally missing.
 * 
 * @author sh20xyqi
 */

@Label(value = "Null", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface NullDescriptor extends PrimitiveDescriptor<String>{
		
	@Override
		default String getValue() {
			return "<null>";
		}
		
		@Override
		default void setValue(String value) {}
}
