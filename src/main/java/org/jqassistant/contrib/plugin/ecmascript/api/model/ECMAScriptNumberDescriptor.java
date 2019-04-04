package org.jqassistant.contrib.plugin.ecmascript.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("Number")
public interface ECMAScriptNumberDescriptor extends ECMAScriptLiteralDescriptor<Double>{}
