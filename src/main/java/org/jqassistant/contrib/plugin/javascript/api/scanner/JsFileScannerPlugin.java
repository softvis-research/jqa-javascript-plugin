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

import org.jqassistant.contrib.plugin.javascript.api.model.JsFileDescriptor;
import org.jqassistant.contrib.plugin.javascript.scanner.JsSourceParser;
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
 * JS file scanner
 * @author 
 */
@ScannerPlugin.Requires(FileDescriptor.class)
public class JsFileScannerPlugin extends AbstractScannerPlugin<FileResource, JsFileDescriptor> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JsFileScannerPlugin.class);
    
    public static final String JQASSISTANT_PLUGIN_JS_SUFFIXES = "jqassistant.plugin.js.suffixes";

    private static List<String> suffixes = asList("js");

    /**
     * check if file accepted by this plugin
     * @param item
     * @param path
     * @param scope
     * @return boolean
     * @throws IOException 
     */
    @Override
    public boolean accepts(final FileResource item, final String path, final Scope scope) throws IOException {
        int beginIndex = path.lastIndexOf(".");
        if(beginIndex > 0) {
            final String suffix = path.substring(beginIndex + 1).toLowerCase();

            boolean accepted = suffixes.contains(suffix);
            if(accepted) {
                LOGGER.info("ECMA accepted path "+path);
            }

            return accepted;
        }

        return false;
    }

    /**
     * scan js file
     * @param item
     * @param path
     * @param scope
     * @param scanner
     * @return jsFileDescriptor
     * @throws IOException 
     */
    @Override
    public JsFileDescriptor scan(final FileResource item, final String path, final Scope scope, final Scanner scanner) throws IOException {
        final Store store = scanner.getContext().getStore();
        final JsSourceParser sourceParser = new JsSourceParser (store);
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