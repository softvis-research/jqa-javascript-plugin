package org.jqassistant.contrib.plugin.javascript.scanner;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.jqassistant.contrib.plugin.javascript.scanner.visitor.FqnCreator;
import org.junit.Test;

public class TestFqnCreator {

	@Test
	public void testCreateMultipleFqns() {
		FqnCreator testable = new FqnCreator("file.js:myvar:a");
		List<String> result = testable.getAllFqnsFrom("c");
		assertEquals(Arrays.asList("file.js:myvar:a:c", "file.js:myvar:c", "file.js:c"), result);
	}
	
	@Test
	public void testSimpleFqn() {
		FqnCreator testable = new FqnCreator("file.js");
		List<String> result = testable.getAllFqnsFrom("c");
		assertEquals(Arrays.asList("file.js:c"), result);
	}
}
