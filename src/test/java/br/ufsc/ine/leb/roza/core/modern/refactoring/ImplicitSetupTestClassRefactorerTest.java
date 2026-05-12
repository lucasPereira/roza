package br.ufsc.ine.leb.roza.core.modern.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.SetupAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;

class ImplicitSetupTestClassRefactorerTest {

	@Test
	void shouldMoveCommonInitialStatementsToSetup() {
		TestClass source = source("Example", junit4Imports(), setup("Before", "@Before", "import org.junit.Before;"));
		TestCase first = testCase(0, "alpha", source, testAnnotation("Test", "@Test"), statement("Sut sut = new Sut();"), statement("sut.save(1);"), assertion("assertEquals(1, sut.count());"));
		TestCase second = testCase(1, "beta", source, testAnnotation("Test", "@Test"), statement("Sut sut = new Sut();"), statement("sut.save(1);"), assertion("assertTrue(sut.exists());"));

		TestClass generated = refactor(first, second).testClasses().get(0);

		assertEquals("TestClass1", generated.name());
		assertEquals(Optional.empty(), generated.packageName());
		assertEquals(List.of("private Sut sut;"), fields(generated));
		assertEquals(List.of("@Before"), annotations(generated.fixtures().get(0).annotations()));
		assertEquals(List.of("sut = new Sut();", "sut.save(1);"), statements(generated.fixtures().get(0)));
		assertEquals(List.of("assertEquals(1, sut.count());"), statements(generated.testMethods().get(0)));
		assertEquals(List.of("assertTrue(sut.exists());"), statements(generated.testMethods().get(1)));
	}

	@Test
	void shouldPreferBeforeEachWhenClusterMixesSetupAnnotations() {
		TestClass junit4 = source("Junit4Example", junit4Imports(), setup("Before", "@Before", "import org.junit.Before;"));
		TestClass junit5 = source("Junit5Example", junit5Imports(), setup("BeforeEach", "@BeforeEach", "import org.junit.jupiter.api.BeforeEach;"));
		TestCase first = testCase(0, "alpha", junit4, testAnnotation("Test", "@Test"), statement("Sut sut = new Sut();"), assertion("assertTrue(sut.exists());"));
		TestCase second = testCase(1, "beta", junit5, testAnnotation("Test", "@Test"), statement("Sut sut = new Sut();"), assertion("assertTrue(sut.exists());"));

		TestClass generated = refactor(first, second).testClasses().get(0);

		assertEquals(List.of("@BeforeEach"), annotations(generated.fixtures().get(0).annotations()));
		assertEquals(List.of("import org.junit.Test;", "import org.junit.Before;", "import org.junit.jupiter.api.Test;", "import org.junit.jupiter.api.BeforeEach;"), generated.imports());
		assertEquals(List.of("@Test"), annotations(generated.testMethods().get(0).annotations()));
		assertEquals(List.of("@Test"), annotations(generated.testMethods().get(1).annotations()));
	}

	@Test
	void shouldKeepAssertionsOutOfSetupEvenWhenTheyAreCommon() {
		TestClass source = source("Example", junit4Imports(), setup("Before", "@Before", "import org.junit.Before;"));
		TestCase first = testCase(0, "alpha", source, testAnnotation("Test", "@Test"), statement("Sut sut = new Sut();"), assertion("assertTrue(sut.exists());"));
		TestCase second = testCase(1, "beta", source, testAnnotation("Test", "@Test"), statement("Sut sut = new Sut();"), assertion("assertTrue(sut.exists());"));

		TestClass generated = refactor(first, second).testClasses().get(0);

		assertEquals(List.of("sut = new Sut();"), statements(generated.fixtures().get(0)));
		assertEquals(List.of("assertTrue(sut.exists());"), statements(generated.testMethods().get(0)));
		assertEquals(List.of("assertTrue(sut.exists());"), statements(generated.testMethods().get(1)));
	}

