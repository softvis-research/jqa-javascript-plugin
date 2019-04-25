package org.jqassistant.contrib.plugin.javascript.scanner.visitor.manipulators;

import java.util.Optional;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.LiteralContext;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser.NumericLiteralContext;
import org.jqassistant.contrib.plugin.javascript.api.model.BooleanDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.LiteralDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.NullDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.NumberDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.StringDescriptor;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.FqnCreator;

import com.buschmais.jqassistant.core.store.api.Store;

/**
 * Manipulator for the {@link LiteralDescriptor} to prepare the interaction with the store for this type of descriptor.
 * 
 * @author sh20xyqi
 */

public class LiteralStoreManipulator implements NodeStoreManipulator<LiteralDescriptor<?>, LiteralContext> {

	@Override
	public LiteralDescriptor<?> createNodeIn(Store store, LiteralContext ctx, FqnCreator fqnCreator) {
		ParseTree literal = ctx.getChild(0);
		String text = ctx.getText();
		Optional<LiteralDescriptor<?>> to = Optional.empty();
		if (literal instanceof NumericLiteralContext) {
			NumberDescriptor number = store.create(NumberDescriptor.class);
			number.setValue(Double.parseDouble(text));
			to = Optional.of(number);
		} else if (literal instanceof TerminalNodeImpl) {
			if ("true".equals(text)) {
				BooleanDescriptor t = store.create(BooleanDescriptor.class);
				t.setValue(false);
				to = Optional.of(t);
			} else if ("false".equals(text)) {
				BooleanDescriptor f = store.create(BooleanDescriptor.class);
				f.setValue(false);
				to = Optional.of(f);
			} else if ("null".equals(text)) {
				NullDescriptor f = store.create(NullDescriptor.class);
				to = Optional.of(f);
			} else {
				StringDescriptor string = store.create(StringDescriptor.class);
				String value = text.substring(1, text.length() - 1);
				string.setValue(value);
				to = Optional.of(string);
			}
		} else {
			System.err.println("Found not expected literal...");

		}
		return to.orElse(null);
	}

}
