package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Interface that describes javascript arrays.
 *
 * @author sh20xyqi
 */

@Label(value = "Array", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ArrayDescriptor extends ComplexDescriptor {
	
}

