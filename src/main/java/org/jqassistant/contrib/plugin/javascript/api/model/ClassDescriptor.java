package org.jqassistant.contrib.plugin.javascript.api.model;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Interface for describing a javascript class.
 * 
 * @author sh20xyqi
 */
@Label(value = "Class", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ClassDescriptor extends CodeArtifact {
    
    /**
     * Binds the parent class.
     * @return String
     */
    @Property("extends")
    String getSuperClass();
    void setSuperClass(String superClass);

}
