package org.jqassistant.contrib.plugin.javascript.scanner;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.jqassistant.contrib.plugin.javascript.api.model.JavaScriptFileDescriptor;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptLexer;
import org.jqassistant.contrib.plugin.javascript.parser.JavaScriptParser;
import org.jqassistant.contrib.plugin.javascript.scanner.listener.JavaScriptListener;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;

/**
 * Class that contains the logic for processing JavaScript source code artifacts.
 * 
 * @author sh20xyqi
 * 
 */
public class JavaScriptSourceParser {
    private final Store store;

    public JavaScriptSourceParser(final Store store) {
        this.store = store;
    }

    /**
     * Function that runs through the individual processing steps of the plugin.
     * @param item The passed file.
     * @throws IOException 
     */
    public JavaScriptFileDescriptor parseFile(final FileResource item) throws IOException {
        	JavaScriptFileDescriptor fileDescriptor = store.create(JavaScriptFileDescriptor.class);
        	String fileName = item.getFile().getName();
			fileDescriptor.setFileName(fileName);
			fileDescriptor.setFilepath(item.getFile().getAbsolutePath());
        	fileDescriptor.setLine(0); 
        	fileDescriptor.setFullQualifiedName(fileName);
            InputStream inputStream = item.createStream();
            Lexer lexer = new JavaScriptLexer(CharStreams.fromStream(inputStream));
            TokenStream tokenStream = new CommonTokenStream(lexer);
            JavaScriptParser parser = new JavaScriptParser(tokenStream); 
            ParseTreeWalker treeWalker = new ParseTreeWalker();
            ParseTree tree = parser.program();
            treeWalker.walk(new JavaScriptListener(store, fileDescriptor), tree);
            return fileDescriptor;
    }
    
   
}