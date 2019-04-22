package org.jqassistant.contrib.plugin.javascript.api.model;

import java.util.List;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;

@Label("JS")
public interface JsDescriptor extends Descriptor {
	@Incoming
	List<ArrayDeclares> getArrayDeclares();
	
	@Incoming
	List<ObjectDeclares> getObjectDeclares();
}
