package org.jqassistant.contrib.plugin.ecmascript.api.model;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * ECMAScript Class Descriptor
 * @author sh20xyqi
 */
@Label(value = "Class", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ECMAScriptClassDescriptor extends ECMAScriptDescriptor, ECMAScriptBasicComponents, FullQualifiedNameDescriptor {
    
	/**
     * Returns the name of the class
     * @return String 
     */
	@Property("NAME")
    String getName();
    void setName(String name);
    
    /**
     * Returns the super class of the class
     * @return String
     */
    @Property("EXTENDS")
    String getSuperClass();
    void setSuperClass(String superClass);

}
