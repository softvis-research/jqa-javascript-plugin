package org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jqassistant.contrib.plugin.javascript.api.model.FunctionDescriptor;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.FqnCreator;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * Manipulator for the {@link FunctionDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class FunctionStoreManipulator implements NodeStoreManipulator<FunctionDescriptor, ParserRuleContext> {

	public static final String FUNC_ANONYMOUS_NAME = "<anonymous>";
	private String functionName;
	
	public FunctionStoreManipulator(String functionName) {
		this.functionName = functionName;
	}
	
	public FunctionStoreManipulator() {
		this(FUNC_ANONYMOUS_NAME);
	}
	
	@Override
	public FunctionDescriptor createNodeIn(Store store, ParserRuleContext ctx, FqnCreator fqnCreator) {
		FunctionDescriptor ecmaFunc = store.create(FunctionDescriptor.class);
		ecmaFunc.setName(functionName);
		ecmaFunc.setFullQualifiedName(fqnCreator.createFqn(functionName));
		int line = ctx.start.getLine();
		ecmaFunc.setLine(line);
		return ecmaFunc;
	}


}
