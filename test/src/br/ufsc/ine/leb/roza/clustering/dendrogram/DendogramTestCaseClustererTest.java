package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.clustering.TestCaseClusterer;

public class DendogramTestCaseClustererTest {

	@Test
	void zeroTests() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		TestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		assertEquals(0, clusterer.cluster(report).size());
	}

	@Test
	void oneTest() throws Exception {
		TestCase testA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		SimilarityReport report = new SimilarityReportBuilder(true).add(testA).build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		TestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		Set<Cluster> clusters = clusterer.cluster(report);
		assertEquals(1, clusters.size());
		assertTrue(clusters.contains(new Cluster(testA)));
	}

	@Test
	void twoDistinctTestsStopingInZero() throws Exception {
		TestCase testA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		SimilarityReport report = new SimilarityReportBuilder(true).add(testA).add(testB).complete().build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new SimilarityBasedCriteria(BigDecimal.ZERO);
		TestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		Set<Cluster> clusters = clusterer.cluster(report);
		assertEquals(2, clusters.size());
		assertTrue(clusters.contains(new Cluster(testA)));
		assertTrue(clusters.contains(new Cluster(testB)));
	}

	@Test
	void twosSimilarTestsStopingInZero() throws Exception {
		TestCase testA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		Cluster clusterA = new Cluster(testA);
		Cluster clusterB = new Cluster(testB);
		Cluster clusterAB = clusterA.merge(clusterB);
		SimilarityReport report = new SimilarityReportBuilder(true).add(testA, testB, new BigDecimal("0.5")).build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new SimilarityBasedCriteria(BigDecimal.ZERO);
		TestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		Set<Cluster> clusters = clusterer.cluster(report);
		assertEquals(1, clusters.size());
		assertTrue(clusters.contains(clusterAB));
	}

	@Test
	void twoDistinctTestsDoesNotStopAtAll() throws Exception {
		TestCase testA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		SimilarityReport report = new SimilarityReportBuilder(true).add(testA).add(testB).complete().build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		TestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		Set<Cluster> clusters = clusterer.cluster(report);
		assertEquals(1, clusters.size());
		assertTrue(clusters.contains(new Cluster(testA).merge(new Cluster(testB))));
	}

}
