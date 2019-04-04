package org.jqassistant.contrib.plugin.ecmascript.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * ECMAScript file descriptor
 * @author sh20xyqi
 */
@Label("ECMAScriptFile")
public interface ECMAScriptFileDescriptor extends ECMAScriptBasicComponents, ECMAScriptDescriptor, NamedDescriptor, FileDescriptor {}