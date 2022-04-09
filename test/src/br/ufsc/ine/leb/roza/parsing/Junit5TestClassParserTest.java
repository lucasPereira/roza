package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.loading.TextFile;
import br.ufsc.ine.leb.roza.utils.CodeStringBuilder;

class Junit5TestClassParserTest {

	@Test
	void setupMethod() throws Exception {
		Junit5TestClassParser parser = new Junit5TestClassParser();
		CodeStringBuilder code = new CodeStringBuilder();
		code.add("public class ExampleTest {");
		code.tab().add("@BeforeEach public void setup() {}");
		code.tab().add("@Test public void test() {}");
		code.add("}");
		TextFile textFile = new TextFile("ExampleTest.java", code.toString());
		List<TestClass> classes = parser.parse(Arrays.asList(textFile));

		assertEquals(1, classes.size());
		assertEquals("ExampleTest", classes.get(0).getName());
		assertEquals(0, classes.get(0).getFields().size());
		assertEquals(1, classes.get(0).getSetupMethods().size());
		assertEquals(1, classes.get(0).getTestMethods().size());
		assertEquals("setup", classes.get(0).getSetupMethods().get(0).getName());
		assertEquals(0, classes.get(0).getSetupMethods().get(0).getStatements().size());
		assertEquals("test", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(0, classes.get(0).getTestMethods().get(0).getStatements().size());
	}

}
