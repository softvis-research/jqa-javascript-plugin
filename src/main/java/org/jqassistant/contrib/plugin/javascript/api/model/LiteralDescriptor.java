package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

@Label(value = "Literal")
public interface LiteralDescriptor <T> extends JsDescriptor, FullQualifiedNameDescriptor, LineNumberDescriptor {
	
	@Property("value")
	T getValue();
	void setValue(T value);
	
	@Override
	default String getFullQualifiedName() {
		return getClass().getSimpleName();
	}
	
}
