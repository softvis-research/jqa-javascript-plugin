package org.jqassistant.contrib.plugin.javascript.api.model;

import java.util.List;

import com.buschmais.xo.neo4j.api.annotation.Relation;

public interface ContainsPrimitiveDescriptor {
    @Relation("HAS")
    List<PrimitiveDescriptor<?>> getPrimitives();
}
