package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 * Interface that describes the relations of an {@link ObjectDescriptor} to its values. 
 * 
 * @author sh20xyqi
 */
@Relation("DECLARES_OBJECT")
public interface ObjectDeclaresRelationshipDescriptor extends Descriptor {

	@Outgoing
	ObjectDescriptor getObjectDescriptor();
	
	@Incoming
	JsDescriptor getJsDescriptor();
	
	/**
     * Returns all existing keys of an object.
     *
     * @return The keys.
     */
	
	@Property(value = "key")
	String getKey();
	void setKey(String key);
}

