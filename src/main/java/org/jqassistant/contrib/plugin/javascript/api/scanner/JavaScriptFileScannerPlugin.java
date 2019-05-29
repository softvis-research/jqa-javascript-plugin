/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.javascript.api.scanner;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jqassistant.contrib.plugin.javascript.api.model.JavaScriptFileDescriptor;
import org.jqassistant.contrib.plugin.javascript.scanner.JavaScriptSourceParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;

/**
 * JS file Scanner
 * @author sh20xyqi
 */
@ScannerPlugin.Requires(FileDescriptor.class)
public class JavaScriptFileScannerPlugin extends AbstractScannerPlugin<FileResource, JavaScriptFileDescriptor> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaScriptFileScannerPlugin.class);
    
    public static final String JQASSISTANT_PLUGIN_JS_SUFFIXES = "jqassistant.plugin.js.suffixes";

    private static List<String> suffixes = asList("js");

    /**
     * Routine to check whether the passed file can be processed by the plugin.
     * @param item The passed file.
     * @param path The absolute path to the file.
     * @param scope The passed lexical scope of the passed file.
     * @return boolean State whether the passed file would be accepted by the plugin.
     * @throws IOException 
     */
    @Override
    public boolean accepts(final FileResource item, final String path, final Scope scope) throws IOException {
        int beginIndex = path.lastIndexOf(".");
        if(beginIndex > 0) {
            final String suffix = path.substring(beginIndex + 1).toLowerCase();

            boolean accepted = suffixes.contains(suffix);
            if(accepted) {
                LOGGER.info("JS accepted path " + path);
            }

            return accepted;
        }

        return false;
    }

    /**
     * Function that calls the Listener to scan the transferred, checked file.
     * @param item The passed file.
     * @param path The absolute path to the file.
     * @param scope The passed lexical scope of the passed file.
     * @param scanner The passed scanner which shall be used to scan the file.
     * @return jsFileDescriptor The used file descriptor.
     * @throws IOException 
     */
    @Override
    public JavaScriptFileDescriptor scan(final FileResource item, final String path, final Scope scope, final Scanner scanner) throws IOException {
        final Store store = scanner.getContext().getStore();
        final JavaScriptSourceParser sourceParser = new JavaScriptSourceParser (store);
        return sourceParser.parseFile(item);
    }

    /**
     * configuration
     */
    @Override
    protected void configure() {
        super.configure();

        if(getProperties().containsKey(JQASSISTANT_PLUGIN_JS_SUFFIXES)) {
            suffixes = new ArrayList<>();
            String serializedSuffixes = (String) getProperties().get(JQASSISTANT_PLUGIN_JS_SUFFIXES);
            for (String suffix : serializedSuffixes.split(",")) {
                suffixes.add(suffix.toLowerCase().trim());
            }
        }

        LOGGER.info(String.format("js plugin looks for files with suffixes '%s'", suffixes.toString()));
    }
    
}