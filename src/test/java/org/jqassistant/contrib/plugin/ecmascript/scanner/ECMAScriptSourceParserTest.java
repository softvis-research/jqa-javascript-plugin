/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.ecmascript.scanner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScripArrayDeclares;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScripObjectDeclares;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptArrayDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptBasicComponents;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptBooleanDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptClassDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptFileDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptFunctionDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptFunctionParameterDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptNullDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptNumberDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptObjectDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptStringDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptUndefinedDescriptor;
import org.jqassistant.contrib.plugin.ecmascript.api.model.ECMAScriptVariableDescriptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;

@RunWith(Parameterized.class)
public class ECMAScriptSourceParserTest {

	public String filePath;
	public Consumer<Map<Class<? extends Descriptor>, Descriptor>> f;

	public static class FileResource implements com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource {
		private final File entry;

		public FileResource(File entry) {
			this.entry = entry;
		}

		@Override
		public InputStream createStream() throws IOException {
			return new FileInputStream(entry);
		}

		@Override
		public File getFile() {
			return entry;
		}

		@Override
		public void close() {
		}
	}

	 @Parameters(name = "{2}: File {1}: ")
	public static Collection<Object[]> data() {
		
		Consumer<Map<Class<Descriptor>, Descriptor> > simpleVarI = (Map<Class<Descriptor>, Descriptor> mocks) -> {
			ECMAScriptVariableDescriptor mockDesc = (ECMAScriptVariableDescriptor) mocks.get(ECMAScriptVariableDescriptor.class);
			verify(mockDesc).setFullQualifiedName("test.js::i");
			verify(mockDesc).setName("i");
			ECMAScriptFileDescriptor fd = (ECMAScriptFileDescriptor) mocks.get(ECMAScriptFileDescriptor.class);
			verify(fd).getVariables();
		};
			
		Consumer<Map<Class<Descriptor>, Descriptor>> multipleVars = (Map<Class<Descriptor>, Descriptor> mocks) -> {
			ECMAScriptVariableDescriptor mockDesc = (ECMAScriptVariableDescriptor) mocks.get(ECMAScriptVariableDescriptor.class);
			verify(mockDesc).setFullQualifiedName("variable1.js::x");
			verify(mockDesc).setName("x");
			verify(mockDesc).setLine(5);
			verify(mockDesc).setFullQualifiedName("variable1.js::y");
			verify(mockDesc).setName("y");
			verify(mockDesc).setLine(6);
			verify(mockDesc).setFullQualifiedName("variable1.js::z");
			verify(mockDesc).setName("z");
			verify(mockDesc).setLine(7);
			ECMAScriptFileDescriptor fd = (ECMAScriptFileDescriptor) mocks.get(ECMAScriptFileDescriptor.class);
			verify(fd, times(3)).getVariables();
			
			ECMAScriptNumberDescriptor mockNum = (ECMAScriptNumberDescriptor) mocks.get(ECMAScriptNumberDescriptor.class);
			verify(mockNum).setValue(5d);
			verify(mockNum).setValue(6d);
//			ECMAScriptExpressionDescriptor mockExpr = (ECMAScriptExpressionDescriptor ) mocks.get(ECMAScriptExpressionDescriptor.class);
//			verify(mockExpr).setValue("x + y;");
			verify(mockDesc, times(2)).getLiterals();
		};
		
		Consumer<Map<Class<Descriptor>, Descriptor>> multipleVars2 = (Map<Class<Descriptor>, Descriptor> mocks) -> {
			
			ECMAScriptNumberDescriptor mockNum = (ECMAScriptNumberDescriptor) mocks.get(ECMAScriptNumberDescriptor.class);
			ECMAScriptVariableDescriptor mockDesc = (ECMAScriptVariableDescriptor) mocks.get(ECMAScriptVariableDescriptor.class);
			ECMAScriptStringDescriptor mockString = (ECMAScriptStringDescriptor) mocks.get(ECMAScriptStringDescriptor.class);
			ECMAScriptUndefinedDescriptor mockUndefined = (ECMAScriptUndefinedDescriptor) mocks.get(ECMAScriptUndefinedDescriptor.class);
			verify(mockDesc).setFullQualifiedName("variable2.js::pi");
			verify(mockDesc).setName("pi");
			verify(mockNum).setValue(3.14d);
			verify(mockDesc).setFullQualifiedName("variable2.js::person");
			verify(mockDesc).setName("person");
			verify(mockString).setValue("John Doe");
			verify(mockDesc).setFullQualifiedName("variable2.js::answer");
			verify(mockDesc).setName("answer");
			verify(mockString).setValue("Yes I am!");
			verify(mockDesc).setFullQualifiedName("variable2.js::u");
			
			
			verify(mockDesc).setName("u");
			verify(mockDesc).setFullQualifiedName("variable2.js::a");
			verify(mockDesc).setName("a");
			verify(mockDesc).setFullQualifiedName("variable2.js::car");
			verify(mockDesc).setName("car");
			verify(mockDesc).setFullQualifiedName("variable2.js::add");
			verify(mockDesc).setName("add");
			verify(mockDesc).setFullQualifiedName("variable2.js::c");
			verify(mockDesc).setName("c");
			verify(mockDesc).setFullQualifiedName("variable2.js::z");
			verify(mockDesc).setName("z");
			verify(mockDesc).setFullQualifiedName("variable2.js::veryBig");
			verify(mockDesc).setName("veryBig");
			verify(mockDesc).setFullQualifiedName("variable2.js::verySmall");
			verify(mockDesc).setName("verySmall");
			verify(mockDesc, times(17)).getLiterals();
		};
		
		Consumer<Map<Class<Descriptor>, Descriptor>> reassignVars = (Map<Class<Descriptor>, Descriptor> mocks) -> {
			ECMAScriptVariableDescriptor mockDesc = (ECMAScriptVariableDescriptor) mocks
					.get(ECMAScriptVariableDescriptor.class);
			
			verify(mockDesc).setFullQualifiedName("variable3.js::author");
			verify(mockDesc).setName("author");
			
		};
	
		Consumer<Map<Class<Descriptor>, Descriptor>> varWithFunc = (Map<Class<Descriptor>, Descriptor> mocks) -> {
			ECMAScriptVariableDescriptor mockDesc = (ECMAScriptVariableDescriptor) mocks
					.get(ECMAScriptVariableDescriptor.class);
			verify(mockDesc).setFullQualifiedName("variableWithFunction.js::f");
			verify(mockDesc).setName("f");
			verify(mockDesc).setFullQualifiedName("variableWithFunction.js::f::<anonymous>::n");
			verify(mockDesc).setName("n");
			verify(mockDesc).getFunctions();
			
			ECMAScriptFunctionDescriptor mockFunc = (ECMAScriptFunctionDescriptor) mocks.get(ECMAScriptFunctionDescriptor.class);
			
			verify(mockFunc).setFullQualifiedName("variableWithFunction.js::f::<anonymous>");
			verify(mockFunc).setName("<anonymous>");
			verify(mockFunc).getVariables();
		};
		
		Consumer<Map<Class<Descriptor>, Descriptor> > function1 = (Map<Class<Descriptor>, Descriptor> mocks) -> {
			ECMAScriptFunctionDescriptor mockDescFunc = (ECMAScriptFunctionDescriptor) mocks.get(ECMAScriptFunctionDescriptor.class);
			verify(mockDescFunc).setFullQualifiedName("function1.js::pow");
			verify(mockDescFunc).setName("pow");
			verify(mockDescFunc).setFullQualifiedName("function1.js::pow::iterate::<anonymous>");
			verify(mockDescFunc).setName("<anonymous>");
			ECMAScriptVariableDescriptor mockDesc = (ECMAScriptVariableDescriptor) mocks.get(ECMAScriptVariableDescriptor.class);
			verify(mockDesc).setFullQualifiedName("function1.js::pow::iterate");
			verify(mockDesc).setFullQualifiedName("function1.js::pow::ret");
			verify(mockDesc).setFullQualifiedName("function1.js::pow::iterate::<anonymous>::i");
			verify(mockDesc).setFullQualifiedName("function1.js::x");
			verify(mockDesc, times(1)).getFunctions();
			
			ECMAScriptFunctionParameterDescriptor mockParam = (ECMAScriptFunctionParameterDescriptor) mocks.get(ECMAScriptFunctionParameterDescriptor.class);
			verify(mockParam).setFullQualifiedName("function1.js::pow::base");
			verify(mockParam).setFullQualifiedName("function1.js::pow::exp");
			verify(mockParam).setName("base");
			verify(mockParam).setName("exp");
			verify(mockParam).setIndex(0);
			verify(mockParam).setIndex(1);
			
			verify(mockDescFunc, times(2)).getParameters();
			
			verify(mockDescFunc).setLine(1);
			verify(mockDescFunc).setLine(4);
			
		};
		
		Consumer<Map<Class<Descriptor>, Descriptor> > function2 = (Map<Class<Descriptor>, Descriptor> mocks) -> {
			ECMAScriptFunctionDescriptor mockDescFunc = (ECMAScriptFunctionDescriptor) mocks.get(ECMAScriptFunctionDescriptor.class);
			verify(mockDescFunc).setFullQualifiedName("function2.js::def");
			verify(mockDescFunc).setFullQualifiedName("function2.js::def::a::<anonymous>");
			verify(mockDescFunc).setName("<anonymous>");
			verify(mockDescFunc).setName("def");
			
			ECMAScriptFunctionParameterDescriptor mockParam = (ECMAScriptFunctionParameterDescriptor) mocks.get(ECMAScriptFunctionParameterDescriptor.class);
			verify(mockParam).setFullQualifiedName("function2.js::def::a");
			verify(mockParam).setName("a");
			verify(mockParam).setIndex(0);
		};
		
		Consumer<Map<Class<Descriptor>, Descriptor> > array = (Map<Class<Descriptor>, Descriptor> mocks) -> {
			ECMAScriptVariableDescriptor mockDesc = (ECMAScriptVariableDescriptor) mocks.get(ECMAScriptVariableDescriptor.class);
			verify(mockDesc).setFullQualifiedName("object1.js::car");
			verify(mockDesc).setName("car");

			ECMAScriptArrayDescriptor mockArray = (ECMAScriptArrayDescriptor) mocks.get(ECMAScriptArrayDescriptor.class);
			verify(mockArray).setFullQualifiedName("object1.js::vars::[array]");
			
			verify(mockArray, times(2)).getLiterals();
			// FIXME verify(mockArray, times(1)).getVariables();
			
		};
		
		Consumer<Map<Class<Descriptor>, Descriptor> > object1 = (Map<Class<Descriptor>, Descriptor> mocks) -> {
			ECMAScriptVariableDescriptor mockDesc = (ECMAScriptVariableDescriptor) mocks.get(ECMAScriptVariableDescriptor.class);
			verify(mockDesc).setFullQualifiedName("object2.js::car");
			verify(mockDesc).setName("car");

			ECMAScriptObjectDescriptor mockObj = (ECMAScriptObjectDescriptor) mocks.get(ECMAScriptObjectDescriptor.class);
			verify(mockObj).setFullQualifiedName("object2.js::car::[object]");
			
			verify(mockObj, times(3)).getLiterals();
			
		};
		

		Consumer<Map<Class<Descriptor>, Descriptor> > object5 = (Map<Class<Descriptor>, Descriptor> mocks) -> {
			ECMAScriptVariableDescriptor mockDesc = (ECMAScriptVariableDescriptor) mocks.get(ECMAScriptVariableDescriptor.class);
			verify(mockDesc).setFullQualifiedName("object5.js::person");
			verify(mockDesc).setName("person");

			ECMAScriptObjectDescriptor mockObj = (ECMAScriptObjectDescriptor) mocks.get(ECMAScriptObjectDescriptor.class);
			verify(mockObj).setFullQualifiedName("object5.js::person::[object]");
			
			verify(mockObj, times(3)).getLiterals();
			verify(mockObj, times(1)).getFunctions();
			
		};
	
		Consumer<Map<Class<Descriptor>, Descriptor> > class3 = (Map<Class<Descriptor>, Descriptor> mocks) -> {
			ECMAScriptClassDescriptor mockClass = (ECMAScriptClassDescriptor) mocks.get(ECMAScriptClassDescriptor.class);
			verify(mockClass).setFullQualifiedName("class3.js::Polygon");
			verify(mockClass).setName("Polygon");
			verify(mockClass).setSuperClass("Object");
			List<ECMAScriptFunctionDescriptor> functions = verify(mockClass, times(4)).getFunctions();
			
		};
		
		return Arrays.asList(
		new Object[][] { 
			{ "src/test/resources/test.js",  simpleVarI, "simple var i"},
			{ "src/test/resources/scripts/variable/variable1.js",  multipleVars, "multiple vars"},
			{ "src/test/resources/scripts/variable/variable2.js",  multipleVars2, "multiple vars2"},
			{ "src/test/resources/scripts/variable/variable3.js",  reassignVars, "reassign vars"}, 
			
			{ "src/test/resources/scripts/variable/variableWithFunction.js",  varWithFunc, "var with func"},
			
			{ "src/test/resources/scripts/function/function1.js",  function1, "functionBasic"},
			{ "src/test/resources/scripts/function/function2.js",  function2, "function Paramters"},
			{ "src/test/resources/scripts/object/object1.js",  array, "array"},
			{ "src/test/resources/scripts/object/object2.js",  object1, "object"},
			{ "src/test/resources/scripts/object/object5.js",  object5, "object with func"},
			
			{ "src/test/resources/scripts/class/class3.js",  class3, "classes complex"},
		});
	}


