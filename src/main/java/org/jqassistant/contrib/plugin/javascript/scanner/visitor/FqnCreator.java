package org.jqassistant.contrib.plugin.javascript.scanner.visitor;

public class FqnCreator {

	private static final String FQN_SEPARATOR = ":";
	
	private String fqnBase;

	public FqnCreator(String fqnBase) {
		super();
		this.fqnBase = fqnBase;
	}
	
	public String createFQNFor(String artifactName){
		return createFqn(artifactName);
	}
	
	public String createFqn(String artifactName) {
		String fqn = String.format("%s%s%s", fqnBase, FQN_SEPARATOR, artifactName);
		return fqn;
	}
	
}
