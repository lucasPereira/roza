package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

public class EvaluationBasedCriteriaTest {

	private TestCase testA;
	private TestCase testB;

	@BeforeEach
	void setup() {
		testA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		testB = new TestCase("testB", Arrays.asList(), Arrays.asList());
	}

	@Test
	void evaluationBasedCriteriaNoLevels() throws Exception {
		ThresholdCriteria threshold = new EvaluationBasedCriteria(BigDecimal.ZERO);
		assertTrue(threshold.shoudlStop(new LinkedList<>()));
	}

	@Test
	void evaluationBasedCriteriaZeroEvaluation() throws Exception {
		SimilarityAssessment assessmentAA = new SimilarityAssessment(testA, testA, BigDecimal.ONE);
		SimilarityAssessment assessmentAB = new SimilarityAssessment(testA, testB, BigDecimal.ZERO);
		SimilarityAssessment assessmentBA = new SimilarityAssessment(testB, testA, BigDecimal.ZERO);
		SimilarityAssessment assessmentBB = new SimilarityAssessment(testB, testA, BigDecimal.ONE);
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessmentAA, assessmentAB, assessmentBA, assessmentBB));
		Level level = new Level(new SingleLinkage(report), new InsecureReferee(), new ClusterFactory().create(report));
		List<Level> levels = Arrays.asList(level);
		assertFalse(new EvaluationBasedCriteria(new BigDecimal("-0.1")).shoudlStop(levels));
		assertTrue(new EvaluationBasedCriteria(BigDecimal.ZERO).shoudlStop(levels));
		assertTrue(new EvaluationBasedCriteria(new BigDecimal("0.1")).shoudlStop(levels));
	}

	@Test
	void evaluationBasedCriteriaOneEvaluation() throws Exception {
		SimilarityAssessment assessmentAA = new SimilarityAssessment(testA, testA, BigDecimal.ONE);
		SimilarityAssessment assessmentAB = new SimilarityAssessment(testA, testB, BigDecimal.ONE);
		SimilarityAssessment assessmentBA = new SimilarityAssessment(testB, testA, BigDecimal.ONE);
		SimilarityAssessment assessmentBB = new SimilarityAssessment(testB, testA, BigDecimal.ONE);
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessmentAA, assessmentAB, assessmentBA, assessmentBB));
		Level level = new Level(new SingleLinkage(report), new InsecureReferee(), new ClusterFactory().create(report));
		List<Level> levels = Arrays.asList(level);
		assertFalse(new EvaluationBasedCriteria(new BigDecimal("0.9")).shoudlStop(levels));
		assertTrue(new EvaluationBasedCriteria(BigDecimal.ONE).shoudlStop(levels));
		assertTrue(new EvaluationBasedCriteria(new BigDecimal("1.1")).shoudlStop(levels));
	}

	@Test
	void evaluationBasedCriteriaZeroPointFiveEvaluation() throws Exception {
		SimilarityAssessment assessmentAA = new SimilarityAssessment(testA, testA, BigDecimal.ONE);
		SimilarityAssessment assessmentAB = new SimilarityAssessment(testA, testB, new BigDecimal("0.5"));
		SimilarityAssessment assessmentBA = new SimilarityAssessment(testB, testA, new BigDecimal("0.5"));
		SimilarityAssessment assessmentBB = new SimilarityAssessment(testB, testA, BigDecimal.ONE);
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessmentAA, assessmentAB, assessmentBA, assessmentBB));
		Level level = new Level(new SingleLinkage(report), new InsecureReferee(), new ClusterFactory().create(report));
		List<Level> levels = Arrays.asList(level);
		assertFalse(new EvaluationBasedCriteria(new BigDecimal("0.4")).shoudlStop(levels));
		assertTrue(new EvaluationBasedCriteria(new BigDecimal("0.5")).shoudlStop(levels));
		assertTrue(new EvaluationBasedCriteria(new BigDecimal("0.6")).shoudlStop(levels));
	}

}