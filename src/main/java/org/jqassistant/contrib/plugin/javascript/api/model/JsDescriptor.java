package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Interface to mark all entities as JavaScript artifacts with "JS".
 * 
 * @author sh20xyqi
 */

@Label("JavaScript")
public interface JsDescriptor extends Descriptor {
	
}
