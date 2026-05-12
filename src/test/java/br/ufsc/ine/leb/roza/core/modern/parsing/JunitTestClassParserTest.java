package br.ufsc.ine.leb.roza.core.modern.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import br.ufsc.ine.leb.roza.core.modern.loading.CodeFile;
import br.ufsc.ine.leb.roza.core.modern.loading.LoadedCodeFiles;

class JunitTestClassParserTest {

	private JunitTestClassParser parser;

	@BeforeEach
	void setup() {
		parser = new JunitTestClassParser();
	}

	@Test
	void shouldIgnoreFileWithoutTest() {
		ParsedTestClasses parsed = parse("class Example { void helper() { run(); } }");

		assertEquals(0, parsed.testClasses().size());
	}

	@Test
	void shouldParseClassWithOneTestMethod() {
		ParsedTestClasses parsed = parse("class Example { @Test public void shouldWork() { assertEquals(1, 1); } }");

		assertEquals(1, parsed.testClasses().size());
		TestClass testClass = parsed.testClasses().get(0);
		assertEquals("Example", testClass.name());
		assertEquals(1, testClass.testMethods().size());
		TestMethod testMethod = testClass.testMethods().get(0);
		assertEquals("shouldWork", testMethod.name());
		assertEquals("Test", testMethod.annotations().get(0).name());
		assertEquals("assertEquals(1, 1);", testMethod.body().statements().get(0).normalizedText());
		assertTrue(testMethod.body().statements().get(0).isAssertion());
	}

	@Test
	void shouldParseImports() {
		ParsedTestClasses parsed = parse("import org.junit.Test; import static org.junit.Assert.assertTrue; class Example { @Test public void test() { assertTrue(true); } }");

		assertEquals(List.of("import org.junit.Test;", "import static org.junit.Assert.assertTrue;"), parsed.testClasses().get(0).imports());
	}

	@Test
	void shouldParsePackageName() {
		ParsedTestClasses parsed = parse("package example.tests; import org.junit.Test; class Example { @Test public void test() { assertTrue(true); } }");

		assertEquals("example.tests", parsed.testClasses().get(0).packageName().orElseThrow());
	}

	@Test
	void shouldParseThrownExceptionsFromFixturesAndTests() {
		ParsedTestClasses parsed = parse("import org.junit.Before; import org.junit.Test; class Example { @Before public void setup() throws IOException { prepare(); } @Test public void test() throws IOException, InterruptedException { assertTrue(true); } }");

		TestClass testClass = parsed.testClasses().get(0);
		assertEquals(List.of("IOException"), testClass.fixtures().get(0).thrownExceptions());
		assertEquals(List.of("IOException", "InterruptedException"), testClass.testMethods().get(0).thrownExceptions());
	}

	@Test
	void shouldInferJunit4SetupAnnotationFromJunit4TestImportWhenFixtureIsMissing() {
		ParsedTestClasses parsed = parse("import org.junit.Test; class Example { @Test public void test() { assertTrue(true); } }");

		SetupAnnotation setup = parsed.testClasses().get(0).setupAnnotation().orElseThrow();
		assertEquals("Before", setup.annotation().name());
		assertEquals("@Before", setup.annotation().text());
		assertEquals("import org.junit.Before;", setup.importDeclaration().orElseThrow());
	}

	@Test
	void shouldInferJunit5SetupAnnotationFromJupiterTestImportWhenFixtureIsMissing() {
		ParsedTestClasses parsed = parse("import org.junit.jupiter.api.Test; class Example { @Test public void test() { assertTrue(true); } }");

		SetupAnnotation setup = parsed.testClasses().get(0).setupAnnotation().orElseThrow();
		assertEquals("BeforeEach", setup.annotation().name());
		assertEquals("@BeforeEach", setup.annotation().text());
		assertEquals("import org.junit.jupiter.api.BeforeEach;", setup.importDeclaration().orElseThrow());
	}

	@Test
	void shouldUseExistingFixtureAnnotationAsSetupAnnotation() {
		ParsedTestClasses parsed = parse("import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test; class Example { @BeforeEach public void setup() { value = 1; } @Test public void test() { assertTrue(true); } }");

		SetupAnnotation setup = parsed.testClasses().get(0).setupAnnotation().orElseThrow();
		assertEquals("BeforeEach", setup.annotation().name());
		assertEquals("@BeforeEach", setup.annotation().text());
		assertEquals("import org.junit.jupiter.api.BeforeEach;", setup.importDeclaration().orElseThrow());
	}

