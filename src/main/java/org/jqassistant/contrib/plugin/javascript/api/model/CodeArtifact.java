package org.jqassistant.contrib.plugin.javascript.api.model;

import java.util.List;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Relation;

public interface CodeArtifact extends  FullQualifiedNameDescriptor, LineNumberDescriptor {

	public static final String DECLARES = "DECLARES";

	/**
     * declared functions
     * @return list of functions 
     */
    @Relation(DECLARES)
    List<FunctionDescriptor> getFunctions();
    
    /**
     * declared classes
     * @return list of classes
     */
    @Relation(DECLARES)
    List<ClassDescriptor> getClasses();
    
    /**
     * declared objects
     * @return list of objects 
     */
    @Relation(DECLARES)
    List<BaseObjectDescriptor> getObjects();
    
    /**
     * declared variables
     * @return list of variables
     */
    @Relation(DECLARES)
    List<VariableDescriptor> getVariables();
    
    /**
     * declared literals
     * @return list of literals
     */
    @Relation(DECLARES)
    List<LiteralDescriptor<?>> getLiterals();
}
