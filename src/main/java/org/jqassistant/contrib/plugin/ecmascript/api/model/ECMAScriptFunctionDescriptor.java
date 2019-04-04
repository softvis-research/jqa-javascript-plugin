package org.jqassistant.contrib.plugin.ecmascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import java.util.List;

/**
 * ECMAScript function
 * @author sh20xyqi
 */
@Label(value = "Function", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ECMAScriptFunctionDescriptor extends ECMAScriptDescriptor, FullQualifiedNameDescriptor, ECMAScriptBasicComponents {
    
	/**
     * Return the name of a function.
     *
     * @return The name.
     */
	@Property("name")
    String getName();
    void setName(String name);
    
    /**
     * Return all declared parameters of this function.
     *
     * @return The declared parameters.
     */
    @Relation("HAS")
    List<ECMAScriptFunctionParameterDescriptor> getParameters();
    
}
