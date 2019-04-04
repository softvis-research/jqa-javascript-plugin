package org.jqassistant.contrib.plugin.ecmascript.api.model;

import java.util.List;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

@Label("ECMAScriptComponent")
public interface ECMAScriptBasicComponents extends ECMAScriptDescriptor, FullQualifiedNameDescriptor, LineNumberDescriptor {

	public static final String DECLARES = "DECLARES";

	/**
     * declared functions
     * @return list of functions 
     */
    @Relation(DECLARES)
    List<ECMAScriptFunctionDescriptor> getFunctions();
    
    /**
     * declared classes
     * @return list of classes
     */
    @Relation(DECLARES)
    List<ECMAScriptClassDescriptor> getClasses();
    
    /**
     * declared objects
     * @return list of objects 
     */
    @Relation(DECLARES)
    List<ECMAScriptBaseObjectDescriptor> getObjects();
    
    /**
     * declared variables
     * @return list of variables
     */
    @Relation(DECLARES)
    List<ECMAScriptVariableDescriptor> getVariables();
    
    /**
     * declared literals
     * @return list of literals
     */
    @Relation(DECLARES)
    List<ECMAScriptLiteralDescriptor<?>> getLiterals();
}
