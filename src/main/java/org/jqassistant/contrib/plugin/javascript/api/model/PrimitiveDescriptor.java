package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Interface for describing a literal, also known as a primitive data type.
 * 
 * @author sh20xyqi
 */

@Label(value = "Value")
public interface PrimitiveDescriptor <T> extends JavaScriptDescriptor, FullQualifiedNameDescriptor, LineNumberDescriptor {
	
	/**
	 * A primitive can have a single value which is assigned to it.
	 * 
	 * @return Returns the value of the literal.
	 */
	
	@Property("value")
	T getValue();
	void setValue(T value);
	
	@Override
	default String getFullQualifiedName() {
		return getClass().getSimpleName();
	}
	
}
