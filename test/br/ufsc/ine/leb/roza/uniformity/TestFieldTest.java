package br.ufsc.ine.leb.roza.uniformity;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestField;
import br.ufsc.ine.leb.roza.TestMethod;

public class TestFieldTest {

	@Test
	public void getTestClass() throws Exception {
		Statement inicializationStatement = new Statement("sut = new Sut();");
		Statement assertStatement = new Statement("assertEquals(0, sut.get(0));");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(inicializationStatement));
		TestMethod testMethod = new TestMethod("test", Arrays.asList(assertStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		List<Statement> fields = Arrays.asList(inicializationStatement);
		TestField testField = new TestField(testClass, fields);
		assertEquals(testClass, testField.getTestClass());
		assertEquals(fields, testField.getFields());
	}

}
