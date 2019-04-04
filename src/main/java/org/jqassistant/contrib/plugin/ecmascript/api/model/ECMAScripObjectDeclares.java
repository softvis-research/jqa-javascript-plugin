package org.jqassistant.contrib.plugin.ecmascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 * ECMAScript Object Declares
 * @author sh20xyqi
 */
@Relation("DECLARES_OBJECT")
public interface ECMAScripObjectDeclares extends Descriptor {

	@Outgoing
	ECMAScriptObjectDescriptor getECMAScriptObjectDescriptor();
	
	@Incoming
	ECMAScriptDescriptor getECMAScriptDescriptor();
	
	@Property(value = "key")
	String getKey();
	void setKey(String key);
}

