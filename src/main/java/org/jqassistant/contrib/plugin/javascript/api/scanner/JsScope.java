package org.jqassistant.contrib.plugin.javascript.api.scanner;

import com.buschmais.jqassistant.core.scanner.api.Scope;

/**
 * Defines the scope for the JavaScript Scanner Plugin, which is JavaScript.
 * 
 * @author sh20xyqi
 */
public enum JsScope implements Scope {
    SRC;

    @Override
    public String getPrefix() {
        return "JavaScript";
    }

    @Override
    public String getName() {
        return name();
    }
}
