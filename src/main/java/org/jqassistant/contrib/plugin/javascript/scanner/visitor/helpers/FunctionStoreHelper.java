package org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers;

import java.util.Optional;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jqassistant.contrib.plugin.javascript.api.model.FunctionDescriptor;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * Manipulator for the {@link FunctionDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class FunctionStoreHelper implements NodeStoreHelper<FunctionDescriptor, ParserRuleContext> {

	public static final String FUNC_ANONYMOUS_NAME = "<anonymous>";
	public static final String CONSTRUCTOR = "constructor";
	private String functionName;
	
	public FunctionStoreHelper(String functionName) {
		this.functionName = functionName;
	}
	
	public FunctionStoreHelper(Optional<String> s) {
		this(s.orElse(FUNC_ANONYMOUS_NAME));
	}
	
	
	
	@Override
	public FunctionDescriptor createNodeIn(Store store, ParserRuleContext ctx) {
		FunctionDescriptor ecmaFunc = store.create(FunctionDescriptor.class);
		ecmaFunc.setName(functionName);
		int line = ctx.start.getLine();
		ecmaFunc.setLine(line);
		return ecmaFunc;
	}


}
