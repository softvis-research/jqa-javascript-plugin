package org.jqassistant.contrib.plugin.ecmascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

@Label(value = "Literal")
public interface ECMAScriptLiteralDescriptor <T> extends ECMAScriptDescriptor, FullQualifiedNameDescriptor, LineNumberDescriptor {
	
	@Property("value")
	T getValue();
	void setValue(T value);
	
	@Override
	default String getFullQualifiedName() {
		return getClass().getSimpleName();
	}
	
}
