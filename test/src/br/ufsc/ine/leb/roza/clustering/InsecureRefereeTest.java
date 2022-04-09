package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.NoCombinationToChooseException;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;
import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.utils.CollectionUtils;

class InsecureRefereeTest {

	private Referee referee;
	private Combination alphaBetaCombinedDelta;
	private Combination gammaCombinedDelta;
	private CollectionUtils collectionUtils;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		TestCase delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster gammaCluster = new Cluster(gamma);
		Cluster deltaCluster = new Cluster(delta);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		alphaBetaCombinedDelta = new Combination(alphaBetaCluster, deltaCluster);
		gammaCombinedDelta = new Combination(gammaCluster, deltaCluster);
		collectionUtils = new CollectionUtils();
		referee = new InsecureReferee();
	}

	@Test
	void zero() throws Exception {
		assertThrows(NoCombinationToChooseException.class, () -> referee.untie(collectionUtils.set()));
	}

	@Test
	void one() throws Exception {
		Combination chosen = referee.untie(collectionUtils.set(alphaBetaCombinedDelta));
		assertEquals(chosen, alphaBetaCombinedDelta);
	}

	@Test
	void two() throws Exception {
		assertThrows(TiebreakException.class, () -> referee.untie(collectionUtils.set(gammaCombinedDelta, alphaBetaCombinedDelta)));
	}

}
