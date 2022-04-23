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

		assertEquals(1, classes.size());
		assertEquals("ExampleTest", classes.get(0).getName());
		assertEquals(0, classes.get(0).getFields().size());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(0, classes.get(0).getTestMethods().size());
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
		code.add("}");
		TextFile textFile = new TextFile("ExampleTest.java", code.toString());
		List<TestClass> classes = parser.parse(Arrays.asList(textFile));

		assertEquals(1, classes.size());
		assertEquals("ExampleTest", classes.get(0).getName());
		assertEquals(0, classes.get(0).getFields().size());
		assertEquals(1, classes.get(0).getSetupMethods().size());
		assertEquals(0, classes.get(0).getTestMethods().size());
		assertEquals("setup", classes.get(0).getSetupMethods().get(0).getName());
		assertEquals(0, classes.get(0).getSetupMethods().get(0).getStatements().size());
	}

	@Test
	void fields() throws Exception {
		code.add("public class ExampleTest {");
		code.tab().add("private int a;");
		code.tab().add("private Integer b, c;");
		code.tab().add("private List<String> d;");
		code.tab().add("private Float e = 0.0f, f = 1.1f;");
		code.add("}");
		TextFile textFile = new TextFile("ExampleTest.java", code.toString());
		List<TestClass> classes = parser.parse(Arrays.asList(textFile));

		assertEquals(1, classes.size());
		assertEquals("ExampleTest", classes.get(0).getName());
		assertEquals(6, classes.get(0).getFields().size());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(0, classes.get(0).getTestMethods().size());
		assertEquals("private int a;", classes.get(0).getFields().get(0).toCode());
		assertEquals("private Integer b;", classes.get(0).getFields().get(1).toCode());
		assertEquals("private Integer c;", classes.get(0).getFields().get(2).toCode());
		assertEquals("private List<String> d;", classes.get(0).getFields().get(3).toCode());
		assertEquals("private Float e = 0.0f;", classes.get(0).getFields().get(4).toCode());
		assertEquals("private Float f = 1.1f;", classes.get(0).getFields().get(5).toCode());
	}

	@Test
	void methodCallStatements() throws Exception {
		code.add("public class ExampleTest {");
		code.tab().add("@Test public void test() {");
		code.tab().tab().add("operation();");
		code.tab().tab().add("operation(0);");
		code.tab().tab().add("someObject.operation();");
		code.tab().tab().add("someObject.attribute.operation();");
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
		assertEquals("operation();", classes.get(0).getTestMethods().get(0).getStatements().get(0).toCode());
		assertEquals("operation(0);", classes.get(0).getTestMethods().get(0).getStatements().get(1).toCode());
		assertEquals("someObject.operation();", classes.get(0).getTestMethods().get(0).getStatements().get(2).toCode());
		assertEquals("someObject.attribute.operation();", classes.get(0).getTestMethods().get(0).getStatements().get(3).toCode());
		assertEquals("SomeClass.operation();", classes.get(0).getTestMethods().get(0).getStatements().get(4).toCode());
		assertEquals("new SomeClass().operation();", classes.get(0).getTestMethods().get(0).getStatements().get(5).toCode());

		StatementCollector collector = new StatementCollector(classes.get(0).getTestMethods().get(0).getStatements());
		List<AssignStatement> assignStatements = collector.getAssignStatements();
		List<GenericStatement> genericStatements = collector.getGenericStatements();
		List<MethodCallStatement> methodCallStatements = collector.getMethodCallStatements();
		List<VariableDeclarationStatement> variableDeclarationStatements = collector.getVariableDeclarationStatements();

		assertEquals(0, assignStatements.size());
		assertEquals(0, genericStatements.size());
		assertEquals(6, methodCallStatements.size());
		assertEquals(0, variableDeclarationStatements.size());
		assertEquals("operation();", methodCallStatements.get(0).toCode());
		assertEquals("operation(0);", methodCallStatements.get(1).toCode());
		assertEquals("someObject.operation();", methodCallStatements.get(2).toCode());
		assertEquals("someObject.attribute.operation();", methodCallStatements.get(3).toCode());
		assertEquals("SomeClass.operation();", methodCallStatements.get(4).toCode());
		assertEquals("new SomeClass().operation();", methodCallStatements.get(5).toCode());
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
		assertEquals("Integer variable1 = 1;", classes.get(0).getTestMethods().get(0).getStatements().get(0).toCode());
		assertEquals("Integer variable2;", classes.get(0).getTestMethods().get(0).getStatements().get(1).toCode());
		assertEquals("Integer variable3 = 3;", classes.get(0).getTestMethods().get(0).getStatements().get(2).toCode());
		assertEquals("Integer variable4 = operation(4);", classes.get(0).getTestMethods().get(0).getStatements().get(3).toCode());
		assertEquals("Integer variable5;", classes.get(0).getTestMethods().get(0).getStatements().get(4).toCode());

		StatementCollector collector = new StatementCollector(classes.get(0).getTestMethods().get(0).getStatements());
		List<AssignStatement> assignStatements = collector.getAssignStatements();
		List<GenericStatement> genericStatements = collector.getGenericStatements();
		List<MethodCallStatement> methodCallStatements = collector.getMethodCallStatements();
		List<VariableDeclarationStatement> variableDeclarationStatements = collector.getVariableDeclarationStatements();

		assertEquals(0, assignStatements.size());
		assertEquals(0, genericStatements.size());
		assertEquals(0, methodCallStatements.size());
		assertEquals(5, variableDeclarationStatements.size());
		assertEquals("Integer variable1 = 1;", variableDeclarationStatements.get(0).toCode());
		assertEquals("Integer variable2;", variableDeclarationStatements.get(1).toCode());
		assertEquals("Integer variable3 = 3;", variableDeclarationStatements.get(2).toCode());
		assertEquals("Integer variable4 = operation(4);", variableDeclarationStatements.get(3).toCode());
		assertEquals("Integer variable5;", variableDeclarationStatements.get(4).toCode());
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
		code.tab().tab().add("someObject.attribute = 4;");
		code.tab().tab().add("SomeClass.attribute = 5;");
		code.tab().add("}");
		code.add("}");
		TextFile textFile = new TextFile("ExampleTest.java", code.toString());
		List<TestClass> classes = parser.parse(Arrays.asList(textFile));

		assertEquals(1, classes.size());
		assertEquals("ExampleTest", classes.get(0).getName());
		assertEquals(1, classes.get(0).getFields().size());
		assertEquals("private Integer fixture;", classes.get(0).getFields().get(0).toCode());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(1, classes.get(0).getTestMethods().size());
		assertEquals("test", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(9, classes.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("Integer localFixture1;", classes.get(0).getTestMethods().get(0).getStatements().get(0).toCode());
		assertEquals("Integer localFixture2;", classes.get(0).getTestMethods().get(0).getStatements().get(1).toCode());
		assertEquals("Integer localFixture3 = 3;", classes.get(0).getTestMethods().get(0).getStatements().get(2).toCode());
		assertEquals("fixture = 0;", classes.get(0).getTestMethods().get(0).getStatements().get(3).toCode());
		assertEquals("localFixture1 = 1;", classes.get(0).getTestMethods().get(0).getStatements().get(4).toCode());
		assertEquals("localFixture2 = operation(2);", classes.get(0).getTestMethods().get(0).getStatements().get(5).toCode());
		assertEquals("localFixture3 = localFixture3 - 1;", classes.get(0).getTestMethods().get(0).getStatements().get(6).toCode());
		assertEquals("someObject.attribute = 4;", classes.get(0).getTestMethods().get(0).getStatements().get(7).toCode());
		assertEquals("SomeClass.attribute = 5;", classes.get(0).getTestMethods().get(0).getStatements().get(8).toCode());

		StatementCollector collector = new StatementCollector(classes.get(0).getTestMethods().get(0).getStatements());
		List<AssignStatement> assignStatements = collector.getAssignStatements();
		List<GenericStatement> genericStatements = collector.getGenericStatements();
		List<MethodCallStatement> methodCallStatements = collector.getMethodCallStatements();
		List<VariableDeclarationStatement> variableDeclarationStatements = collector.getVariableDeclarationStatements();

		assertEquals(6, assignStatements.size());
		assertEquals(0, genericStatements.size());
		assertEquals(0, methodCallStatements.size());
		assertEquals(3, variableDeclarationStatements.size());
		assertEquals("fixture = 0;", assignStatements.get(0).toCode());
		assertEquals("localFixture1 = 1;", assignStatements.get(1).toCode());
		assertEquals("localFixture2 = operation(2);", assignStatements.get(2).toCode());
		assertEquals("localFixture3 = localFixture3 - 1;", assignStatements.get(3).toCode());
		assertEquals("someObject.attribute = 4;", assignStatements.get(4).toCode());
		assertEquals("SomeClass.attribute = 5;", assignStatements.get(5).toCode());
		assertEquals("Integer localFixture1;", variableDeclarationStatements.get(0).toCode());
		assertEquals("Integer localFixture2;", variableDeclarationStatements.get(1).toCode());
		assertEquals("Integer localFixture3 = 3;", variableDeclarationStatements.get(2).toCode());
	}

	@Test
	void whileStatement() throws Exception {
		code.add("public class ExampleTest {");
		code.tab().add("@Test public void test() {");
		code.tab().tab().add("while (true) break;");
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
		assertEquals("while (true) break;", classes.get(0).getTestMethods().get(0).getStatements().get(0).toCode());
	}

	@Test
	void outOfMethodExpression() throws Exception {
		code.add("public class ExampleTest {");
		code.tab().add("{ System.out.println(0); }");
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