	public ECMAScriptSourceParserTest(String filePath, Consumer<Map<Class<? extends Descriptor>, Descriptor>> f, String testName) {
		this.filePath = filePath;
		this.f = f;
	}

	@Test
	public void testParserIsParsingJavascript() throws Exception {
		// here starts the action
		Store mockedStore = mock(Store.class);
		// the map will be used to compare the mock objects returned by the store at the test cases
		Map<Class<? extends Descriptor>, Descriptor> mocks = new HashMap<Class<? extends Descriptor>, Descriptor>();
		// now we create the mock objects
		addMockToStore(ECMAScriptVariableDescriptor.class, mocks, mockedStore);
		addMockToStore(ECMAScriptFunctionDescriptor.class, mocks, mockedStore);
		addMockToStore(ECMAScriptFileDescriptor.class, mocks, mockedStore);
		addMockToStore(ECMAScriptFunctionParameterDescriptor.class, mocks, mockedStore);
		
		addMockToStore(ECMAScriptClassDescriptor.class, mocks, mockedStore);
		
		addMockToStore(ECMAScriptArrayDescriptor.class, mocks, mockedStore);
		addMockToStore(ECMAScriptObjectDescriptor.class, mocks, mockedStore);
		
		addMockToStore(ECMAScriptNumberDescriptor.class, mocks, mockedStore);
		addMockToStore(ECMAScriptStringDescriptor.class, mocks, mockedStore);
		addMockToStore(ECMAScriptBooleanDescriptor.class, mocks, mockedStore);
		addMockToStore(ECMAScriptUndefinedDescriptor.class, mocks, mockedStore);
		addMockToStore(ECMAScriptNullDescriptor.class, mocks, mockedStore);

		when(mockedStore.create(any(ECMAScriptArrayDescriptor.class), eq(ECMAScripArrayDeclares.class), any(FullQualifiedNameDescriptor.class))).thenReturn(mock(ECMAScripArrayDeclares.class));
		when(mockedStore.create(any(ECMAScriptObjectDescriptor.class), eq(ECMAScripObjectDeclares.class), any(FullQualifiedNameDescriptor.class))).thenReturn(mock(ECMAScripObjectDeclares.class));
	
		// lets run the parser
		ECMAScriptSourceParser testable = new ECMAScriptSourceParser(mockedStore);
		FileResource fr = new FileResource(new File(filePath));
		testable.parseFile(fr);
		
		// lets run the test
		f.accept(mocks);

	}
	
	public static class ListAnswer <T extends ECMAScriptBasicComponents> implements Answer<List<T>> {
		ArrayList<T> l = new ArrayList<>();
		@Override
		public List<T> answer(InvocationOnMock invocation) throws Throwable {
			return l;
		}
	};
	
	private <T extends Descriptor> T addMockToStore(Class<T> baseClass, Map<Class<? extends Descriptor>, Descriptor> mocks, Store mockedStore) {
		T mock = mock(baseClass);
		if(mock instanceof ECMAScriptBasicComponents) {
			ECMAScriptBasicComponents c = (ECMAScriptBasicComponents) mock;
			when(c.getVariables()).thenAnswer(new ListAnswer<>());
			when(c.getClasses()).thenAnswer(new ListAnswer<>());
			when(c.getFunctions()).thenAnswer(new ListAnswer<>());
			when(c.getObjects()).thenAnswer(new ListAnswer<>());
			when(c.getLiterals()).thenAnswer(new ListAnswer<>());
		}
		mocks.put(baseClass, mock);
		when(mockedStore.create(baseClass)).thenReturn(mock);
		
		return mock;
	}

}