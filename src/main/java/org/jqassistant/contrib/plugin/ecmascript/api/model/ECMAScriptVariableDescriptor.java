package org.jqassistant.contrib.plugin.ecmascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * ECMAScript Variable Descriptor
 * @author sh20xyqi
 */
@Label(value = "Variable", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ECMAScriptVariableDescriptor extends ECMAScriptDescriptor, FullQualifiedNameDescriptor, ECMAScriptBasicComponents {
    
	/**
     * Returns the name of the variable
     * @return String 
     */
	@Property("name")
    String getName();
    void setName(String name);
    
}
