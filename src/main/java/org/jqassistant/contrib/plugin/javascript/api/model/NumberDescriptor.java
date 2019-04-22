package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("Number")
public interface NumberDescriptor extends LiteralDescriptor<Double>{}
