package org.jqassistant.contrib.plugin.javascript.scanner.visitor.helpers;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.javascript.api.model.ClassDescriptor;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser.ClassDeclarationContext;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * Manipulator for the {@link ClassDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class ClassStoreHelper implements NodeStoreHelper<ClassDescriptor, ClassDeclarationContext> {

	@Override
	public ClassDescriptor createNodeIn(Store store, ClassDeclarationContext ctx) {
		ClassDescriptor ecmaClass = store.create(ClassDescriptor.class);
		String className = ctx.children.get(1).getText();
		ecmaClass.setName(className);
		ecmaClass.setLine(ctx.getStart().getLine());
		ParseTree classBody = ctx.children.get(2);
		if ("extends".equals(classBody.getChild(0).getText())) {
			String superClassName = classBody.getChild(1).getChild(0).getText();
			ecmaClass.setSuperClass(superClassName);
		}
		return ecmaClass;
	}

}
