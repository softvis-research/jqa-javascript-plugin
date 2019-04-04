package org.jqassistant.contrib.plugin.ecmascript.api.model;

import java.util.concurrent.atomic.AtomicInteger;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * ECMAScript Object Baseclass
 * @author sh20xyqi
 */
@Label(value = "ObjectType", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ECMAScriptBaseObjectDescriptor extends ECMAScriptBasicComponents, FullQualifiedNameDescriptor {

	public static final AtomicInteger count = new AtomicInteger(0);
	
	public static int getCount() {
		return ECMAScriptArrayDescriptor.count.incrementAndGet();
	}
}
