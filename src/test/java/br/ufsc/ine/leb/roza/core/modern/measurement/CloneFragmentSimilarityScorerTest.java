package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

class CloneFragmentSimilarityScorerTest {

	private final CloneFragmentSimilarityScorer scorer = new CloneFragmentSimilarityScorer();

	@Test
	void shouldComputeAsymmetricCoveredSourceLineRatio() {
		List<MaterializedTestCase> testCases = List.of(
				materialized("source", "Source.java", 3, 3),
				materialized("target", "Target.java", 3, 2));
		List<CloneFragment> fragments = List.of(
				new CloneFragment("Source.java", "Target.java", 3, 4),
				new CloneFragment("Target.java", "Source.java", 3, 3));

		TestCaseSimilarityMatrix matrix = scorer.score(testCases, fragments);

		assertEquals(2.0 / 3.0, matrix.similarity(0, 1), 0.000001);
		assertEquals(1.0 / 2.0, matrix.similarity(1, 0), 0.000001);
	}

	@Test
	void shouldMergeOverlappingCoveredSourceIntervals() {
		List<MaterializedTestCase> testCases = List.of(
				materialized("source", "Source.java", 3, 3),
				materialized("target", "Target.java", 3, 3));
		List<CloneFragment> fragments = List.of(
				new CloneFragment("Source.java", "Target.java", 3, 4),
				new CloneFragment("Source.java", "Target.java", 4, 5));

		TestCaseSimilarityMatrix matrix = scorer.score(testCases, fragments);

		assertEquals(1.0, matrix.similarity(0, 1), 0.000001);
	}

	@Test
	void shouldIgnoreWrapperLinesOutsideTheProjectedBody() {
		List<MaterializedTestCase> testCases = List.of(
				materialized("source", "Source.java", 3, 3),
				materialized("target", "Target.java", 3, 3));
		List<CloneFragment> fragments = List.of(
				new CloneFragment("Source.java", "Target.java", 1, 2),
				new CloneFragment("Source.java", "Target.java", 3, 3));

		TestCaseSimilarityMatrix matrix = scorer.score(testCases, fragments);

		assertEquals(1.0 / 3.0, matrix.similarity(0, 1), 0.000001);
	}

	@Test
	void shouldReturnZeroForEmptySourceProjection() {
		List<MaterializedTestCase> testCases = List.of(
				materialized("source", "Source.java", 3, 0),
				materialized("target", "Target.java", 3, 1));

		TestCaseSimilarityMatrix matrix = scorer.score(testCases, List.of(
				new CloneFragment("Source.java", "Target.java", 3, 3)));

		assertEquals(0.0, matrix.similarity(0, 1), 0.000001);
		assertEquals(1.0, matrix.similarity(0, 0), 0.000001);
	}

	private MaterializedTestCase materialized(String name, String fileName, int firstProjectedLine, int projectedLineCount) {
		return new MaterializedTestCase(testCase(name), fileName, firstProjectedLine, projectedLineCount);
	}

	private TestCase testCase(String name) {
		return new TestCase(name, new CodeBlock(List.of(new CodeStatement("statement();", "statement();"))));
	}
}