	@Test
	void shouldProduceStableClassNamesForMultipleClusters() {
		TestClass source = source("Example", junit4Imports(), setup("Before", "@Before", "import org.junit.Before;"));
		TestCase first = testCase(0, "alpha", source, testAnnotation("Test", "@Test"), assertion("assertTrue(true);"));
		TestCase second = testCase(1, "beta", source, testAnnotation("Test", "@Test"), assertion("assertTrue(false);"));

		RefactoredTestClasses refactored = new ImplicitSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(
				new TestCaseCluster(0, first),
				new TestCaseCluster(1, second))));

		assertEquals(List.of("TestClass1", "TestClass2"), refactored.testClasses().stream().map(TestClass::name).collect(Collectors.toList()));
	}

	@Test
	void shouldMakeDuplicateGeneratedTestMethodNamesUnique() {
		TestClass alphaSource = source("AlphaTest", junit4Imports(), setup("Before", "@Before", "import org.junit.Before;"));
		TestClass betaSource = source("BetaTest", junit4Imports(), setup("Before", "@Before", "import org.junit.Before;"));
		TestCase first = testCase(0, "testDistance", alphaSource, testAnnotation("Test", "@Test"), assertion("assertTrue(true);"));
		TestCase second = testCase(1, "testDistance", betaSource, testAnnotation("Test", "@Test"), assertion("assertTrue(false);"));

		TestClass generated = refactor(first, second).testClasses().get(0);

		assertEquals(List.of("testDistance", "betaTestTestDistance"), generated.testMethods().stream().map(br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod::name).collect(Collectors.toList()));
	}

	@Test
	void shouldUseSourcePackageWhenPackagePartitioningIsSelected() {
		TestClass source = source("Example", junit4Imports(), setup("Before", "@Before", "import org.junit.Before;"));
		TestCase first = testCase(0, "alpha", source, testAnnotation("Test", "@Test"), assertion("assertTrue(true);"));
		TestCase second = testCase(1, "beta", source, testAnnotation("Test", "@Test"), assertion("assertTrue(false);"));

		TestClass generated = refactor(ImplicitSetupPackagePolicy.PACKAGE_PARTITIONING, first, second).testClasses().get(0);

		assertEquals("example.tests", generated.packageName().orElseThrow());
	}

	@Test
	void shouldSplitMixedPackageClustersWhenPackagePartitioningIsSelected() {
		TestClass firstSource = source("FirstTest", "first.package", junit4Imports(), setup("Before", "@Before", "import org.junit.Before;"));
		TestClass secondSource = source("SecondTest", "second.package", junit4Imports(), setup("Before", "@Before", "import org.junit.Before;"));
		TestCase first = testCase(0, "alpha", firstSource, testAnnotation("Test", "@Test"), assertion("assertTrue(true);"));
		TestCase second = testCase(1, "beta", secondSource, testAnnotation("Test", "@Test"), assertion("assertTrue(false);"));

		RefactoredTestClasses refactored = refactor(ImplicitSetupPackagePolicy.PACKAGE_PARTITIONING, first, second);

		assertEquals(List.of("TestClass1", "TestClass2"), refactored.testClasses().stream().map(TestClass::name).collect(Collectors.toList()));
		assertEquals("first.package", refactored.testClasses().get(0).packageName().orElseThrow());
		assertEquals("second.package", refactored.testClasses().get(1).packageName().orElseThrow());
	}

	private RefactoredTestClasses refactor(TestCase first, TestCase second) {
		return new ImplicitSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(new TestCaseCluster(0, first).merge(new TestCaseCluster(1, second)))));
	}

	private RefactoredTestClasses refactor(ImplicitSetupPackagePolicy packagePolicy, TestCase first, TestCase second) {
		return new ImplicitSetupTestClassRefactorer(packagePolicy).refactor(new TestCaseClusters(List.of(new TestCaseCluster(0, first).merge(new TestCaseCluster(1, second)))));
	}

	private TestCase testCase(int index, String name, TestClass source, CodeAnnotation annotation, CodeStatement... statements) {
		return new TestCase(name, new CodeBlock(List.of(statements)), source, List.of(annotation));
	}

	private TestClass source(String name, List<String> imports, SetupAnnotation setupAnnotation) {
		return source(name, "example.tests", imports, setupAnnotation);
	}

	private TestClass source(String name, String packageName, List<String> imports, SetupAnnotation setupAnnotation) {
		return new TestClass(name, packageName, imports, setupAnnotation, List.of(), List.<FixtureMethod>of(), List.of(), List.of());
	}

	private List<String> junit4Imports() {
		return List.of("import org.junit.Test;", "import org.junit.Before;");
	}

	private List<String> junit5Imports() {
		return List.of("import org.junit.jupiter.api.Test;", "import org.junit.jupiter.api.BeforeEach;");
	}

	private SetupAnnotation setup(String name, String text, String importDeclaration) {
		return new SetupAnnotation(new CodeAnnotation(name, text), Optional.of(importDeclaration));
	}

	private CodeAnnotation testAnnotation(String name, String text) {
		return new CodeAnnotation(name, text);
	}

	private CodeStatement statement(String text) {
		return new CodeStatement(text, text);
	}

	private CodeStatement assertion(String text) {
		return new CodeStatement(text, text, true);
	}

	private List<String> fields(TestClass testClass) {
		return testClass.fields().stream().map(field -> String.join(" ", field.modifiers()) + " " + field.type() + " " + field.name() + ";").collect(Collectors.toList());
	}

	private List<String> annotations(List<CodeAnnotation> annotations) {
		return annotations.stream().map(CodeAnnotation::text).collect(Collectors.toList());
	}

	private List<String> statements(FixtureMethod fixture) {
		return fixture.body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList());
	}

	private List<String> statements(br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod testMethod) {
		return testMethod.body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList());
	}
}
