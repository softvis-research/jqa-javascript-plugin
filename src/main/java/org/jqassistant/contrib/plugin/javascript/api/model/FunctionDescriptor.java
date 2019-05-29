package org.jqassistant.contrib.plugin.javascript.api.model;

import java.util.List;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;

/**
 * Interface to describe a named or anonymous function.
 * 
 * @author sh20xyqi
 */
@Label(value = "Function", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface FunctionDescriptor extends CodeArtifact {
    
    /**
     * Returns all declared {@link FunctionParameterDescriptor} of this function.
     *
     * @return The declared parameters.
     */
    @Relation("HAS")
    List<FunctionParameterDescriptor> getParameters();
 
    @Incoming
    List<InvokesDescriptor> getInvokedBy();
}
