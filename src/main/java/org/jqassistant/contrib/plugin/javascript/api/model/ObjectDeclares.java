package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 * Object Declares
 * @author sh20xyqi
 */
@Relation("DECLARES_OBJECT")
public interface ObjectDeclares extends Descriptor {

	@Outgoing
	ObjectDescriptor getObjectDescriptor();
	
	@Incoming
	JsDescriptor getJsDescriptor();
	
	@Property(value = "key")
	String getKey();
	void setKey(String key);
}

