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

class InsecureRefereeTest {

	private Referee referee;
	private Combination alphaBetaCombinedDelta;
	private Combination gammaCombinedDelta;
	private Combination deltaCombinedGamma;
	private Combination alphaCombinedBeta;
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
		deltaCombinedGamma = new Combination(deltaCluster, gammaCluster);
		alphaCombinedBeta = new Combination(alphaCluster, betaCluster);
		collectionUtils = new CollectionUtils();
		referee = new InsecureReferee();
	}

	@Test
	void zero() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set());
		assertEquals(0, tiebreak.size());
	}

	@Test
	void one() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(alphaBetaCombinedDelta));
		assertEquals(1, tiebreak.size());
		assertTrue(tiebreak.contains(alphaBetaCombinedDelta));
	}

	@Test
	void two() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(gammaCombinedDelta, alphaBetaCombinedDelta));
		assertEquals(2, tiebreak.size());
		assertTrue(tiebreak.contains(gammaCombinedDelta));
		assertTrue(tiebreak.contains(alphaBetaCombinedDelta));
	}

	@Test
	void twoEquals() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(gammaCombinedDelta, deltaCombinedGamma));
		assertEquals(1, tiebreak.size());
		assertTrue(tiebreak.contains(gammaCombinedDelta));
		assertTrue(tiebreak.contains(deltaCombinedGamma));
	}

	@Test
	void three() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(gammaCombinedDelta, alphaBetaCombinedDelta, alphaCombinedBeta));
		assertEquals(3, tiebreak.size());
		assertTrue(tiebreak.contains(gammaCombinedDelta));
		assertTrue(tiebreak.contains(alphaBetaCombinedDelta));
		assertTrue(tiebreak.contains(alphaCombinedBeta));
	}

}
