package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Interface for describing the undefined literal, a special kind of {@link LiteralDescriptor}. An undefined literal is used to describe that a particular variable is not defined in more detail.
 * 
 * @author sh20xyqi
 */

@Label(value = "Undefined", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface UndefinedDescriptor extends LiteralDescriptor<String>{
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
