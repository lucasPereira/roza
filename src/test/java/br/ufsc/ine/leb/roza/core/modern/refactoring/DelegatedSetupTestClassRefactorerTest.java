package br.ufsc.ine.leb.roza.core.modern.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.HelperMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

class DelegatedSetupTestClassRefactorerTest {

	@Test
	void shouldExtractASharedContiguousRunIntoAHelperClass() {
		TestClass source = source("Example", testMethod("alpha", "createUser();", "login();", "assertTrue(true);"), testMethod("beta", "deleteUser();", "login();", "assertFalse(false);"));
		TestCase first = testCase("alpha", source, "createUser();", "login();", "assertTrue(true);");
		TestCase second = testCase("beta", source, "deleteUser();", "login();", "assertFalse(false);");

		RefactoredTestClasses refactored = new DelegatedSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(
				new TestCaseCluster(0, first).merge(new TestCaseCluster(1, second)))));

		assertEquals(1, refactored.testClasses().size());
		assertEquals("Example", refactored.testClasses().get(0).name());
		assertEquals(1, refactored.helperClasses().size());
		assertEquals("HelperClass1", refactored.helperClasses().get(0).name());
		HelperMethod helper = refactored.helperClasses().get(0).helperMethods().get(0);
		assertEquals("setup1", helper.name());
		assertEquals(List.of("login();"), helper.body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList()));
		assertEquals("HelperClass1.setup1();", refactored.testClasses().get(0).testMethods().get(0).body().statements().get(1).normalizedText());
		assertEquals("HelperClass1.setup1();", refactored.testClasses().get(0).testMethods().get(1).body().statements().get(1).normalizedText());
	}

	@Test
	void shouldExtractASharedRunFromTwoOriginalClasses() {
		TestClass firstSource = source("FirstExample", testMethod("alpha", "login();", "assertTrue(true);"));
		TestClass secondSource = source("SecondExample", testMethod("beta", "login();", "assertFalse(false);"));
		TestCase first = testCase("alpha", firstSource, "login();", "assertTrue(true);");
		TestCase second = testCase("beta", secondSource, "login();", "assertFalse(false);");

		RefactoredTestClasses refactored = new DelegatedSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(
				new TestCaseCluster(0, first).merge(new TestCaseCluster(1, second)))));

		assertEquals(List.of("FirstExample", "SecondExample"), refactored.testClasses().stream().map(TestClass::name).collect(Collectors.toList()));
		assertEquals(1, refactored.helperClasses().size());
		assertEquals("HelperClass1", refactored.helperClasses().get(0).name());
		assertTrue(refactored.helperClasses().get(0).packageName().isEmpty());
		assertEquals("HelperClass1.setup1();", refactored.testClasses().get(0).testMethods().get(0).body().statements().get(0).normalizedText());
		assertEquals("HelperClass1.setup1();", refactored.testClasses().get(1).testMethods().get(0).body().statements().get(0).normalizedText());
	}

	@Test
	void shouldRewriteTheOriginalMethodBodyInsteadOfAnInlinedDecomposedBody() {
		TestClass source = source("Example", testMethod("alpha", "login();", "assertTrue(true);"), testMethod("beta", "login();", "assertFalse(false);"));
		TestCase first = testCase("alpha", source, "Sut sut = new Sut();", "login();", "assertTrue(true);");
		TestCase second = testCase("beta", source, "Sut sut = new Sut();", "login();", "assertFalse(false);");

		RefactoredTestClasses refactored = new DelegatedSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(
				new TestCaseCluster(0, first).merge(new TestCaseCluster(1, second)))));

		assertEquals(List.of("login();"), refactored.helperClasses().get(0).helperMethods().get(0).body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList()));
		assertEquals("HelperClass1.setup1();", refactored.testClasses().get(0).testMethods().get(0).body().statements().get(0).normalizedText());
		assertEquals("assertTrue(true);", refactored.testClasses().get(0).testMethods().get(0).body().statements().get(1).normalizedText());
		assertEquals(List.of("HelperClass1.setup1();", "assertFalse(false);"), refactored.testClasses().get(0).testMethods().get(1).body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList()));
	}

	@Test
	void shouldKeepOnlyTestsThatEnteredClustering() {
		TestClass source = source(
				"Example",
				testMethod("alpha", "login();", "assertTrue(true);"),
				testMethod("beta", "login();", "assertFalse(false);"),
				testMethod("gamma", "login();", "assertEquals(1, 1);"));
		TestCase first = testCase("alpha", source, "login();", "assertTrue(true);");
		TestCase second = testCase("beta", source, "login();", "assertFalse(false);");

		RefactoredTestClasses refactored = new DelegatedSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(
				new TestCaseCluster(0, first).merge(new TestCaseCluster(1, second)))));

		assertEquals(List.of("alpha", "beta"), refactored.testClasses().get(0).testMethods().stream().map(TestMethod::name).collect(Collectors.toList()));
	}

	@Test
	void shouldExtractASubsetRunWhenTheClusterIncludesAnUnrelatedTest() {
		TestClass source = source(
				"Example",
				testMethod("alpha", "createUser();", "login();", "assertTrue(true);"),
				testMethod("beta", "deleteUser();", "login();", "assertFalse(false);"),
				testMethod("gamma", "assertEquals(1, 1);"));
		TestCase first = testCase("alpha", source, "createUser();", "login();", "assertTrue(true);");
		TestCase second = testCase("beta", source, "deleteUser();", "login();", "assertFalse(false);");
		TestCase unrelated = testCase("gamma", source, "assertEquals(1, 1);");

		RefactoredTestClasses refactored = new DelegatedSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(
				new TestCaseCluster(0, first).merge(new TestCaseCluster(1, second)).merge(new TestCaseCluster(2, unrelated)))));

		assertEquals(1, refactored.helperClasses().size());
		assertEquals(List.of("login();"), refactored.helperClasses().get(0).helperMethods().get(0).body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList()));
		assertEquals("HelperClass1.setup1();", refactored.testClasses().get(0).testMethods().get(0).body().statements().get(1).normalizedText());
		assertEquals("HelperClass1.setup1();", refactored.testClasses().get(0).testMethods().get(1).body().statements().get(1).normalizedText());
		assertEquals(List.of("assertEquals(1, 1);"), refactored.testClasses().get(0).testMethods().get(2).body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList()));
	}

	@Test
	void shouldLeaveASingletonClusterUnchanged() {
		TestClass source = source("Example", testMethod("alpha", "login();", "assertTrue(true);"));
		TestCase only = testCase("alpha", source, "login();", "assertTrue(true);");

		RefactoredTestClasses refactored = new DelegatedSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(new TestCaseCluster(0, only))));

		assertEquals(1, refactored.testClasses().size());
		assertTrue(refactored.helperClasses().isEmpty());
		assertEquals(List.of("login();", "assertTrue(true);"), refactored.testClasses().get(0).testMethods().get(0).body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList()));
	}

	@Test
	void shouldMoveOriginalHelpersToASeparateHelperClassAlongsideCreatedHelpers() {
		HelperMethod originalHelper = new HelperMethod(
				List.of("private"),
				"Sut",
				"createSut",
				List.of(),
				List.of(),
				new CodeBlock(List.of(new CodeStatement("return new Sut();", "return new Sut();"))));
		TestClass source = new TestClass(
				"Example",
				"example.tests",
				List.of(),
				null,
				List.of(),
				List.of(),
				List.of(originalHelper),
				List.of(
						testMethod("alpha", "login();", "assertTrue(true);"),
						testMethod("beta", "login();", "assertFalse(false);")));
		TestCase first = testCase("alpha", source, "login();", "assertTrue(true);");
		TestCase second = testCase("beta", source, "login();", "assertFalse(false);");

		RefactoredTestClasses refactored = new DelegatedSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(
				new TestCaseCluster(0, first).merge(new TestCaseCluster(1, second)))));

		assertTrue(refactored.testClasses().get(0).helperMethods().isEmpty());
		assertEquals(List.of("ExampleHelpers", "HelperClass1"), refactored.helperClasses().stream().map(TestClass::name).collect(Collectors.toList()));
		assertEquals("createSut", refactored.helperClasses().get(0).helperMethods().get(0).name());
		assertEquals("setup1", refactored.helperClasses().get(1).helperMethods().get(0).name());
		assertEquals("HelperClass1.setup1();", refactored.testClasses().get(0).testMethods().get(0).body().statements().get(0).normalizedText());
	}

	private TestCase testCase(String name, TestClass source, String... statements) {
		List<CodeStatement> coded = List.of(statements).stream().map(this::statement).collect(Collectors.toList());
		if (coded.get(coded.size() - 1).normalizedText().startsWith("assert")) {
			coded = coded.stream().map(statement -> statement.normalizedText().startsWith("assert") ? new CodeStatement(statement.normalizedText(), statement.normalizedText(), true) : statement).collect(Collectors.toList());
		}
		return new TestCase(name, new CodeBlock(coded), source, List.of(new CodeAnnotation("Test", "@Test")));
	}

	private TestClass source(String name, TestMethod... methods) {
		return new TestClass(name, "example.tests", List.of(), null, List.of(), List.of(), List.of(), List.of(methods));
	}

	private TestMethod testMethod(String name, String... statements) {
		List<CodeStatement> coded = List.of(statements).stream().map(this::statement).collect(Collectors.toList());
		if (coded.get(coded.size() - 1).normalizedText().startsWith("assert")) {
			coded = coded.stream().map(statement -> statement.normalizedText().startsWith("assert") ? new CodeStatement(statement.normalizedText(), statement.normalizedText(), true) : statement).collect(Collectors.toList());
		}
		return new TestMethod(name, List.of(new CodeAnnotation("Test", "@Test")), new CodeBlock(coded));
	}

	private CodeStatement statement(String text) {
		return new CodeStatement(text, text, text.startsWith("assert"));
	}
}
