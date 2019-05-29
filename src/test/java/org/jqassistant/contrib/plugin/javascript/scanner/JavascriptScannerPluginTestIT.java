package org.jqassistant.contrib.plugin.javascript.scanner;

import java.io.File;

import org.junit.Test;

import com.buschmais.jqassistant.core.scanner.api.DefaultScope;
import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;

/*
 * Used for mvn install.
 * 
 * @author sh20xyqi
 */

public class JavascriptScannerPluginTestIT extends AbstractPluginIT {
	
	@Test
	public void testClassFile() {
		store.beginTransaction();
		// Scan the test JS file located as resource in the classpath
		File testFile = new File("src/test/resources/scripts/");

		getScanner().scan(testFile, "*.js", DefaultScope.NONE);
		store.commitTransaction();
		
	}
	
	
}

