package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Interface to describe a file as a JavaScript file.
 * 
 * @author sh20xyqi
 */

@Label("JS")
public interface JsFileDescriptor extends CodeArtifact, JsDescriptor, NamedDescriptor, FileDescriptor {
	
}