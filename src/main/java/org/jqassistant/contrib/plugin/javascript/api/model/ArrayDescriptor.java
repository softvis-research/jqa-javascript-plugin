package org.jqassistant.contrib.plugin.javascript.api.model;

import java.util.List;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 * ECMAScript Object Baseclass
 * @author sh20xyqi
 */
@Label(value = "Array", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ArrayDescriptor extends BaseObjectDescriptor {
	
	public static final String ARRAY = "[array]";

	@Override
	default String getFullQualifiedName() {
		return ARRAY;
	}
	
	@Outgoing
	List<ArrayDeclares> getArrayDeclares();
}

