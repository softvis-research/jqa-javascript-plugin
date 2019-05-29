package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Interface for describing a named or unnamed variable that carries a {@link PrimitiveDescriptor} or a {@link ComplexDescriptor}.
 * 
 * @author sh20xyqi
 */
@Label(value = "Variable", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface VariableDescriptor extends CodeArtifact, ContainsPrimitiveDescriptor {
    
}
