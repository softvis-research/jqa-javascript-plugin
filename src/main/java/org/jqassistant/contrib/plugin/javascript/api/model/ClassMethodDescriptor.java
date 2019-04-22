package org.jqassistant.contrib.plugin.javascript.api.model;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Attributes of a class
 * @author sh20xyqi
 */
@Label("Method")
public interface ClassMethodDescriptor extends JsDescriptor{
    
	/**
     * Returns the name of the class method
     * @return String 
     */
	@Property("HAS")
    String getName();
    void setName(String name);
    
    /**
     * Returns the signature of the class method
     * @return String 
     */
	@Property("HAS")
    String getSignature();
    void setSignature(String signature);
}
