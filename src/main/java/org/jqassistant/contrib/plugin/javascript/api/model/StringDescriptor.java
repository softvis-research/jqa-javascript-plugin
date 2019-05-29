package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Interface for describing a string literal, a special kind of {@link PrimitiveDescriptor}. A string literal is used to describe a series of characters.
 * 
 * @author sh20xyqi
 */

@Label("String")
public interface StringDescriptor extends PrimitiveDescriptor<String>{
	
}
