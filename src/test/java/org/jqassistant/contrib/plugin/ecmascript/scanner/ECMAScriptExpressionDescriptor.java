package org.jqassistant.contrib.plugin.ecmascript.scanner;

import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptLiteralDescriptor;

import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("Expression")
public interface ECMAScriptExpressionDescriptor extends ECMAScriptLiteralDescriptor<String>{}
