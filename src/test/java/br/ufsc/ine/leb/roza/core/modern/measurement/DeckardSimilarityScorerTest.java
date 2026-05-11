package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

class DeckardSimilarityScorerTest {

	private final DeckardSimilarityScorer scorer = new DeckardSimilarityScorer();

	@Test
	void shouldComputeAsymmetricCoveredSourceLineRatio() {
		List<DeckardMaterializedTestCase> testCases = List.of(
				materialized("source", "Source.java", 3, 3),
				materialized("target", "Target.java", 3, 2));
		List<DeckardCloneFragment> fragments = List.of(
				new DeckardCloneFragment("Source.java", "Target.java", 3, 4),
				new DeckardCloneFragment("Target.java", "Source.java", 3, 3));

		TestCaseSimilarityMatrix matrix = scorer.score(testCases, fragments);

		assertEquals(2.0 / 3.0, matrix.similarity(0, 1), 0.000001);
		assertEquals(1.0 / 2.0, matrix.similarity(1, 0), 0.000001);
	}

	@Test
	void shouldMergeOverlappingCoveredSourceIntervals() {
		List<DeckardMaterializedTestCase> testCases = List.of(
				materialized("source", "Source.java", 3, 3),
				materialized("target", "Target.java", 3, 3));
		List<DeckardCloneFragment> fragments = List.of(
				new DeckardCloneFragment("Source.java", "Target.java", 3, 4),
				new DeckardCloneFragment("Source.java", "Target.java", 4, 5));

		TestCaseSimilarityMatrix matrix = scorer.score(testCases, fragments);

		assertEquals(1.0, matrix.similarity(0, 1), 0.000001);
	}

	@Test
	void shouldIgnoreDeckardWrapperLinesOutsideTheProjectedBody() {
		List<DeckardMaterializedTestCase> testCases = List.of(
				materialized("source", "Source.java", 3, 3),
				materialized("target", "Target.java", 3, 3));
		List<DeckardCloneFragment> fragments = List.of(
				new DeckardCloneFragment("Source.java", "Target.java", 1, 2),
				new DeckardCloneFragment("Source.java", "Target.java", 3, 3));

		TestCaseSimilarityMatrix matrix = scorer.score(testCases, fragments);

		assertEquals(1.0 / 3.0, matrix.similarity(0, 1), 0.000001);
	}

	@Test
	void shouldReturnZeroForEmptySourceProjection() {
		List<DeckardMaterializedTestCase> testCases = List.of(
				materialized("source", "Source.java", 3, 0),
				materialized("target", "Target.java", 3, 1));

		TestCaseSimilarityMatrix matrix = scorer.score(testCases, List.of(
				new DeckardCloneFragment("Source.java", "Target.java", 3, 3)));

		assertEquals(0.0, matrix.similarity(0, 1), 0.000001);
		assertEquals(1.0, matrix.similarity(0, 0), 0.000001);
	}

	private DeckardMaterializedTestCase materialized(String name, String fileName, int firstProjectedLine, int projectedLineCount) {
		return new DeckardMaterializedTestCase(testCase(name), fileName, firstProjectedLine, projectedLineCount);
	}

	private TestCase testCase(String name) {
		return new TestCase(name, new CodeBlock(List.of(new CodeStatement("statement();", "statement();"))));
	}
}
