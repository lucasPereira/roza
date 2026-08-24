package br.ufsc.ine.leb.roza.core.modern.analytics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;
import java.util.ArrayList;
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
import br.ufsc.ine.leb.roza.core.modern.refactoring.ResidualImplicitSetupTestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.TestClassRefactorer;

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

	@Test
	void shouldMatchFullRefactoringRankingWhenScoringLevelsIncrementally() {
		List<ClusteringLevel> levels = exampleLevels();
		assertEquals(fullRanking(levels, new DelegatedSetupTestClassRefactorer()), RefactoringLevelRanker.topLevelIndices(levels, new DelegatedSetupTestClassRefactorer(), 10));
		assertEquals(fullRanking(levels, new ImplicitSetupTestClassRefactorer()), RefactoringLevelRanker.topLevelIndices(levels, new ImplicitSetupTestClassRefactorer(), 10));
		assertEquals(fullRanking(levels, new ResidualImplicitSetupTestClassRefactorer()), RefactoringLevelRanker.topLevelIndices(levels, new ResidualImplicitSetupTestClassRefactorer(), 10));
	}

	@Test
	void shouldFinishDelegatedRankingOnAGrowingClusterChain() {
		int testCount = 80;
		List<TestMethod> methods = new ArrayList<>();
		for (int index = 0; index < testCount; index++) {
			methods.add(testMethod("test" + index, statementsFor(index)));
		}
		TestClass source = new TestClass("Example", "example.tests", List.of(), null, List.of(), List.of(), List.of(), methods);
		List<TestCaseCluster> remaining = new ArrayList<>();
		for (int index = 0; index < testCount; index++) {
			remaining.add(new TestCaseCluster(index, testCase("test" + index, source, statementsFor(index))));
		}
		List<ClusteringLevel> levels = new ArrayList<>();
		levels.add(new ClusteringLevel(0, List.copyOf(remaining)));
		TestCaseCluster growing = remaining.remove(0);
		for (int level = 1; !remaining.isEmpty(); level++) {
			growing = growing.merge(remaining.remove(0));
			List<TestCaseCluster> clusters = new ArrayList<>();
			clusters.add(growing);
			clusters.addAll(remaining);
			levels.add(new ClusteringLevel(level, clusters));
		}

		assertTimeout(Duration.ofSeconds(3), () -> RefactoringLevelRanker.topLevelIndices(levels, new DelegatedSetupTestClassRefactorer(), 10));
	}

	@Test
	void shouldReportProgressAfterEachLevel() {
		TestClass source = source();
		TestCase alpha = testCase("alpha", source, "uniqueAlpha();", "a();", "assertTrue(true);");
		TestCase beta = testCase("beta", source, "uniqueBeta();", "a();", "assertFalse(false);");
		List<ClusteringLevel> levels = List.of(
				new ClusteringLevel(0, List.of(new TestCaseCluster(0, alpha), new TestCaseCluster(1, beta))),
				new ClusteringLevel(1, List.of(new TestCaseCluster(0, alpha).merge(new TestCaseCluster(1, beta)))));
		List<Integer> completed = new ArrayList<>();
		List<Integer> totals = new ArrayList<>();

		RefactoringLevelRanker.topLevelIndices(levels, new ImplicitSetupTestClassRefactorer(), 10, (done, total) -> {
			completed.add(done);
			totals.add(total);
		});

		assertEquals(List.of(0, 1, 2), completed);
		assertEquals(List.of(2, 2, 2), totals);
	}

	private List<ClusteringLevel> exampleLevels() {
		TestClass source = source();
		TestCase alpha = testCase("alpha", source, "uniqueAlpha();", "a();", "b();", "c();", "assertTrue(true);");
		TestCase beta = testCase("beta", source, "uniqueBeta();", "a();", "b();", "c();", "assertFalse(false);");
		TestCase gamma = testCase("gamma", source, "uniqueGamma();", "d();", "e();", "f();", "assertEquals(1, 1);");
		TestCase delta = testCase("delta", source, "uniqueDelta();", "d();", "e();", "f();", "assertEquals(2, 2);");
		TestCaseCluster firstPair = new TestCaseCluster(0, alpha).merge(new TestCaseCluster(1, beta));
		TestCaseCluster secondPair = new TestCaseCluster(2, gamma).merge(new TestCaseCluster(3, delta));
		return List.of(
				new ClusteringLevel(0, List.of(
						new TestCaseCluster(0, alpha),
						new TestCaseCluster(1, beta),
						new TestCaseCluster(2, gamma),
						new TestCaseCluster(3, delta))),
				new ClusteringLevel(1, List.of(firstPair, secondPair)),
				new ClusteringLevel(2, List.of(firstPair.merge(secondPair))));
	}

	private List<Integer> fullRanking(List<ClusteringLevel> levels, TestClassRefactorer refactorer) {
		TestClassRefactorer full = clusters -> refactorer.refactor(clusters);
		return RefactoringLevelRanker.topLevelIndices(levels, full, 10);
	}

	private String[] statementsFor(int index) {
		return new String[] {
				"unique" + index + "();",
				"a();",
				"b();",
				"c();",
				"d();",
				"e();",
				"f();",
				"g();",
				"h();",
				"i();",
				"j();",
				"assertTrue(true);"
		};
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
