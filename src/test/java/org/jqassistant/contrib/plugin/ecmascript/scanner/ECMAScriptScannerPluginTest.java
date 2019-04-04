package org.jqassistant.contrib.plugin.ecmascript.scanner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptFileDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptVariableDescriptor;
import org.junit.Ignore;
import org.junit.Test;

import com.buschmais.jqassistant.core.scanner.api.DefaultScope;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.core.store.impl.EmbeddedGraphStore;
import com.buschmais.jqassistant.neo4j.backend.bootstrap.EmbeddedNeo4jServer;
import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;
/*
@Ignore
public class ECMAScriptScannerPluginTest extends AbstractPluginIT {

	//Integration Test Kommen nicht in die neo4j Datenbank. Schreiben eine neo4j datenbank in einen ordner im target ordner. Werden permanent überschrieben. 
	//Ziel ist es, dass das Plugin für alle FUnktionen / Objekte usw. funktioniert (Integratitionstest)
	
	@Test
	public void scanECMAFile() {
		store.beginTransaction();
		// Scan the test JS file located as resource in the classpath
		File testFile = new File("src/test/resources/test.js");

		// Scan the CSV file and assert that the returned descriptor is a
		// CSVFileDescriptor
		ECMAScriptFileDescriptor scanResult = getScanner().scan(testFile, "/test.js", DefaultScope.NONE);
		assertThat(scanResult,
				CoreMatchers.<Descriptor>instanceOf(ECMAScriptFileDescriptor.class));

		// Determine the ECMAScriptFileDescriptor by executing a Cypher query
		TestResult testResult = query("MATCH (file:ECMAScript:File) RETURN file ");
		List<ECMAScriptFileDescriptor> jsFiles = testResult.getColumn("file");
		assertThat(jsFiles.size(), equalTo(1));
		assertThat(jsFiles.get(0).getVariables().size(), equalTo(1));
		ECMAScriptVariableDescriptor variable = jsFiles.get(0).getVariables().get(0);
		assertEquals("test.js::i", variable.getFullQualifiedName());
		assertEquals("i", variable.getName());
		store.commitTransaction();

	}

	
	@Test
	public void scanComplexFunctionECMAFile() {
		store.beginTransaction();
		// Scan the test JS file located as resource in the classpath
		File testFile = new File("src/test/resources/scripts/function/function1.js");

		// Scan the CSV file and assert that the returned descriptor is a
		// CSVFileDescriptor
		ECMAScriptFileDescriptor scanResult = getScanner().scan(testFile, "/function1.js", DefaultScope.NONE);
		assertThat(scanResult,
				CoreMatchers.<Descriptor>instanceOf(ECMAScriptFileDescriptor.class));

		// Determine the ECMAScriptFileDescriptor by executing a Cypher query
		TestResult testResult = query("MATCH (file:ECMAScript:File) RETURN file ");
		List<ECMAScriptFileDescriptor> jsFiles = testResult.getColumn("file");
		assertThat(jsFiles.size(), equalTo(1));
		store.commitTransaction();

		
//		
//		EmbeddedGraphStore embeddedGraphStore = (EmbeddedGraphStore) store;
//		EmbeddedNeo4jServer server = embeddedGraphStore.getServer();
//        server.start();
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            
//        } finally {
//            server.stop();
//        }
	}

	@Test
	public void testScanObjectFile() {
		store.beginTransaction();
		// Scan the test JS file located as resource in the classpath
		File testFile = new File("src/test/resources/scripts/integration/integration1.js");

		// Scan the CSV file and assert that the returned descriptor is a
		// CSVFileDescriptor
		ECMAScriptFileDescriptor scanResult = getScanner().scan(testFile, "/integration1.js", DefaultScope.NONE);
		assertThat(scanResult,
				CoreMatchers.<Descriptor>instanceOf(ECMAScriptFileDescriptor.class));

		// Determine the ECMAScriptFileDescriptor by executing a Cypher query
		TestResult testResult = query("MATCH (file:ECMAScript:File) RETURN file ");
		List<ECMAScriptFileDescriptor> jsFiles = testResult.getColumn("file");
		assertThat(jsFiles.size(), equalTo(1));
		store.commitTransaction();
		
	}

	@Test
	public void testFile() {
		store.beginTransaction();
		// Scan the test JS file located as resource in the classpath
		File testFile = new File("src/test/resources/scripts/object/object6.js");

		// Scan the CSV file and assert that the returned descriptor is a
		// CSVFileDescriptor
		ECMAScriptFileDescriptor scanResult = getScanner().scan(testFile, "/object6.js", DefaultScope.NONE);
		assertThat(scanResult,
				CoreMatchers.<Descriptor>instanceOf(ECMAScriptFileDescriptor.class));

		// Determine the ECMAScriptFileDescriptor by executing a Cypher query
		TestResult testResult = query("MATCH (file:ECMAScript:File) RETURN file ");
		List<ECMAScriptFileDescriptor> jsFiles = testResult.getColumn("file");
		assertThat(jsFiles.size(), equalTo(1));
		store.commitTransaction();
		
	}
	
	@Test
	public void testClassFile() {
		store.beginTransaction();
		// Scan the test JS file located as resource in the classpath
		File testFile = new File("src/test/resources/scripts/class/class3.js");

		// Scan the CSV file and assert that the returned descriptor is a
		// CSVFileDescriptor
		ECMAScriptFileDescriptor scanResult = getScanner().scan(testFile, "/class3.js", DefaultScope.NONE);
		assertThat(scanResult,
				CoreMatchers.<Descriptor>instanceOf(ECMAScriptFileDescriptor.class));

		// Determine the ECMAScriptFileDescriptor by executing a Cypher query
		TestResult testResult = query("MATCH (file:ECMAScript:File) RETURN file ");
		List<ECMAScriptFileDescriptor> jsFiles = testResult.getColumn("file");
		assertThat(jsFiles.size(), equalTo(1));
		store.commitTransaction();
		
	}
	
}
*/
