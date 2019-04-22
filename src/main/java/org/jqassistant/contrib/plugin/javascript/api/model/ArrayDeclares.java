package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 * JS Array Declares
 * @author sh20xyqi
 */
@Relation("DECLARES_ARRAY")
public interface ArrayDeclares extends Descriptor {

	@Outgoing
	ArrayDescriptor getArrayDescriptor();
	
	@Incoming
	JsDescriptor getJsDescriptor();
	
	@Property(value = "index")
	int getIndex();
	void setIndex(int index);
}