	@Test
	void shouldParseFields() {
		ParsedTestClasses parsed = parse("class Example { private int first = 1; String second; int[] values = { 1, 2 }; @Test public void test() { assertTrue(true); } }");

		List<Field> fields = parsed.testClasses().get(0).fields();
		assertEquals(3, fields.size());
		assertEquals(List.of("private"), fields.get(0).modifiers());
		assertEquals("int", fields.get(0).type());
		assertEquals("first", fields.get(0).name());
		assertEquals("1", fields.get(0).initialization().orElseThrow().normalizedText());
		assertEquals("String", fields.get(1).type());
		assertEquals("second", fields.get(1).name());
		assertTrue(fields.get(1).initialization().isEmpty());
		assertEquals("int[]", fields.get(2).type());
		assertEquals("values", fields.get(2).name());
		assertEquals("{ 1, 2 }", fields.get(2).initialization().orElseThrow().normalizedText());
	}

	@Test
	void shouldParseFieldsWithComplexTypes() {
		ParsedTestClasses parsed = parse("class Example { List<String> names; Map<String, List<Integer>> values; List<? extends Number> numbers; String[][] matrix; int[][][] cube; java.util.List<String> qualified; List<String>[] groupedNames; Map.Entry<String, Integer> entry; @Test public void test() { assertTrue(true); } }");

		List<Field> fields = parsed.testClasses().get(0).fields();
		assertEquals(8, fields.size());
		assertEquals("List<String>", fields.get(0).type());
		assertEquals("names", fields.get(0).name());
		assertEquals("Map<String,List<Integer>>", fields.get(1).type());
		assertEquals("values", fields.get(1).name());
		assertEquals("List<? extends Number>", fields.get(2).type());
		assertEquals("numbers", fields.get(2).name());
		assertEquals("String[][]", fields.get(3).type());
		assertEquals("matrix", fields.get(3).name());
		assertEquals("int[][][]", fields.get(4).type());
		assertEquals("cube", fields.get(4).name());
		assertEquals("java.util.List<String>", fields.get(5).type());
		assertEquals("qualified", fields.get(5).name());
		assertEquals("List<String>[]", fields.get(6).type());
		assertEquals("groupedNames", fields.get(6).name());
		assertEquals("Map.Entry<String,Integer>", fields.get(7).type());
		assertEquals("entry", fields.get(7).name());
	}

	@Test
	void shouldParseMultipleVariablesInOneFieldDeclaration() {
		ParsedTestClasses parsed = parse("class Example { int first = 1, second = 2; @Test public void test() { assertTrue(true); } }");

		List<Field> fields = parsed.testClasses().get(0).fields();
		assertEquals(2, fields.size());
		assertEquals("first", fields.get(0).name());
		assertEquals("1", fields.get(0).initialization().orElseThrow().normalizedText());
		assertEquals("second", fields.get(1).name());
		assertEquals("2", fields.get(1).initialization().orElseThrow().normalizedText());
	}

	@Test
	void shouldParseFixtures() {
		ParsedTestClasses parsed = parse("class Example { @Before public void setup() { int value = 1; } @Test public void test() { assertTrue(true); } }");

		List<FixtureMethod> fixtures = parsed.testClasses().get(0).fixtures();
		assertEquals(1, fixtures.size());
		assertEquals(FixtureKind.BEFORE, fixtures.get(0).kind());
		assertEquals("setup", fixtures.get(0).name());
		assertEquals("int value = 1;", fixtures.get(0).body().statements().get(0).normalizedText());
	}

	@Test
	void shouldParseHelperMethod() {
		ParsedTestClasses parsed = parse("class Example { private void helper() { int value = 1; } @Test public void test() { helper(); } }");

		List<HelperMethod> helpers = parsed.testClasses().get(0).helperMethods();
		assertEquals(1, helpers.size());
		assertEquals(List.of("private"), helpers.get(0).modifiers());
		assertEquals("void", helpers.get(0).returnType());
		assertEquals("helper", helpers.get(0).name());
		assertEquals("int value = 1;", helpers.get(0).body().statements().get(0).normalizedText());
	}

	@Test
	void shouldParseMultipleTestMethods() {
		ParsedTestClasses parsed = parse("class Example { @Test public void first() { assertTrue(true); } @Test public void second() { assertTrue(false); } }");

		List<TestMethod> tests = parsed.testClasses().get(0).testMethods();
		assertEquals(2, tests.size());
		assertEquals("first", tests.get(0).name());
		assertEquals("second", tests.get(1).name());
	}

