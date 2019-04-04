package org.jqassistant.contrib.plugin.ecmascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Function Parameters
 * @author sh20xyqi
 */
@Label(value = "Parameter", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ECMAScriptFunctionParameterDescriptor extends ECMAScriptVariableDescriptor {
    
	/**
     * Returns the index of the parameter
     * @return String 
     */
	@Property("index")
    int getIndex();
    void setIndex(int index);
}
	