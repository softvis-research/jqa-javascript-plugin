package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Interface to describe a number, a special kind of {@link PrimitiveDescriptor}. Numbers can be represented as a decimal, octal, hexadecimal, and binary number and represented as an integer or floating point number.
 * 
 * @author sh20xyqi
 */

@Label("Number")
public interface NumberDescriptor extends PrimitiveDescriptor<Double>{}
