package org.jqassistant.contrib.plugin.ecmascript.api.scanner;

import com.buschmais.jqassistant.core.scanner.api.Scope;

/**
 * Defines the scopes for Ecmascript SourceParser Plugin.
 */
public enum ECMAScriptScope implements Scope {
    SRC;

    @Override
    public String getPrefix() {
        return "ECMAScript";
    }

    @Override
    public String getName() {
        return name();
    }
}
