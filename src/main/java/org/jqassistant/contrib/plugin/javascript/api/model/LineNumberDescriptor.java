package org.jqassistant.contrib.plugin.javascript.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Property;

public interface LineNumberDescriptor extends Descriptor {
	
	@Property("line")
	int getLine();
	void setLine(int line);
}
