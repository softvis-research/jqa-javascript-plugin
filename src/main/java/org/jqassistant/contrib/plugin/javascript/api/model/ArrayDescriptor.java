package org.jqassistant.contrib.plugin.javascript.api.model;

import java.util.List;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Interface used to describe the special type of a {@link BaseObjectDescriptor} called array.
 * 
 * @author sh20xyqi
 */
@Label(value = "Array", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ArrayDescriptor extends BaseObjectDescriptor {
	
	public static final String ARRAY = "[array]";

	@Override
	default String getFullQualifiedName() {
		return ARRAY;
	}
	/**
     * Returns all index {@link ArrayDeclaresRelationshipDescriptor} of this array.
     *
     * @return The elements of the array.
     */
	List<ArrayDeclaresRelationshipDescriptor> getArrayDeclares();
}

