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

class OriginalHelperClassExtractorTest {

	@Test
	void shouldEmitOneHelperClassPerOriginalClass() {
		TestClass first = source("FirstExample", "example.one", helper("createFirst"));
		TestClass second = source("SecondExample", "example.two", helper("createSecond"));
		TestCase alpha = testCase("alpha", first);
		TestCase beta = testCase("beta", second);

		List<TestClass> helpers = OriginalHelperClassExtractor.helperClasses(new TestCaseClusters(List.of(
				new TestCaseCluster(0, alpha),
				new TestCaseCluster(1, beta))));

		assertEquals(List.of("FirstExampleHelpers", "SecondExampleHelpers"), helpers.stream().map(TestClass::name).collect(Collectors.toList()));
		assertTrue(helpers.get(0).packageName().isEmpty());
		assertTrue(helpers.get(0).isHelperClass());
		assertEquals("createFirst", helpers.get(0).helperMethods().get(0).name());
		assertEquals("createSecond", helpers.get(1).helperMethods().get(0).name());
	}

	@Test
	void shouldReuseOneHelperClassWhenSeveralTestsComeFromTheSameSource() {
		TestClass source = source("Example", "example.tests", helper("createSut"));
		TestCase alpha = testCase("alpha", source);
		TestCase beta = testCase("beta", source);

		List<TestClass> helpers = OriginalHelperClassExtractor.helperClasses(new TestCaseClusters(List.of(
				new TestCaseCluster(0, alpha).merge(new TestCaseCluster(1, beta)))));

		assertEquals(1, helpers.size());
		assertEquals("ExampleHelpers", helpers.get(0).name());
	}

	@Test
	void shouldSkipSourcesWithoutHelpers() {
		TestClass source = new TestClass(
				"Example",
				"example.tests",
				List.of(),
				null,
				List.of(),
				List.of(),
				List.of(),
				List.of(testMethod("alpha")));

		assertTrue(OriginalHelperClassExtractor.helperClasses(new TestCaseClusters(List.of(
				new TestCaseCluster(0, testCase("alpha", source))))).isEmpty());
	}

	@Test
	void shouldDisambiguateHelperClassNamesWhenSimpleNamesCollide() {
		TestClass first = source("Example", "example.one", helper("first"));
		TestClass second = source("Example", "example.two", helper("second"));

		List<TestClass> helpers = OriginalHelperClassExtractor.helperClasses(new TestCaseClusters(List.of(
				new TestCaseCluster(0, testCase("alpha", first)),
				new TestCaseCluster(1, testCase("beta", second)))));

		assertEquals(List.of("ExampleHelpers", "ExampleHelpers2"), helpers.stream().map(TestClass::name).collect(Collectors.toList()));
	}

	private TestClass source(String name, String packageName, HelperMethod helper) {
		return new TestClass(
				name,
				packageName,
				List.of("import example.Sut;"),
				null,
				List.of(),
				List.of(),
				List.of(helper),
				List.of(testMethod("alpha")));
	}

	private HelperMethod helper(String name) {
		return new HelperMethod(
				List.of("private"),
				"Sut",
				name,
				List.of(),
				List.of(),
				new CodeBlock(List.of(new CodeStatement("return new Sut();", "return new Sut();"))));
	}

	private TestMethod testMethod(String name) {
		return new TestMethod(
				name,
				List.of(new CodeAnnotation("Test", "@Test")),
				new CodeBlock(List.of(new CodeStatement("assertTrue(true);", "assertTrue(true);", true))));
	}

	private TestCase testCase(String name, TestClass source) {
		return new TestCase(
				name,
				new CodeBlock(List.of(new CodeStatement("assertTrue(true);", "assertTrue(true);", true))),
				source,
				List.of(new CodeAnnotation("Test", "@Test")));
	}
}
