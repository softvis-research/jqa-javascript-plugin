package org.jqassistant.contrib.plugin.ecmascript.api.model;

import java.util.List;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;

@Label("ECMAScript")
public interface ECMAScriptDescriptor extends Descriptor {
	@Incoming
	List<ECMAScripArrayDeclares> getECMAScripArrayDeclares();
	
	@Incoming
	List<ECMAScripObjectDeclares> getECMAScripObjectDeclares();
}
