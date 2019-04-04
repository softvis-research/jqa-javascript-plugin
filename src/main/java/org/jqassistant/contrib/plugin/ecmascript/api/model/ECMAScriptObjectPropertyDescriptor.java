package org.jqassistant.contrib.plugin.ecmascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * ECMAScript Class Descriptor
 * @author sh20xyqi
 */
@Label(value = "Property", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ECMAScriptObjectPropertyDescriptor extends ECMAScriptDescriptor {
    
    /**
     * Returns the value of the object property
     * @return String 
     */
	@Property("value")
    String getValue();
    void setValue(String value);
    
    /**
     * Returns the key of the object property
     * @return String 
     */
	@Property("key")
    String getKey();
    void setKey(String key);
   
}
