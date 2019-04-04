/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.ecmascript.scanner;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptLexer;
import org.jqassistant.contrib.plugin.ecmascript.JavaScriptParser;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptFileDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.scanner.visitor.JQAssistantECMAScriptVistor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;

/**
 *
 * @author 
 */
public class ECMAScriptSourceParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ECMAScriptSourceParser.class);
    private final Store store;

    public ECMAScriptSourceParser(final Store store) {
        this.store = store;
    }

    /**
     * parse js file
     * @param item 
     * @throws IOException 
     */
    public ECMAScriptFileDescriptor parseFile(final FileResource item) throws IOException {
        	ECMAScriptFileDescriptor fileDescriptor = store.create(ECMAScriptFileDescriptor.class);
        	String fqnBase = item.getFile().getName();
        	fileDescriptor.setFileName(item.getFile().getName());
        	fileDescriptor.setLine(1);
            InputStream inputStream = item.createStream();
            Lexer lexer = new JavaScriptLexer(CharStreams.fromStream(inputStream));
            TokenStream tokenStream = new CommonTokenStream(lexer);
            JavaScriptParser parser = new JavaScriptParser(tokenStream); 
            ParseTree tree = parser.program();
            JQAssistantECMAScriptVistor visitor = new JQAssistantECMAScriptVistor(store, fqnBase, fileDescriptor);
			tree.accept(visitor);
            return fileDescriptor;
    }
    
   
}