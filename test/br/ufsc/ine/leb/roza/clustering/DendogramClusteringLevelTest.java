package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.NoNextLevelException;

public class DendogramClusteringLevelTest {

	private TestCase alfa;
	private TestCase beta;
	private TestCase gamma;
	private DendogramCluster clusterAlfa;
	private DendogramCluster clusterBeta;
	private DendogramCluster clusterGamma;
	private SimilarityReport similarityReport;

	@BeforeEach
	void setup() {
		alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		clusterAlfa = new DendogramCluster(alfa);
		clusterBeta = new DendogramCluster(beta);
		clusterGamma= new DendogramCluster(gamma);
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alfa, alfa, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alfa, beta, new BigDecimal("0.4"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alfa, gamma, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alfa, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, BigDecimal.ONE);
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alfa, BigDecimal.ONE);
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma));
	}

	@Test
	void oneElement() throws Exception {
		DendogramClusteringLevel one = new DendogramClusteringLevel(Arrays.asList(clusterAlfa), similarityReport);

		assertFalse(one.hasNextLevel());
		assertEquals(1, one.getClusters().size());
		assertEquals(1, one.getClusters().get(0).getElements().size());
		assertEquals(alfa, one.getClusters().get(0).getElements().get(0));
	}

	@Test
	void twoElements() throws Exception {
		DendogramClusteringLevel one = new DendogramClusteringLevel(Arrays.asList(clusterAlfa, clusterBeta), similarityReport);
		DendogramClusteringLevel two = one.generateNextLevel();

		assertTrue(one.hasNextLevel());
		assertEquals(2, one.getClusters().size());
		assertEquals(1, one.getClusters().get(0).getElements().size());
		assertEquals(alfa, one.getClusters().get(0).getElements().get(0));
		assertEquals(1, one.getClusters().get(1).getElements().size());
		assertEquals(beta, one.getClusters().get(1).getElements().get(0));

		assertFalse(two.hasNextLevel());
		assertEquals(1, two.getClusters().size());
		assertEquals(2, two.getClusters().get(0).getElements().size());
		assertEquals(alfa, two.getClusters().get(0).getElements().get(0));
		assertEquals(beta, two.getClusters().get(0).getElements().get(1));
	}

	@Test
	void threeElements() throws Exception {
		DendogramClusteringLevel one = new DendogramClusteringLevel(Arrays.asList(clusterAlfa, clusterBeta, clusterGamma), similarityReport);
		DendogramClusteringLevel two = one.generateNextLevel();
		DendogramClusteringLevel three = two.generateNextLevel();

		assertTrue(one.hasNextLevel());
		assertEquals(3, one.getClusters().size());
		assertEquals(1, one.getClusters().get(0).getElements().size());
		assertEquals(alfa, one.getClusters().get(0).getElements().get(0));
		assertEquals(1, one.getClusters().get(1).getElements().size());
		assertEquals(beta, one.getClusters().get(1).getElements().get(0));
		assertEquals(1, one.getClusters().get(2).getElements().size());
		assertEquals(gamma, one.getClusters().get(2).getElements().get(0));

		assertTrue(two.hasNextLevel());
		assertEquals(2, two.getClusters().size());
		assertEquals(2, two.getClusters().get(0).getElements().size());
		assertEquals(alfa, two.getClusters().get(0).getElements().get(0));
		assertEquals(beta, two.getClusters().get(0).getElements().get(1));
		assertEquals(1, two.getClusters().get(1).getElements().size());
		assertEquals(gamma, two.getClusters().get(1).getElements().get(0));

		assertFalse(three.hasNextLevel());
		assertEquals(1, three.getClusters().size());
		assertEquals(3, three.getClusters().get(0).getElements().size());
		assertEquals(alfa, three.getClusters().get(0).getElements().get(0));
		assertEquals(beta, three.getClusters().get(0).getElements().get(1));
		assertEquals(gamma, three.getClusters().get(0).getElements().get(2));
	}

	@Test
	void levelUpWithoutNextLevel() throws Exception {
		assertThrows(NoNextLevelException.class, () -> {
			new DendogramClusteringLevel(Arrays.asList(clusterAlfa), similarityReport).generateNextLevel();
		});
	}

}
