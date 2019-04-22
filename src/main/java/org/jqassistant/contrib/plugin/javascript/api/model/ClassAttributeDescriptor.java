package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Parameter of a function
 * @author sh20xyqi
 */
@Label("Parameter")
public interface ClassAttributeDescriptor extends JsDescriptor{
    
    /**
     * Position of parameter
     * @return Integer 
     */
    @Property("index")
    int getIndex();
    void setIndex(int index);
    
    /**
     * Name of parameter
     * @return String 
     */
    String getName();
    void setName(String name);
    
}
