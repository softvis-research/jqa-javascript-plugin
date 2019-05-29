package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Interface for the description of data structures, which can be either an {@link ObjectDescriptor} or an {@kink ArrayDescriptor}.
 * 
 * @author sh20xyqi
 */
@Label(value = "", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ComplexDescriptor extends CodeArtifact, FullQualifiedNameDescriptor, ContainsPrimitiveDescriptor {

}
