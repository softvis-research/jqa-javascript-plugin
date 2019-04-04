package org.jqassistant.contrib.plugin.ecmascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 * ECMAScript Array Declares
 * @author sh20xyqi
 */
@Relation("DECLARES_ARRAY")
public interface ECMAScripArrayDeclares extends Descriptor {

	@Outgoing
	ECMAScriptArrayDescriptor getECMAScriptArrayDescriptor();
	
	@Incoming
	ECMAScriptDescriptor getECMAScriptDescriptor();
	
	@Property(value = "index")
	int getIndex();
	void setIndex(int index);
}

