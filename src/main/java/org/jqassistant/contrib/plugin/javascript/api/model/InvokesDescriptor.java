package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 * Defines an INVOKES relation between two methods.
 */
@Relation("INVOKES")
public interface InvokesDescriptor extends Descriptor, LineNumberDescriptor {

	@Incoming
	FunctionDescriptor getFunctionDescriptor();
	
    @Outgoing
    CodeArtifact getCodeArtifact();

}