package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.clustering.dendrogram.Cluster;
import br.ufsc.ine.leb.roza.clustering.dendrogram.ClusterFactory;

public class ClusterFactoryTest {

	private TestCase alfa;
	private TestCase beta;
	private SimilarityReport similarityReport;
	private ClusterFactory factory;

	@BeforeEach
	void setup() {
		alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alfa, alfa, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alfa, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alfa, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentBetaAlfa, assessmentBetaBeta));
		factory = new ClusterFactory();
	}

	@Test
	void create() throws Exception {
		List<Cluster> clusters = factory.create(similarityReport);
		assertEquals(2, clusters.size());
		assertEquals(1, clusters.get(0).getElements().size());
		assertEquals(alfa, clusters.get(0).getElements().get(0));
		assertEquals(1, clusters.get(1).getElements().size());
		assertEquals(beta, clusters.get(1).getElements().get(0));
	}

}
