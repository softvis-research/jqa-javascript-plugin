package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Interface for describing all passed parameters of a function. Parameters are still special variables.
 * 
 * @author sh20xyqi
 */
@Label(value = "Parameter", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface FunctionParameterDescriptor extends VariableDescriptor {
    
	/**
     * Returns the index of the parameter.
     * @return String 
     */
	@Property("index")
    int getIndex();
    void setIndex(int index);
}
	