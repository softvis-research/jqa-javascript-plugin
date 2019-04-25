package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Interface for describing a named or unnamed variable that carries a {@link LiteralDescriptor} or a {@link BaseObjectDescriptor}.
 * 
 * @author sh20xyqi
 */
@Label(value = "Variable", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface VariableDescriptor extends JsDescriptor, FullQualifiedNameDescriptor, CodeArtifact {
    
	/**
     * Returns the name of the variable.
     * @return String 
     */
	@Property("name")
    String getName();
    void setName(String name);
    
}
