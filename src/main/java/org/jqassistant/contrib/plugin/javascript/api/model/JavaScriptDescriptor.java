package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Interface to mark all entities as JavaScript artifacts with "JavaScript".
 * 
 * @author sh20xyqi
 */

@Label("JavaScript")
public interface JavaScriptDescriptor extends Descriptor {
	
    
	public static final String NAME = "name";
	/**
     * Returns the name of the JavaScript code line.
     * @return String 
     */
	@Property(NAME)
    String getName();
    void setName(String name);
}
