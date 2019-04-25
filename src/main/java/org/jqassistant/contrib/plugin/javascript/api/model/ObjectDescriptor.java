package org.jqassistant.contrib.plugin.javascript.api.model;

import java.util.List;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * Interface used to describe the special type of a {@link BaseObjectDescriptor} called object.
 * 
 * @author sh20xyqi
 */
@Label(value = "Object", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface ObjectDescriptor extends BaseObjectDescriptor {
	
	public static final String OBJECT = "[object]";

	@Override
	default String getFullQualifiedName() {
		return OBJECT;
	}
	/**
     * Returns all declared {@link ObjectDeclaresRelationshipDescriptor} of this object.
     *
     * @return The elements of the object.
     */
	List<ObjectDeclaresRelationshipDescriptor> getObjectDeclaresRelationshipDescriptor();
}
