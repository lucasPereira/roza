package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.loading.TextFile;
import br.ufsc.ine.leb.roza.utils.CodeStringBuilder;

public class JavaTestClassParserTest {

	private JavaTestClassParser parser;
	private CodeStringBuilder code;

	@BeforeEach
	void setup() {
		parser = new JavaTestClassParser(org.junit.Before.class, org.junit.Test.class);
		code = new CodeStringBuilder();
	}

	@Test
	void emptyClass() throws Exception {
		code.add("public class ExampleTest {}");
		TextFile textFile = new TextFile("ExampleTest.java", code.toString());
		List<TestClass> classes = parser.parse(Arrays.asList(textFile));

		assertEquals(0, classes.size());
	}

	@Test
	void testMethod() throws Exception {
		code.add("public class ExampleTest {");
		code.tab().add("@Test public void test() {}");
		code.add("}");
		TextFile textFile = new TextFile("ExampleTest.java", code.toString());
		List<TestClass> classes = parser.parse(Arrays.asList(textFile));

		assertEquals(1, classes.size());
		assertEquals("ExampleTest", classes.get(0).getName());
		assertEquals(0, classes.get(0).getFields().size());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(1, classes.get(0).getTestMethods().size());
		assertEquals("test", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(0, classes.get(0).getTestMethods().get(0).getStatements().size());
	}

	@Test
	void setupMethod() throws Exception {
		code.add("public class ExampleTest {");
		code.tab().add("@Before public void setup() {}");
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

	@Test
	void fields() throws Exception {
		code.add("public class ExampleTest {");
		code.tab().add("private int a;");
		code.tab().add("private Integer b, c;");
		code.tab().add("private List<String> d;");
		code.tab().add("private Float e = 0.0f;");
		code.tab().add("@Test public void test() {}");
		code.add("}");
		TextFile textFile = new TextFile("ExampleTest.java", code.toString());
		List<TestClass> classes = parser.parse(Arrays.asList(textFile));

		assertEquals(1, classes.size());
		assertEquals("ExampleTest", classes.get(0).getName());
		assertEquals(5, classes.get(0).getFields().size());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(1, classes.get(0).getTestMethods().size());
		assertEquals("int", classes.get(0).getFields().get(0).getType());
		assertEquals("a", classes.get(0).getFields().get(0).getName());
		assertEquals("Integer", classes.get(0).getFields().get(1).getType());
		assertEquals("b", classes.get(0).getFields().get(1).getName());
		assertEquals("Integer", classes.get(0).getFields().get(2).getType());
		assertEquals("c", classes.get(0).getFields().get(2).getName());
		assertEquals("List<String>", classes.get(0).getFields().get(3).getType());
		assertEquals("d", classes.get(0).getFields().get(3).getName());
		assertEquals("Float", classes.get(0).getFields().get(4).getType());
		assertEquals("e", classes.get(0).getFields().get(4).getName());
		assertEquals("test", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(0, classes.get(0).getTestMethods().get(0).getStatements().size());
	}

	@Test
	void methodCallStatements() throws Exception {
		code.add("public class ExampleTest {");
		code.tab().add("@Test public void test() {");
		code.tab().tab().add("operation();");
		code.tab().tab().add("operation(0);");
		code.tab().tab().add("object.operation();");
		code.tab().tab().add("object.attribute.operation();");
		code.tab().tab().add("SomeClass.operation();");
		code.tab().tab().add("new SomeClass().operation();");
		code.tab().add("}");
		code.add("}");
		TextFile textFile = new TextFile("ExampleTest.java", code.toString());
		List<TestClass> classes = parser.parse(Arrays.asList(textFile));

		assertEquals(1, classes.size());
		assertEquals("ExampleTest", classes.get(0).getName());
		assertEquals(0, classes.get(0).getFields().size());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(1, classes.get(0).getTestMethods().size());
		assertEquals("test", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(6, classes.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("operation();", classes.get(0).getTestMethods().get(0).getStatements().get(0).getCode());
		assertEquals("operation(0);", classes.get(0).getTestMethods().get(0).getStatements().get(1).getCode());
		assertEquals("object.operation();", classes.get(0).getTestMethods().get(0).getStatements().get(2).getCode());
		assertEquals("object.attribute.operation();", classes.get(0).getTestMethods().get(0).getStatements().get(3).getCode());
		assertEquals("SomeClass.operation();", classes.get(0).getTestMethods().get(0).getStatements().get(4).getCode());
		assertEquals("new SomeClass().operation();", classes.get(0).getTestMethods().get(0).getStatements().get(5).getCode());

		StatementCollector collector = new StatementCollector(classes.get(0).getTestMethods().get(0).getStatements());
		List<AssignStatement> assignStatements = collector.getAssignStatements();
		List<GenericStatement> genericStatements = collector.getGenericStatements();
		List<MethodCallStatement> methodCallStatements = collector.getMethodCallStatements();
		List<VariableDeclarationStatement> variableDeclarationStatements = collector.getVariableDeclarationStatements();

		assertEquals(0, assignStatements.size());
		assertEquals(0, genericStatements.size());
		assertEquals(6, methodCallStatements.size());
		assertEquals(0, variableDeclarationStatements.size());
		assertEquals("operation();", methodCallStatements.get(0).getCode());
		assertEquals("operation(0);", methodCallStatements.get(1).getCode());
		assertEquals("object.operation();", methodCallStatements.get(2).getCode());
		assertEquals("object.attribute.operation();", methodCallStatements.get(3).getCode());
		assertEquals("SomeClass.operation();", methodCallStatements.get(4).getCode());
		assertEquals("new SomeClass().operation();", methodCallStatements.get(5).getCode());
	}

	@Test
	void variableDeclarationStatements() throws Exception {
		code.add("public class ExampleTest {");
		code.tab().add("@Test public void test() {");
		code.tab().tab().add("Integer variable1 = 1;");
		code.tab().tab().add("Integer variable2;");
		code.tab().tab().add("Integer variable3 = 3, variable4 = operation(4), variable5;");
		code.tab().add("}");
		code.add("}");
		TextFile textFile = new TextFile("ExampleTest.java", code.toString());
		List<TestClass> classes = parser.parse(Arrays.asList(textFile));

		assertEquals(1, classes.size());
		assertEquals("ExampleTest", classes.get(0).getName());
		assertEquals(0, classes.get(0).getFields().size());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(1, classes.get(0).getTestMethods().size());
		assertEquals("test", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(5, classes.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("Integer variable1 = 1;", classes.get(0).getTestMethods().get(0).getStatements().get(0).getCode());
		assertEquals("Integer variable2;", classes.get(0).getTestMethods().get(0).getStatements().get(1).getCode());
		assertEquals("Integer variable3 = 3;", classes.get(0).getTestMethods().get(0).getStatements().get(2).getCode());
		assertEquals("Integer variable4 = operation(4);", classes.get(0).getTestMethods().get(0).getStatements().get(3).getCode());
		assertEquals("Integer variable5;", classes.get(0).getTestMethods().get(0).getStatements().get(4).getCode());

		StatementCollector collector = new StatementCollector(classes.get(0).getTestMethods().get(0).getStatements());
		List<AssignStatement> assignStatements = collector.getAssignStatements();
		List<GenericStatement> genericStatements = collector.getGenericStatements();
		List<MethodCallStatement> methodCallStatements = collector.getMethodCallStatements();
		List<VariableDeclarationStatement> variableDeclarationStatements = collector.getVariableDeclarationStatements();

		assertEquals(0, assignStatements.size());
		assertEquals(0, genericStatements.size());
		assertEquals(0, methodCallStatements.size());
		assertEquals(5, variableDeclarationStatements.size());
		assertEquals("Integer variable1 = 1;", variableDeclarationStatements.get(0).getCode());
		assertEquals("Integer variable2;", variableDeclarationStatements.get(1).getCode());
		assertEquals("Integer variable3 = 3;", variableDeclarationStatements.get(2).getCode());
		assertEquals("Integer variable4 = operation(4);", variableDeclarationStatements.get(3).getCode());
		assertEquals("Integer variable5;", variableDeclarationStatements.get(4).getCode());
	}

	@Test
	void assignmentStatements() throws Exception {
		code.add("public class ExampleTest {");
		code.tab().add("private Integer fixture;");
		code.tab().add("@Test public void test() {");
		code.tab().tab().add("Integer localFixture1;");
		code.tab().tab().add("Integer localFixture2, localFixture3 = 3;");
		code.tab().tab().add("fixture = 0;");
		code.tab().tab().add("localFixture1 = 1;");
		code.tab().tab().add("localFixture2 = operation(2);");
		code.tab().tab().add("localFixture3 = localFixture3 - 1;");
		code.tab().add("}");
		code.add("}");
		TextFile textFile = new TextFile("ExampleTest.java", code.toString());
		List<TestClass> classes = parser.parse(Arrays.asList(textFile));

		assertEquals(1, classes.size());
		assertEquals("ExampleTest", classes.get(0).getName());
		assertEquals(1, classes.get(0).getFields().size());
		assertEquals("Integer", classes.get(0).getFields().get(0).getType());
		assertEquals("fixture", classes.get(0).getFields().get(0).getName());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(1, classes.get(0).getTestMethods().size());
		assertEquals("test", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(7, classes.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("Integer localFixture1;", classes.get(0).getTestMethods().get(0).getStatements().get(0).getCode());
		assertEquals("Integer localFixture2;", classes.get(0).getTestMethods().get(0).getStatements().get(1).getCode());
		assertEquals("Integer localFixture3 = 3;", classes.get(0).getTestMethods().get(0).getStatements().get(2).getCode());
		assertEquals("fixture = 0;", classes.get(0).getTestMethods().get(0).getStatements().get(3).getCode());
		assertEquals("localFixture1 = 1;", classes.get(0).getTestMethods().get(0).getStatements().get(4).getCode());
		assertEquals("localFixture2 = operation(2);", classes.get(0).getTestMethods().get(0).getStatements().get(5).getCode());
		assertEquals("localFixture3 = localFixture3 - 1;", classes.get(0).getTestMethods().get(0).getStatements().get(6).getCode());

		StatementCollector collector = new StatementCollector(classes.get(0).getTestMethods().get(0).getStatements());
		List<AssignStatement> assignStatements = collector.getAssignStatements();
		List<GenericStatement> genericStatements = collector.getGenericStatements();
		List<MethodCallStatement> methodCallStatements = collector.getMethodCallStatements();
		List<VariableDeclarationStatement> variableDeclarationStatements = collector.getVariableDeclarationStatements();

		assertEquals(4, assignStatements.size());
		assertEquals(0, genericStatements.size());
		assertEquals(0, methodCallStatements.size());
		assertEquals(3, variableDeclarationStatements.size());
		assertEquals("fixture = 0;", assignStatements.get(0).getCode());
		assertEquals("localFixture1 = 1;", assignStatements.get(1).getCode());
		assertEquals("localFixture2 = operation(2);", assignStatements.get(2).getCode());
		assertEquals("localFixture3 = localFixture3 - 1;", assignStatements.get(3).getCode());
		assertEquals("Integer localFixture1;", variableDeclarationStatements.get(0).getCode());
		assertEquals("Integer localFixture2;", variableDeclarationStatements.get(1).getCode());
		assertEquals("Integer localFixture3 = 3;", variableDeclarationStatements.get(2).getCode());
	}

	@Test
	void whileStatement() throws Exception {
		code.add("public class ExampleTest {");
		code.tab().add("@Test public void test() {");
		code.tab().tab().add("while (true) { System.out.println(0); }");
		code.tab().add("}");
		code.add("}");
		TextFile textFile = new TextFile("ExampleTest.java", code.toString());
		List<TestClass> classes = parser.parse(Arrays.asList(textFile));

		assertEquals(1, classes.size());
		assertEquals("ExampleTest", classes.get(0).getName());
		assertEquals(0, classes.get(0).getFields().size());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(1, classes.get(0).getTestMethods().size());
		assertEquals("test", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(1, classes.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("while (true) { System.out.println(0); }", classes.get(0).getTestMethods().get(0).getStatements().get(0).getCode());
	}

	@Test
	void outOfMethodExpression() throws Exception {
		code.add("public class ExampleTest {");
		code.add("{ System.out.println(0); }");
		code.tab().add("@Test public void test() {}");
		code.add("}");
		TextFile textFile = new TextFile("ExampleTest.java", code.toString());
		List<TestClass> classes = parser.parse(Arrays.asList(textFile));

		assertEquals(1, classes.size());
		assertEquals("ExampleTest", classes.get(0).getName());
		assertEquals(0, classes.get(0).getFields().size());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(1, classes.get(0).getTestMethods().size());
		assertEquals("test", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(0, classes.get(0).getTestMethods().get(0).getStatements().size());
	}

}
