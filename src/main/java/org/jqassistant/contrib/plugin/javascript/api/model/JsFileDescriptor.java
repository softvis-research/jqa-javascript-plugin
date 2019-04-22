package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * ECMAScript file descriptor
 * @author sh20xyqi
 */
@Label("JS_Source")
public interface JsFileDescriptor extends CodeArtifact, JsDescriptor, NamedDescriptor, FileDescriptor {}