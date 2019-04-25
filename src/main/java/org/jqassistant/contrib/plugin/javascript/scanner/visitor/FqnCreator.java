package org.jqassistant.contrib.plugin.javascript.scanner.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		String fqn = createFqnFrom(fqnBase, artifactName);
		return fqn;
	}

	public String createFqnFrom(String left, String right) {
		String fqn = String.format("%s%s%s", left, FQN_SEPARATOR, right);
		return fqn;
	}
	
	public List<String> getAllFqnsFrom(String appendable){
		List<String> returnable = new ArrayList<String>();
		String[] partsOfFqnBase = fqnBase.split(FQN_SEPARATOR);
		for(int i = 0; i < partsOfFqnBase.length; i++) {
			StringBuilder b = new StringBuilder();
			for(int j = 0; j < i; j++) {
				b.append(partsOfFqnBase[j]);
				b.append(FQN_SEPARATOR);
			}
			b.append(partsOfFqnBase[i]);
			returnable.add(createFqnFrom(b.toString(), appendable));
		}
		Collections.reverse(returnable);
		return returnable;
	}
	
}
