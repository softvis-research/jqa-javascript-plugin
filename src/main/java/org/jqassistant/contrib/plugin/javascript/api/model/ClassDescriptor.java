package org.jqassistant.contrib.plugin.javascript.api.model;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Interface for describing a class.
 * 
 * @author sh20xyqi
 */
@Label(value = "Class", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ClassDescriptor extends JsDescriptor, CodeArtifact, FullQualifiedNameDescriptor {
    
	/**
     * Returns the name of the class.
     * @return String 
     */
	@Property("NAME")
    String getName();
    void setName(String name);
    
    /**
     * Returns the super class of the class.
     * @return String
     */
    @Property("EXTENDS")
    String getSuperClass();
    void setSuperClass(String superClass);

}
