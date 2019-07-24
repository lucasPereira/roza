package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

public class TestCaseClustererTest {

	private TestCaseClusterer clusterer;

	@BeforeEach
	void setup() {
		clusterer = new DendogramTestCaseClusterer();
	}

	@Test
	void oneTestCase() throws Exception {
		TestCase test = new TestCase("test", Arrays.asList(), Arrays.asList());
		SimilarityAssessment assessment = new SimilarityAssessment(test, test, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessment));
		List<List<TestCase>> cluster = clusterer.cluster(similarityReport);
		assertEquals(1, cluster.size());
		assertEquals(1, cluster.get(0).size());
		assertEquals(test, cluster.get(0).get(0));
	}

	@Test
	void twoTestCasesWithSimilarityEqualsToOne() throws Exception {
		TestCase testAlfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase testBeta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		SimilarityAssessment assessmentAlfa = new SimilarityAssessment(testAlfa, testAlfa, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(testAlfa, testBeta, BigDecimal.ONE);
		SimilarityAssessment assessmentBeta = new SimilarityAssessment(testBeta, testBeta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(testBeta, testAlfa, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfa, assessmentAlfaBeta, assessmentBeta, assessmentBetaAlfa));
		List<List<TestCase>> cluster = clusterer.cluster(similarityReport);
		assertEquals(1, cluster.size());
		assertEquals(2, cluster.get(0).size());
		assertEquals(testAlfa, cluster.get(0).get(0));
		assertEquals(testBeta, cluster.get(0).get(1));
	}

}
