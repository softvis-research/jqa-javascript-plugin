package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Interface to describe a file as a JavaScript file.
 * 
 * @author sh20xyqi
 */

public interface JavaScriptFileDescriptor extends CodeArtifact, NamedDescriptor, FileDescriptor {
	/**
	 * Binds the file path of the javascript file.
	 */
    @Property("filePath")
    String getFilepath();

    void setFilepath(String filePath);

}