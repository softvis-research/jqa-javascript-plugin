package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Interface that describes javascript booleans.
 *
 * @author sh20xyqi
 */

@Label("Boolean")
public interface BooleanDescriptor extends PrimitiveDescriptor<Boolean>{}
