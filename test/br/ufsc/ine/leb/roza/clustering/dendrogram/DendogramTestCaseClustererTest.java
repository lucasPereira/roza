package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.clustering.TestCaseClusterer;

public class DendogramTestCaseClustererTest {

	@Test
	void zeroTests() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList());
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		TestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee);

		assertEquals(0, clusterer.cluster(report).size());
	}

	@Test
	void oneTest() throws Exception {
		TestCase testA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		SimilarityAssessment assessmentAA = new SimilarityAssessment(testA, testA, BigDecimal.ONE);
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessmentAA));
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		TestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee);

		Set<Cluster> clusters = clusterer.cluster(report);
		assertEquals(1, clusters.size());
		assertTrue(clusters.contains(new Cluster(testA)));
	}

	@Test
	void twoDistinctTests() throws Exception {
		fail("Integrates with threshold criteria");
		TestCase testA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		SimilarityAssessment assessmentAA = new SimilarityAssessment(testA, testA, BigDecimal.ONE);
		SimilarityAssessment assessmentAB = new SimilarityAssessment(testA, testB, BigDecimal.ZERO);
		SimilarityAssessment assessmentBA = new SimilarityAssessment(testB, testA, BigDecimal.ZERO);
		SimilarityAssessment assessmentBB = new SimilarityAssessment(testB, testA, BigDecimal.ONE);
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessmentAA, assessmentAB, assessmentBA, assessmentBB));
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		TestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(2, clusters.size());
		assertTrue(clusters.contains(new Cluster(testA)));
		assertTrue(clusters.contains(new Cluster(testB)));
	}

	@Test
	void twosSimilarTests() throws Exception {
		TestCase testA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		Cluster clusterA = new Cluster(testA);
		Cluster clusterB = new Cluster(testB);
		Cluster clusterAB = clusterA.merge(clusterB);
		SimilarityAssessment assessmentAA = new SimilarityAssessment(testA, testA, BigDecimal.ONE);
		SimilarityAssessment assessmentAB = new SimilarityAssessment(testA, testB, new BigDecimal("0.5"));
		SimilarityAssessment assessmentBA = new SimilarityAssessment(testB, testA, new BigDecimal("0.5"));
		SimilarityAssessment assessmentBB = new SimilarityAssessment(testB, testA, BigDecimal.ONE);
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessmentAA, assessmentAB, assessmentBA, assessmentBB));
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		TestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee);

		Set<Cluster> clusters = clusterer.cluster(report);
		assertEquals(1, clusters.size());
		assertTrue(clusters.contains(clusterAB));
	}

}
