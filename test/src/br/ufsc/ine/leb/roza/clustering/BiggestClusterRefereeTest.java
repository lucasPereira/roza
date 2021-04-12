package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.utils.CollectionUtils;

class BiggestClusterRefereeTest {

	private Referee referee;
	private Combination alphaBetaCombinedDelta;
	private Combination gammaCombinedDelta;
	private Combination deltaCombinedAlphaBeta;
	private Combination alphaBetaCombinedGamma;
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
		deltaCombinedAlphaBeta= new Combination(deltaCluster, alphaBetaCluster);
		alphaBetaCombinedGamma= new Combination(alphaBetaCluster, gammaCluster);
		gammaCombinedDelta = new Combination(gammaCluster, deltaCluster);
		collectionUtils = new CollectionUtils();
		referee = new BiggestClusterReferee();
	}

	@Test
	void withoutElements() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set());
		assertEquals(0, tiebreak.size());
	}

	@Test
	void chooseUnique() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(gammaCombinedDelta));
		assertEquals(1, tiebreak.size());
		assertTrue(tiebreak.contains(gammaCombinedDelta));
	}

	@Test
	void chooseFirst() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(gammaCombinedDelta, alphaBetaCombinedDelta));
		assertEquals(1, tiebreak.size());
		assertTrue(tiebreak.contains(alphaBetaCombinedDelta));
	}

	@Test
	void chooseLast() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(alphaBetaCombinedDelta, gammaCombinedDelta));
		assertEquals(1, tiebreak.size());
		assertTrue(tiebreak.contains(alphaBetaCombinedDelta));
	}

	@Test
	void chooseFirstAndLast() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(alphaBetaCombinedDelta, gammaCombinedDelta, alphaBetaCombinedGamma));
		assertEquals(2, tiebreak.size());
		assertTrue(tiebreak.contains(alphaBetaCombinedDelta));
		assertTrue(tiebreak.contains(alphaBetaCombinedGamma));
	}

	@Test
	void chooseFirstAndLastEquals() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(alphaBetaCombinedDelta, gammaCombinedDelta, deltaCombinedAlphaBeta));
		assertEquals(1, tiebreak.size());
		assertTrue(tiebreak.contains(alphaBetaCombinedDelta));
		assertTrue(tiebreak.contains(deltaCombinedAlphaBeta));
	}

}
