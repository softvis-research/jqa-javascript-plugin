package org.jqassistant.contrib.plugin.javascript.api.model;

import java.util.List;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 * Interface to describe a JavaScript code artifact: file, class, function or variable. Is not mentioned as a label in neo4j.
 * 
 * @author sh20xyqi
 */

@Label("")
public interface CodeArtifact extends FullQualifiedNameDescriptor, LineNumberDescriptor, JavaScriptDescriptor {

	public static final String DECLARES = "DECLARES";
	public static final String HAS = "HAS";

	@Outgoing
	List<InvokesDescriptor> getInvokes();
	
	/**
     * Contains all named and anonymous {@link FunctionDescriptor} that occur in this code artifact.
     * @return List of functions
     */
    @Relation(DECLARES)
    List<FunctionDescriptor> getFunctions();
    
    /**
     * Contains all named and anonymous {@link ClassDescriptor} that occur in this JavaScript code artifact.
     * @return List of classes
     */
    @Relation(DECLARES)
    List<ClassDescriptor> getClasses();
    
    /**
     * Contains all named and anonymous {@link VariableDescriptor} that occur in this JavaScript code artifact.
     * @return List of variables
     */
    @Relation(DECLARES)
    List<VariableDescriptor> getVariables();
    
    /**
     * Contains all named and anonymous {@link ComplexDescriptor} (e.g. {@link ObjectDescriptor} or {@link ArrayDescriptor}) that occur in this JavaScript code artifact.
     * @return List of objects 
     */
    @Relation(HAS)
    List<ComplexDescriptor> getObjects();
    
}
