package br.ufsc.ine.leb.roza.core.modern.arrangement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.measurement.TextualPrefixSimilarity;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

class AdmissiblePrefixMatcherTest {

	@Test
	void shouldAnalyzeDefinitionsAndUses() {
		StatementDependencyAnalyzer analyzer = new StatementDependencyAnalyzer();

		StatementDependencyAnalyzer.Analysis declaration =
				analyzer.analyze(statement("Result result = service.create(input);")).orElseThrow();
		StatementDependencyAnalyzer.Analysis assignment =
				analyzer.analyze(statement("result = transform(result, option);")).orElseThrow();
		StatementDependencyAnalyzer.Analysis compoundAssignment =
				analyzer.analyze(statement("this.counter += step;")).orElseThrow();

		assertEquals(Set.of("result"), declaration.definitions());
		assertTrue(declaration.uses().containsAll(Set.of("service", "input")));
		assertEquals(Set.of("result"), assignment.definitions());
		assertTrue(assignment.uses().containsAll(Set.of("result", "option")));
		assertEquals(Set.of("counter"), compoundAssignment.definitions());
		assertTrue(compoundAssignment.uses().containsAll(Set.of("counter", "step")));
		assertFalse(analyzer.analyze(statement("this is not valid Java {")).isPresent());
	}

	@Test
	void shouldPreserveReadAndWriteDependencies() {
		TestCase testCase = testCase(
				"source",
				statement("int value = source();"),
				statement("consume(value);"),
				statement("value = other();"));

		ArrangeDependencyGraph graph = ArrangeDependencyGraph.build(testCase);

		assertEquals(Set.of(0), graph.predecessors(1));
		assertEquals(Set.of(0, 1), graph.predecessors(2));
	}

	@Test
	void shouldIncreasePrefixWhenReorderingIsAdmissible() {
		TestCase source = testCase(
				"source",
				statement("a();"),
				statement("b();"),
				statement("C c = new C();"),
				statement("consume(c);"),
				assertion("assertTrue(true);"));
		TestCase target = testCase(
				"target",
				statement("C c = new C();"),
				statement("a();"),
				statement("b();"),
				statement("consume(c);"),
				assertion("assertFalse(false);"));
		List<String> targetProjection = ArrangeProjection.normalizedStatements(target);

		int textual = TextualPrefixSimilarity.commonPrefixSize(
				ArrangeProjection.normalizedStatements(source),
				targetProjection);
		int greedy = AdmissiblePrefixMatcher.greedyPrefixSize(
				source,
				targetProjection,
				new ArrangeDependencyGraphCache());
		int maximum = AdmissiblePrefixMatcher.maximumPrefixSize(
				source,
				targetProjection,
				new ArrangeDependencyGraphCache(),
				AdmissiblePrefixMatcher.DEFAULT_MAP_NODE_LIMIT);

		assertEquals(0, textual);
		assertEquals(4, greedy);
		assertEquals(4, maximum);
	}

	@Test
	void shouldNotMoveAUseBeforeItsDefinition() {
		TestCase source = testCase(
				"source",
				statement("C c = new C();"),
				statement("consume(c);"));
		TestCase target = testCase(
				"target",
				statement("consume(c);"),
				statement("C c = new C();"));

		int prefixSize = AdmissiblePrefixMatcher.greedyPrefixSize(
				source,
				ArrangeProjection.normalizedStatements(target),
				new ArrangeDependencyGraphCache());

		assertEquals(0, prefixSize);
	}

	@Test
	void shouldFallbackToTextualMatching() {
		TestCase source = testCase("source", statement("this is not valid Java {"));
		TestCase target = testCase("target", statement("this is not valid Java {"));

		int prefixSize = AdmissiblePrefixMatcher.maximumPrefixSize(
				source,
				ArrangeProjection.normalizedStatements(target),
				new ArrangeDependencyGraphCache(),
				AdmissiblePrefixMatcher.DEFAULT_MAP_NODE_LIMIT);

		assertEquals(1, prefixSize);
	}

	@Test
	void shouldFallbackToGreedyMatchingWhenMapReachesItsNodeLimit() {
		TestCase source = testCase(
				"source",
				statement("tick();"),
				statement("tick();"),
				statement("tick();"));
		TestCase target = testCase(
				"target",
				statement("tick();"),
				statement("tick();"),
				statement("tick();"));

		int prefixSize = AdmissiblePrefixMatcher.maximumPrefixSize(
				source,
				ArrangeProjection.normalizedStatements(target),
				new ArrangeDependencyGraphCache(),
				1);

		assertEquals(3, prefixSize);
	}

	private TestCase testCase(String name, CodeStatement... statements) {
		return new TestCase(name, new CodeBlock(List.of(statements)));
	}

	private CodeStatement statement(String text) {
		return new CodeStatement(text, text);
	}

	private CodeStatement assertion(String text) {
		return new CodeStatement(text, text, true);
	}
}
