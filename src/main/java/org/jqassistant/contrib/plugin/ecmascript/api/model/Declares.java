package org.jqassistant.contrib.plugin.ecmascript.api.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.buschmais.xo.neo4j.api.annotation.Relation;

/**
 * Defines the declares relation used for fields, methods and inner classes.
 */
@Relation("DECLARES")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Declares {
}
