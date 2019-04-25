package org.jqassistant.contrib.plugin.javascript.api.model;

import java.util.List;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Relation;

/**
 * Interface to describe a JavaScript code artifact.
 * 
 * @author sh20xyqi
 */

public interface CodeArtifact extends FullQualifiedNameDescriptor, LineNumberDescriptor {

	public static final String DECLARES = "DECLARES";

	/**
     * Contains all named and anonymous {@link FunctionDescriptor} that occur in this code artifact.
     * @return List of functions
     */
    @Relation(DECLARES)
    List<FunctionDescriptor> getFunctions();
    
	/**
     * Contains all functions that were called by another {@link FunctionDescriptor}.
     * @return List of invoked functions 
     */
    @Relation("INVOKES")
    List<FunctionDescriptor> getInvokes();
    
    /**
     * Contains all named and anonymous {@link ClassDescriptor} that occur in this JavaScript code artifact.
     * @return List of classes
     */
    @Relation(DECLARES)
    List<ClassDescriptor> getClasses();
    
    /**
     * Contains all named and anonymous {@link BaseObjectDescriptor} (e.g. {@link ObjectDescriptor} or {@link ArrayDescriptor}) that occur in this JavaScript code artifact.
     * @return List of objects 
     */
    @Relation(DECLARES)
    List<BaseObjectDescriptor> getObjects();
    
    /**
     * Contains all named and anonymous {@link VariableDescriptor} that occur in this JavaScript code artifact.
     * @return List of variables
     */
    @Relation(DECLARES)
    List<VariableDescriptor> getVariables();
    
    /**
     * Contains all named and anonymous {@link LiteralDescriptor} (also called primitive data types) that occur in this JavaScript code artifact.
     * @return List of literals (primitive data types)
     */
    @Relation(DECLARES)
    List<LiteralDescriptor<?>> getLiterals();
}
