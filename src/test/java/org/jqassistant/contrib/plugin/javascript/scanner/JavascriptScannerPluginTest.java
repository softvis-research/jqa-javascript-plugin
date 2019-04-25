package org.jqassistant.contrib.plugin.javascript.scanner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.jqassistant.contrib.plugin.javascript.api.model.JsFileDescriptor;
import org.jqassistant.contrib.plugin.javascript.api.model.VariableDescriptor;
import org.junit.Ignore;
import org.junit.Test;

import com.buschmais.jqassistant.core.scanner.api.DefaultScope;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;

public class JavascriptScannerPluginTest extends AbstractPluginIT {

	//Integration Test Kommen nicht in die neo4j Datenbank. Schreiben eine neo4j datenbank in einen ordner im target ordner. Werden permanent überschrieben. 
	//Ziel ist es, dass das Plugin für alle FUnktionen / Objekte usw. funktioniert (Integratitionstest)
	
//	@Test
//	public void scanBasicFile() {
//		store.beginTransaction();
//		// Scan the test JS file located as resource in the classpath
//		File testFile = new File("src/test/resources/test.js");
//
//		// Scan the CSV file and assert that the returned descriptor is a
//		// CSVFileDescriptor
//		JsFileDescriptor scanResult = getScanner().scan(testFile, "/test.js", DefaultScope.NONE);
//		assertThat(scanResult,
//				CoreMatchers.<Descriptor>instanceOf(JsFileDescriptor.class));
//
//		// Determine the ECMAScriptFileDescriptor by executing a Cypher query
//		TestResult testResult = query("MATCH (file:JS:File) RETURN file ");
//		List<JsFileDescriptor> jsFiles = testResult.getColumn("file");
//		assertThat(jsFiles.size(), equalTo(1));
//		assertThat(jsFiles.get(0).getVariables().size(), equalTo(1));
//		VariableDescriptor variable = jsFiles.get(0).getVariables().get(0);
//		assertEquals("test.js:i", variable.getFullQualifiedName());
//		assertEquals("i", variable.getName());
//		store.commitTransaction();
//
//	}
//
//	
//	@Test
//	public void testComplexFunctionJs() {
//		store.beginTransaction();
//		// Scan the test JS file located as resource in the classpath
//		File testFile = new File("src/test/resources/scripts/function/function1.js");
//
//		// Scan the CSV file and assert that the returned descriptor is a
//		// CSVFileDescriptor
//		JsFileDescriptor scanResult = getScanner().scan(testFile, "/function1.js", DefaultScope.NONE);
//		assertThat(scanResult,
//				CoreMatchers.<Descriptor>instanceOf(JsFileDescriptor.class));
//
//		// Determine the ECMAScriptFileDescriptor by executing a Cypher query
//		TestResult testResult = query("MATCH (file:JS:File) RETURN file ");
//		List<JsFileDescriptor> jsFiles = testResult.getColumn("file");
//		assertThat(jsFiles.size(), equalTo(1));
//		store.commitTransaction();
//
//	}
//
//	@Test
//	public void testObjectFile() {
//		store.beginTransaction();
//		// Scan the test JS file located as resource in the classpath
//		File testFile = new File("src/test/resources/scripts/integration/integration1.js");
//
//		// Scan the CSV file and assert that the returned descriptor is a
//		// CSVFileDescriptor
//		JsFileDescriptor scanResult = getScanner().scan(testFile, "/integration1.js", DefaultScope.NONE);
//		assertThat(scanResult,
//				CoreMatchers.<Descriptor>instanceOf(JsFileDescriptor.class));
//
//		// Determine the ECMAScriptFileDescriptor by executing a Cypher query
//		TestResult testResult = query("MATCH (file:JS:File) RETURN file ");
//		List<JsFileDescriptor> jsFiles = testResult.getColumn("file");
//		assertThat(jsFiles.size(), equalTo(1));
//		store.commitTransaction();
//		
//	}
//
//	@Test
//	public void testClassFile() {
//		store.beginTransaction();
//		// Scan the test JS file located as resource in the classpath
//		File testFile = new File("src/test/resources/scripts/class/class3.js");
//
//		// Scan the CSV file and assert that the returned descriptor is a
//		// CSVFileDescriptor
//		JsFileDescriptor scanResult = getScanner().scan(testFile, "/class3.js", DefaultScope.NONE);
//		assertThat(scanResult,
//				CoreMatchers.<Descriptor>instanceOf(JsFileDescriptor.class));
//
//		// Determine the ECMAScriptFileDescriptor by executing a Cypher query
//		TestResult testResult = query("MATCH (file:JS:File) RETURN file ");
//		List<JsFileDescriptor> jsFiles = testResult.getColumn("file");
//		assertThat(jsFiles.size(), equalTo(1));
//		store.commitTransaction();
//		
//	}
	
	@Test
	public void scanInvokes() {
		store.beginTransaction();
		// Scan the test JS file located as resource in the classpath
		File testFile = new File("src/test/resources/scripts/variable/variable4.js");

		// Scan the CSV file and assert that the returned descriptor is a
		// CSVFileDescriptor
		JsFileDescriptor scanResult = getScanner().scan(testFile, "/variable4.js", DefaultScope.NONE);
		assertThat(scanResult,
				CoreMatchers.<Descriptor>instanceOf(JsFileDescriptor.class));

		// Determine the ECMAScriptFileDescriptor by executing a Cypher query
		TestResult testResult = query("MATCH (file:JS:File) RETURN file ");
		List<JsFileDescriptor> jsFiles = testResult.getColumn("file");
		assertThat(jsFiles.size(), equalTo(1));
		assertThat(jsFiles.get(0).getVariables().size(), equalTo(9));
	
		store.commitTransaction();

	}
	
}

