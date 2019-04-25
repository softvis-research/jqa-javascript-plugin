package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Interface for describing the line number to better identify the entity in the source code artifact.
 * 
 * @author sh20xyqi
 */

public interface LineNumberDescriptor extends Descriptor {
	
	@Property("line")
	int getLine();
	void setLine(int line);
}
