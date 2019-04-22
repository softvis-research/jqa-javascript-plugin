/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.javascript.scanner;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.javascript.JavaScriptLexer;
import org.jqassistant.contrib.plugin.javascript.JavaScriptParser;
import org.jqassistant.contrib.plugin.javascript.api.model.JsFileDescriptor;
import org.jqassistant.contrib.plugin.javascript.scanner.visitor.JavascriptVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;

/**
 *
 * @author 
 */
public class JsSourceParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsSourceParser.class);
    private final Store store;

    public JsSourceParser(final Store store) {
        this.store = store;
    }

    /**
     * parse js file
     * @param item 
     * @throws IOException 
     */
    public JsFileDescriptor parseFile(final FileResource item) throws IOException {
        	JsFileDescriptor fileDescriptor = store.create(JsFileDescriptor.class);
        	String fqnBase = item.getFile().getName();
        	fileDescriptor.setFileName(item.getFile().getName());
        	fileDescriptor.setLine(1);
            InputStream inputStream = item.createStream();
            Lexer lexer = new JavaScriptLexer(CharStreams.fromStream(inputStream));
            TokenStream tokenStream = new CommonTokenStream(lexer);
            JavaScriptParser parser = new JavaScriptParser(tokenStream); 
            ParseTree tree = parser.program();
            JavascriptVisitor visitor = new JavascriptVisitor(store, fqnBase, fileDescriptor);
			tree.accept(visitor);
            return fileDescriptor;
    }
    
   
}