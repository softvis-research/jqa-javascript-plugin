package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import java.util.List;

/**
 * Interface to describe a named or anonymous function.
 * 
 * @author sh20xyqi
 */
@Label(value = "Function", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface FunctionDescriptor extends JsDescriptor, FullQualifiedNameDescriptor, CodeArtifact {
    
	/**
     * Returns the name of the function.
     *
     * @return The name.
     */
	@Property("name")
    String getName();
    void setName(String name);
    
    /**
     * Returns all declared {@link FunctionParameterDescriptor} of this function.
     *
     * @return The declared parameters.
     */
    @Relation("HAS")
    List<FunctionParameterDescriptor> getParameters();
    
}