	@Test
	void shouldKeepControlFlowAsTopLevelStatement() {
		ParsedTestClasses parsed = parse("class Example { @Test public void test() { while (true) { int value = 1; } assertTrue(true); } }");

		List<CodeStatement> statements = parsed.testClasses().get(0).testMethods().get(0).body().statements();
		assertEquals(2, statements.size());
		assertEquals("while (true) { int value = 1; }", statements.get(0).normalizedText());
		assertFalse(statements.get(0).isAssertion());
		assertEquals("assertTrue(true);", statements.get(1).normalizedText());
		assertTrue(statements.get(1).isAssertion());
	}

	@Test
	void shouldNotMarkHelperWhoseNameStartsWithAssertAsAssertion() {
		ParsedTestClasses parsed = parse("class Example { void assertBusinessRule() { int value = 1; } @Test public void test() { assertBusinessRule(); assertTrue(true); } }");

		List<CodeStatement> statements = parsed.testClasses().get(0).testMethods().get(0).body().statements();
		assertEquals("assertBusinessRule();", statements.get(0).normalizedText());
		assertFalse(statements.get(0).isAssertion());
		assertEquals("assertTrue(true);", statements.get(1).normalizedText());
		assertTrue(statements.get(1).isAssertion());
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("assertionMethods")
	void shouldMarkSupportedJunitAndHamcrestAssertionMethods(String assertionMethod) {
		ParsedTestClasses parsed = parse("class Example { @Test public void test() { " + assertionMethod + "(); } }");

		assertTrue(parsed.testClasses().get(0).testMethods().get(0).body().statements().get(0).isAssertion());
	}

	@Test
	void shouldAcceptStaticImportOfJUnitAssertMethod() {
		ParsedTestClasses parsed = parse("import static org.junit.Assert.assertTrue;\nclass Example { @Test public void test() { assertTrue(true); } }");

		assertEquals(1, parsed.testClasses().size());
		assertTrue(parsed.testClasses().get(0).testMethods().get(0).body().statements().get(0).isAssertion());
	}

	@Test
	void shouldMarkAssertionWhenUsingQualifiedJUnit4Assert() {
		ParsedTestClasses parsed = parse("class Example { @Test public void test() { org.junit.Assert.assertFalse(false); } }");

		assertTrue(parsed.testClasses().get(0).testMethods().get(0).body().statements().get(0).isAssertion());
	}

	@Test
	void shouldMarkAssertionWhenUsingQualifiedJupiterAssertions() {
		ParsedTestClasses parsed = parse("class Example { @Test public void test() { org.junit.jupiter.api.Assertions.assertNotNull(\"x\"); } }");

		assertTrue(parsed.testClasses().get(0).testMethods().get(0).body().statements().get(0).isAssertion());
	}

	@Test
	void shouldMarkAssertionWhenUsingQualifiedHamcrestMatcherAssert() {
		ParsedTestClasses parsed = parse("class Example { @Test public void test() { org.hamcrest.MatcherAssert.assertThat(true, org.hamcrest.Matchers.equalTo(true)); } }");

		assertTrue(parsed.testClasses().get(0).testMethods().get(0).body().statements().get(0).isAssertion());
	}

	@Test
	void shouldMarkAssertionWhenUsingSimpleJupiterAssertionsReceiver() {
		ParsedTestClasses parsed = parse("class Example { @Test public void test() { Assertions.assertEquals(1, 1); } }");

		assertTrue(parsed.testClasses().get(0).testMethods().get(0).body().statements().get(0).isAssertion());
	}

	@Test
	void shouldMarkAssertNotInstanceOf() {
		ParsedTestClasses parsed = parse("class Example { @Test public void test() { Assertions.assertNotInstanceOf(String.class, Integer.valueOf(1)); } }");

		assertTrue(parsed.testClasses().get(0).testMethods().get(0).body().statements().get(0).isAssertion());
	}

	@Test
	void shouldNotMarkQualifiedForeignReceiverAsAssertion() {
		ParsedTestClasses parsed = parse("class Example { @Test public void test() { Other.assertEquals(1, 1); } }");

		assertFalse(parsed.testClasses().get(0).testMethods().get(0).body().statements().get(0).isAssertion());
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("unsupportedFeatures")
	void shouldReportUnsupportedSubsetViolation(String name, String content, String expectedDescription) {
		ParsedTestClasses parsed = parse(content);

		assertTrue(
				parsed.violations().stream().anyMatch(violation -> violation.description().contains(expectedDescription)),
				name + " should mention " + expectedDescription + " but was " + parsed.violations().stream().map(TestCodeViolation::description).collect(Collectors.toList()));
	}

	@Test
	void shouldExposeClassLevelViolation() {
		ParsedTestClasses parsed = parse("class Example { void helper() { } @Test public void test() { assertTrue(true); } }");

		TestCodeViolation violation = parsed.violations().get(0);

		assertEquals(ViolationScope.TEST_CLASS, violation.scope());
		assertEquals("Example", violation.testClassName());
		assertTrue(violation.testMethodName().isEmpty());
		assertTrue(violation.description().contains("helper method"));
		assertTrue(violation.codeSnippet().contains("void helper()"));
	}

	@Test
	void shouldExposeTestMethodLevelViolation() {
		ParsedTestClasses parsed = parse("class Example { @Test public void first() { assertTrue(true); } @Test public void second(int value) { assertTrue(true); } }");

		TestCodeViolation violation = parsed.violations().get(0);

		assertEquals(ViolationScope.TEST_METHOD, violation.scope());
		assertEquals("Example", violation.testClassName());
		assertEquals("second", violation.testMethodName().orElseThrow());
		assertTrue(violation.description().contains("parameters"));
		assertTrue(violation.codeSnippet().contains("second(int value)"));
	}

	@Test
	void shouldSupportTestWithoutDetectableAssertion() {
		ParsedTestClasses parsed = parse("class Example { @Test public void test() { service.run(); } }");

		assertEquals(1, parsed.testClasses().size());
		assertEquals(0, parsed.violations().size());
	}

	@Test
	void shouldSupportLambdaInsideDetectableAssertion() {
		ParsedTestClasses parsed = parse("class Example { @Test public void test() { assertThrows(RuntimeException.class, () -> service.run()); } }");

		assertEquals(0, parsed.violations().size());
		assertTrue(parsed.testClasses().get(0).testMethods().get(0).body().statements().get(0).isAssertion());
	}

	@Test
	void shouldSupportMethodReferenceInsideDetectableAssertion() {
		ParsedTestClasses parsed = parse("class Example { @Test public void test() { assertThrows(RuntimeException.class, ComposedCriterion::new); } }");

		assertEquals(0, parsed.violations().size());
		assertTrue(parsed.testClasses().get(0).testMethods().get(0).body().statements().get(0).isAssertion());
	}

	private ParsedTestClasses parse(String content) {
		return parser.parse(files(content));
	}

	private LoadedCodeFiles files(String content) {
		return new LoadedCodeFiles(List.of(new CodeFile(content)));
	}

	private static Stream<String> assertionMethods() {
		return Stream.of(
				"assertAll",
				"assertArrayEquals",
				"assertDoesNotThrow",
				"assertEquals",
				"assertFalse",
				"assertInstanceOf",
				"assertIterableEquals",
				"assertLinesMatch",
				"assertNotEquals",
				"assertNotNull",
				"assertNotSame",
				"assertNull",
				"assertSame",
				"assertThat",
				"assertThrows",
				"assertThrowsExactly",
				"assertTimeout",
				"assertTimeoutPreemptively",
				"assertTrue",
				"fail");
	}

	private static Stream<Arguments> unsupportedFeatures() {
		return Stream.of(
				unsupported("multiple top-level classes", "class Example { @Test public void test() { assertTrue(true); } } class Other { }", "multiple top-level classes"),
				unsupported("test class inheritance", "class Example extends Parent { @Test public void test() { assertTrue(true); } }", "inheritance"),
				unsupported("abstract test class", "abstract class Example { @Test public void test() { assertTrue(true); } }", "abstract test class"),
				unsupported("generic test class", "class Example<T> { @Test public void test() { assertTrue(true); } }", "generic test class"),
				unsupported("explicit constructor", "class Example { Example() { } @Test public void test() { assertTrue(true); } }", "constructor"),
				unsupported("class annotation", "@RunWith(MockitoJUnitRunner.class) class Example { @Test public void test() { assertTrue(true); } }", "class annotation"),
				unsupported("wildcard import", "import java.util.*; class Example { @Test public void test() { assertTrue(true); } }", "wildcard import"),
				unsupported("static field", "class Example { static int value; @Test public void test() { assertTrue(true); } }", "static field"),
				unsupported("field initializer", "class Example { int value = 1; @Test public void test() { assertTrue(true); } }", "field initialization"),
				unsupported("helper method", "class Example { void helper() { } @Test public void test() { assertTrue(true); } }", "helper method"),
				unsupported("JUnit 4 After", "class Example { @After public void teardown() { } @Test public void test() { assertTrue(true); } }", "After"),
				unsupported("JUnit 5 Disabled", "class Example { @Disabled @Test public void test() { assertTrue(true); } }", "Disabled"),
				unsupported("JUnit 5 ParameterizedTest", "class Example { @ParameterizedTest public void test() { assertTrue(true); } }", "ParameterizedTest"),
				unsupported("Test expected attribute", "class Example { @Test(expected = RuntimeException.class) public void test() { action(); } }", "@Test attributes"),
				unsupported("test method with parameters", "class Example { @Test public void test(int value) { assertTrue(true); } }", "parameters"),
				unsupported("test method return type", "class Example { @Test public int test() { return 1; } }", "return type"),
				unsupported("private test method", "class Example { @Test private void test() { assertTrue(true); } }", "private test"),
				unsupported("static test method", "class Example { @Test static void test() { assertTrue(true); } }", "static test"),
				unsupported("multiple Before fixtures", "class Example { @Before public void first() { } @Before public void second() { } @Test public void test() { assertTrue(true); } }", "multiple @Before"),
				unsupported("mixed Before fixtures", "class Example { @Before public void first() { } @BeforeEach public void second() { } @Test public void test() { assertTrue(true); } }", "multiple @Before"),
				unsupported("local class", "class Example { @Test public void test() { class Local { } assertTrue(true); } }", "local class"),
				unsupported("enum declaration", "class Example { enum State { READY } @Test public void test() { assertTrue(true); } }", "enum declaration"),
				unsupported("anonymous class", "class Example { @Test public void test() { Runnable value = new Runnable() { public void run() { } }; } }", "anonymous class"),
				unsupported("lambda in method body", "class Example { @Test public void test() { Runnable value = () -> run(); } }", "lambda"),
				unsupported("method reference in method body", "class Example { @Test public void test() { Runnable value = this::run; } void run() { } }", "method reference"),
				unsupported("try statement", "class Example { @Test public void test() { try { run(); } catch (Exception exception) { fail(); } } }", "try statement"),
				unsupported("try-with-resources", "class Example { @Test public void test() { try (Resource resource = open()) { run(); } } }", "try statement"),
				unsupported("switch statement", "class Example { @Test public void test() { switch (value) { case 1: run(); } } }", "switch statement"),
				unsupported("for loop", "class Example { @Test public void test() { for (int i = 0; i < 1; i++) { assertTrue(true); } } }", "for loop"),
				unsupported("for-each loop", "class Example { @Test public void test() { for (String s : java.util.List.of()) { assertTrue(true); } } }", "for-each loop"),
				unsupported("while loop", "class Example { @Test public void test() { while (false) { assertTrue(true); } } }", "while loop"),
				unsupported("do-while loop", "class Example { @Test public void test() { do { assertTrue(true); } while (false); } }", "do-while loop"),
				unsupported("synchronized block", "class Example { @Test public void test() { synchronized (this) { run(); } } }", "synchronized block"),
				unsupported("labeled statement", "class Example { @Test public void test() { label: run(); } }", "labeled statement"),
				unsupported("break statement", "class Example { @Test public void test() { while (true) { break; } } }", "break statement"),
				unsupported("continue statement", "class Example { @Test public void test() { while (true) { continue; } } }", "continue statement"),
				unsupported("helper return value", "class Example { int helper() { return 1; } @Test public void test() { assertTrue(true); } }", "helper method"),
				unsupported("explicit throw", "class Example { @Test public void test() { throw new RuntimeException(); } }", "throw statement"),
				unsupported("thread creation", "class Example { @Test public void test() { new Thread(); } }", "thread creation"),
				unsupported("async call", "class Example { @Test public void test() { CompletableFuture.runAsync(task); } }", "async"),
				unsupported("reflection Class.forName", "class Example { @Test public void test() { Class.forName(\"Example\"); } }", "reflection"),
				unsupported("reflection getDeclaredMethod", "class Example { @Test public void test() { type.getDeclaredMethod(\"test\"); } }", "reflection"),
				unsupported("file side effect", "class Example { @Test public void test() { Files.readAllBytes(path); } }", "side-effect"),
				unsupported("network side effect", "class Example { @Test public void test() { new Socket(\"localhost\", 80); } }", "Socket"),
				unsupported("url side effect", "class Example { @Test public void test() { new URL(\"http://localhost\"); } }", "URL"),
				unsupported("database side effect", "class Example { @Test public void test() { DriverManager.getConnection(url); } }", "side-effect"),
				unsupported("time call", "class Example { @Test public void test() { System.currentTimeMillis(); } }", "system call"),
				unsupported("explicit this", "class Example { @Test public void test() { this.toString(); } }", "this expression"),
				unsupported("explicit super", "class Example { @Test public void test() { super.toString(); } }", "super expression"));
	}

	private static Arguments unsupported(String name, String content, String expectedMessage) {
		return Arguments.of(name, content, expectedMessage);
	}
}
