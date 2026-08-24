package br.ufsc.ine.leb.roza.core.modern.analytics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.clustering.ClusteringLevel;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;
import br.ufsc.ine.leb.roza.core.modern.refactoring.DelegatedSetupTestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.ImplicitSetupTestClassRefactorer;

class RefactoringLevelRankerTest {

	@Test
	void shouldRankLevelsWithTheSelectedRefactorerInsteadOfAlwaysUsingImplicitSetup() {
		TestClass source = source();
		TestCase alpha = testCase("alpha", source, "uniqueAlpha();", "a();", "b();", "c();", "assertTrue(true);");
		TestCase beta = testCase("beta", source, "uniqueBeta();", "a();", "b();", "c();", "assertFalse(false);");
		TestCase gamma = testCase("gamma", source, "uniqueGamma();", "d();", "e();", "f();", "assertEquals(1, 1);");
		TestCase delta = testCase("delta", source, "uniqueDelta();", "d();", "e();", "f();", "assertEquals(2, 2);");
		TestCaseCluster firstPair = new TestCaseCluster(0, alpha).merge(new TestCaseCluster(1, beta));
		TestCaseCluster secondPair = new TestCaseCluster(2, gamma).merge(new TestCaseCluster(3, delta));
		List<ClusteringLevel> levels = List.of(
				new ClusteringLevel(0, List.of(
						new TestCaseCluster(0, alpha),
						new TestCaseCluster(1, beta),
						new TestCaseCluster(2, gamma),
						new TestCaseCluster(3, delta))),
				new ClusteringLevel(1, List.of(firstPair, secondPair)),
				new ClusteringLevel(2, List.of(firstPair.merge(secondPair))));

		assertEquals(List.of(1), RefactoringLevelRanker.topLevelIndices(levels, new DelegatedSetupTestClassRefactorer(), 1));
		assertEquals(List.of(2), RefactoringLevelRanker.topLevelIndices(levels, new ImplicitSetupTestClassRefactorer(), 1));
	}

	private TestClass source() {
		Field field = new Field(List.of(), "Sut", "sut", Optional.of(new CodeStatement("new Sut()", "new Sut()")));
		return new TestClass(
				"Example",
				"example.tests",
				List.of(),
				null,
				List.of(field),
				List.of(),
				List.of(),
				List.of(
						testMethod("alpha", "uniqueAlpha();", "a();", "b();", "c();", "assertTrue(true);"),
						testMethod("beta", "uniqueBeta();", "a();", "b();", "c();", "assertFalse(false);"),
						testMethod("gamma", "uniqueGamma();", "d();", "e();", "f();", "assertEquals(1, 1);"),
						testMethod("delta", "uniqueDelta();", "d();", "e();", "f();", "assertEquals(2, 2);")));
	}

	private TestCase testCase(String name, TestClass source, String... statements) {
		return new TestCase(name, new CodeBlock(coded(statements)), source, List.of(new CodeAnnotation("Test", "@Test")));
	}

	private TestMethod testMethod(String name, String... statements) {
		return new TestMethod(name, List.of(new CodeAnnotation("Test", "@Test")), new CodeBlock(coded(statements)));
	}

	private List<CodeStatement> coded(String... statements) {
		return List.of(statements).stream()
				.map(text -> new CodeStatement(text, text, text.startsWith("assert")))
				.collect(Collectors.toList());
	}
}
