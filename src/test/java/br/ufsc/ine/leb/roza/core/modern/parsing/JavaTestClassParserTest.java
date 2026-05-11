package br.ufsc.ine.leb.roza.core.modern.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import br.ufsc.ine.leb.roza.core.modern.loading.CodeFile;
import br.ufsc.ine.leb.roza.core.modern.loading.LoadedCodeFiles;

class JavaTestClassParserTest {

	private JavaTestClassParser parser;

	@BeforeEach
	void setup() {
		parser = new JavaTestClassParser();
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
		ParsedTestClasses parsed = parse("class Example { @Before public void setup() { int value = 1; } @After public void teardown() { int value = 2; } @Test public void test() { assertTrue(true); } }");

		List<FixtureMethod> fixtures = parsed.testClasses().get(0).fixtures();
		assertEquals(2, fixtures.size());
		assertEquals(FixtureKind.BEFORE, fixtures.get(0).kind());
		assertEquals("setup", fixtures.get(0).name());
		assertEquals("int value = 1;", fixtures.get(0).body().statements().get(0).normalizedText());
		assertEquals(FixtureKind.AFTER, fixtures.get(1).kind());
		assertEquals("teardown", fixtures.get(1).name());
		assertEquals("int value = 2;", fixtures.get(1).body().statements().get(0).normalizedText());
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
		assertEquals("assertTrue(true);", statements.get(1).normalizedText());
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("unsupportedFeatures")
	void shouldFailInSafeModeWhenUnsupportedFeatureExists(String name, String content, String expectedMessage) {
		UnsupportedFeatureException exception = assertThrows(UnsupportedFeatureException.class, () -> parse(content));

		assertTrue(exception.getMessage().contains(expectedMessage), name + " should mention " + expectedMessage + " but was " + exception.getMessage());
	}

	@Test
	void shouldSkipUnsupportedClassInUnsafeMode() {
		JavaTestClassParser unsafeParser = new JavaTestClassParser(UnsupportedFeaturePolicy.UNSAFE);

		ParsedTestClasses parsed = unsafeParser.parse(files("class Example { static int value; @Test public void test() { assertTrue(true); } }"));

		assertEquals(0, parsed.testClasses().size());
		assertEquals(1, unsafeParser.diagnostics().size());
		assertTrue(unsafeParser.diagnostics().get(0).message().contains("static field"));
	}

	private ParsedTestClasses parse(String content) {
		return parser.parse(files(content));
	}

	private LoadedCodeFiles files(String content) {
		return new LoadedCodeFiles(List.of(new CodeFile(content)));
	}

	private static Stream<Arguments> unsupportedFeatures() {
		return Stream.of(
				unsupported("multiple top-level classes", "class Example { @Test public void test() { assertTrue(true); } } class Other { }", "multiple top-level classes"),
				unsupported("test class nested in another class", "class Outer { class Example { @Test public void test() { assertTrue(true); } } }", "nested class"),
				unsupported("nested class inside test class", "class Example { class Nested { } @Test public void test() { assertTrue(true); } }", "nested class"),
				unsupported("JUnit 5 nested annotation", "class Example { @Nested public void helper() { } @Test public void test() { assertTrue(true); } }", "Nested"),
				unsupported("test class inheritance", "class Example extends Parent { @Test public void test() { assertTrue(true); } }", "inheritance"),
				unsupported("abstract test class", "abstract class Example { @Test public void test() { assertTrue(true); } }", "abstract test class"),
				unsupported("generic test class", "class Example<T> { @Test public void test() { assertTrue(true); } }", "generic test class"),
				unsupported("instance initializer", "class Example { { int value = 1; } @Test public void test() { assertTrue(true); } }", "class initializer"),
				unsupported("static initializer", "class Example { static { int value = 1; } @Test public void test() { assertTrue(true); } }", "class initializer"),
				unsupported("explicit constructor", "class Example { Example() { } @Test public void test() { assertTrue(true); } }", "constructor"),
				unsupported("static import", "import static org.junit.Assert.assertTrue; class Example { @Test public void test() { assertTrue(true); } }", "static import"),
				unsupported("class annotation", "@RunWith(MockitoJUnitRunner.class) class Example { @Test public void test() { assertTrue(true); } }", "class annotation"),
				unsupported("wildcard import", "import java.util.*; class Example { @Test public void test() { assertTrue(true); } }", "wildcard import"),
				unsupported("comment", "class Example { // comment\n @Test public void test() { assertTrue(true); } }", "comments"),
				unsupported("static field", "class Example { static int value; @Test public void test() { assertTrue(true); } }", "static field"),
				unsupported("static final field", "class Example { static final int VALUE = 1; @Test public void test() { assertTrue(true); } }", "static field"),
				unsupported("final field", "class Example { final int value = 1; @Test public void test() { assertTrue(true); } }", "final field"),
				unsupported("volatile field", "class Example { volatile int value; @Test public void test() { assertTrue(true); } }", "volatile field"),
				unsupported("transient field", "class Example { transient int value; @Test public void test() { assertTrue(true); } }", "transient field"),
				unsupported("field initializer lambda", "class Example { Runnable value = () -> run(); @Test public void test() { assertTrue(true); } }", "lambda"),
				unsupported("field initializer method reference", "class Example { Runnable value = this::run; void run() { } @Test public void test() { assertTrue(true); } }", "method reference"),
				unsupported("field initializer anonymous class", "class Example { Runnable value = new Runnable() { public void run() { } }; @Test public void test() { assertTrue(true); } }", "anonymous class"),
				unsupported("static helper method", "class Example { static void helper() { } @Test public void test() { assertTrue(true); } }", "static helper"),
				unsupported("generic helper method", "class Example { <T> void helper(T value) { } @Test public void test() { assertTrue(true); } }", "generic helper"),
				unsupported("overloaded helper method", "class Example { void helper() { } void helper(int value) { } @Test public void test() { assertTrue(true); } }", "overloaded helper"),
				unsupported("helper varargs", "class Example { void helper(String... values) { } @Test public void test() { assertTrue(true); } }", "varargs"),
				unsupported("synchronized helper", "class Example { synchronized void helper() { } @Test public void test() { assertTrue(true); } }", "synchronized helper"),
				unsupported("native helper", "class Example { native void helper(); @Test public void test() { assertTrue(true); } }", "native helper"),
				unsupported("helper without body", "class Example { abstract void helper(); @Test public void test() { assertTrue(true); } }", "without body"),
				unsupported("helper calls super", "class Example { void helper() { super.toString(); } @Test public void test() { assertTrue(true); } }", "super"),
				unsupported("helper depends on inherited member", "class Example { void helper() { inherited(); } @Test public void test() { assertTrue(true); } }", "helper"),
				unsupported("JUnit 4 BeforeClass", "class Example { @BeforeClass public void setup() { } @Test public void test() { assertTrue(true); } }", "BeforeClass"),
				unsupported("JUnit 4 AfterClass", "class Example { @AfterClass public void teardown() { } @Test public void test() { assertTrue(true); } }", "AfterClass"),
				unsupported("JUnit 5 BeforeAll", "class Example { @BeforeAll public void setup() { } @Test public void test() { assertTrue(true); } }", "BeforeAll"),
				unsupported("JUnit 5 AfterAll", "class Example { @AfterAll public void teardown() { } @Test public void test() { assertTrue(true); } }", "AfterAll"),
				unsupported("JUnit 4 Ignore", "class Example { @Ignore @Test public void test() { assertTrue(true); } }", "Ignore"),
				unsupported("JUnit 5 Disabled", "class Example { @Disabled @Test public void test() { assertTrue(true); } }", "Disabled"),
				unsupported("JUnit 4 Parameterized runner", "@RunWith(Parameterized.class) class Example { @Test public void test() { assertTrue(true); } }", "class annotation"),
				unsupported("JUnit 4 Parameters", "class Example { @Parameters public void data() { } @Test public void test() { assertTrue(true); } }", "Parameters"),
				unsupported("JUnit 4 Parameter field", "class Example { @Parameter int value; @Test public void test() { assertTrue(true); } }", "field annotation"),
				unsupported("JUnit 5 ParameterizedTest", "class Example { @ParameterizedTest public void test() { assertTrue(true); } }", "ParameterizedTest"),
				unsupported("JUnit 5 CsvSource", "class Example { @CsvSource(\"a\") @Test public void test() { assertTrue(true); } }", "CsvSource"),
				unsupported("JUnit 5 CsvFileSource", "class Example { @CsvFileSource(resources = \"data.csv\") @Test public void test() { assertTrue(true); } }", "CsvFileSource"),
				unsupported("JUnit 5 ValueSource", "class Example { @ValueSource(ints = 1) @Test public void test() { assertTrue(true); } }", "ValueSource"),
				unsupported("JUnit 5 EnumSource", "class Example { @EnumSource(Value.class) @Test public void test() { assertTrue(true); } }", "EnumSource"),
				unsupported("JUnit 5 MethodSource", "class Example { @MethodSource(\"data\") @Test public void test() { assertTrue(true); } }", "MethodSource"),
				unsupported("JUnit 5 ArgumentsSource", "class Example { @ArgumentsSource(Data.class) @Test public void test() { assertTrue(true); } }", "ArgumentsSource"),
				unsupported("JUnit 4 Theory", "class Example { @Theory public void test() { assertTrue(true); } }", "Theory"),
				unsupported("JUnit 4 DataPoint", "class Example { @DataPoint int value; @Test public void test() { assertTrue(true); } }", "field annotation"),
				unsupported("JUnit 4 DataPoints", "class Example { @DataPoints int[] values; @Test public void test() { assertTrue(true); } }", "field annotation"),
				unsupported("JUnit 5 TestFactory", "class Example { @TestFactory public void test() { assertTrue(true); } }", "TestFactory"),
				unsupported("JUnit 5 TestTemplate", "class Example { @TestTemplate public void test() { assertTrue(true); } }", "TestTemplate"),
				unsupported("JUnit 5 RepeatedTest", "class Example { @RepeatedTest(2) public void test() { assertTrue(true); } }", "RepeatedTest"),
				unsupported("JUnit 4 FixMethodOrder", "@FixMethodOrder(NAME_ASCENDING) class Example { @Test public void test() { assertTrue(true); } }", "class annotation"),
				unsupported("JUnit 5 TestMethodOrder", "@TestMethodOrder(OrderAnnotation.class) class Example { @Test public void test() { assertTrue(true); } }", "class annotation"),
				unsupported("JUnit 5 Order", "class Example { @Order(1) @Test public void test() { assertTrue(true); } }", "Order"),
				unsupported("JUnit 5 TestInstance", "@TestInstance(PER_CLASS) class Example { @Test public void test() { assertTrue(true); } }", "class annotation"),
				unsupported("JUnit 4 Category", "class Example { @Category(Slow.class) @Test public void test() { assertTrue(true); } }", "Category"),
				unsupported("JUnit 5 Tag", "class Example { @Tag(\"slow\") @Test public void test() { assertTrue(true); } }", "Tag"),
				unsupported("JUnit 4 Rule", "class Example { @Rule Object rule; @Test public void test() { assertTrue(true); } }", "field annotation"),
				unsupported("JUnit 4 ClassRule", "class Example { @ClassRule Object rule; @Test public void test() { assertTrue(true); } }", "field annotation"),
				unsupported("JUnit 5 ExtendWith", "@ExtendWith(MockitoExtension.class) class Example { @Test public void test() { assertTrue(true); } }", "class annotation"),
				unsupported("JUnit 5 RegisterExtension", "class Example { @RegisterExtension Object extension; @Test public void test() { assertTrue(true); } }", "field annotation"),
				unsupported("JUnit 4 RunWith", "@RunWith(MockitoJUnitRunner.class) class Example { @Test public void test() { assertTrue(true); } }", "class annotation"),
				unsupported("framework integration annotation", "@SpringBootTest class Example { @Test public void test() { assertTrue(true); } }", "class annotation"),
				unsupported("Test expected attribute", "class Example { @Test(expected = RuntimeException.class) public void test() { action(); } }", "@Test attributes"),
				unsupported("Test timeout attribute", "class Example { @Test(timeout = 1000) public void test() { assertTrue(true); } }", "@Test attributes"),
				unsupported("unknown test method annotation", "class Example { @Custom @Test public void test() { assertTrue(true); } }", "Custom"),
				unsupported("test method with parameters", "class Example { @Test public void test(int value) { assertTrue(true); } }", "parameters"),
				unsupported("test method return type", "class Example { @Test public int test() { return 1; } }", "return type"),
				unsupported("private test method", "class Example { @Test private void test() { assertTrue(true); } }", "private test"),
				unsupported("static test method", "class Example { @Test static void test() { assertTrue(true); } }", "static test"),
				unsupported("repeated annotation", "class Example { @Test @Test public void test() { assertTrue(true); } }", "repeated annotation"),
				unsupported("multiple Before fixtures", "class Example { @Before public void first() { } @Before public void second() { } @Test public void test() { assertTrue(true); } }", "multiple @Before"),
				unsupported("multiple After fixtures", "class Example { @After public void first() { } @After public void second() { } @Test public void test() { assertTrue(true); } }", "multiple @After"),
				unsupported("local class", "class Example { @Test public void test() { class Local { } assertTrue(true); } }", "local class"),
				unsupported("enum declaration", "class Example { enum State { READY } @Test public void test() { assertTrue(true); } }", "enum declaration"),
				unsupported("anonymous class", "class Example { @Test public void test() { Runnable value = new Runnable() { public void run() { } }; } }", "anonymous class"),
				unsupported("lambda in method body", "class Example { @Test public void test() { Runnable value = () -> run(); } }", "lambda"),
				unsupported("method reference in method body", "class Example { @Test public void test() { Runnable value = this::run; } void run() { } }", "method reference"),
				unsupported("try statement", "class Example { @Test public void test() { try { run(); } catch (Exception exception) { fail(); } } }", "try statement"),
				unsupported("try-with-resources", "class Example { @Test public void test() { try (Resource resource = open()) { run(); } } }", "try statement"),
				unsupported("switch statement", "class Example { @Test public void test() { switch (value) { case 1: run(); } } }", "switch statement"),
				unsupported("synchronized block", "class Example { @Test public void test() { synchronized (this) { run(); } } }", "synchronized block"),
				unsupported("labeled statement", "class Example { @Test public void test() { label: run(); } }", "labeled statement"),
				unsupported("break statement", "class Example { @Test public void test() { while (true) { break; } } }", "break statement"),
				unsupported("continue statement", "class Example { @Test public void test() { while (true) { continue; } } }", "continue statement"),
				unsupported("helper return value", "class Example { int helper() { return 1; } @Test public void test() { assertTrue(true); } }", "return value"),
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
				unsupported("explicit this", "class Example { @Test public void test() { this.helper(); } void helper() { } }", "this expression"),
				unsupported("explicit super", "class Example { @Test public void test() { super.toString(); } }", "super expression"),
				unsupported("missing helper", "class Example { @Test public void test() { helper(); } }", "helper"));
	}

	private static Arguments unsupported(String name, String content, String expectedMessage) {
		return Arguments.of(name, content, expectedMessage);
	}
}
