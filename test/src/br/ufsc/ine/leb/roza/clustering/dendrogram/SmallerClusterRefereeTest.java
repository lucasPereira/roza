package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.utils.CollectionUtils;

public class SmallerClusterRefereeTest {

	private Referee referee;
	private Combination alphaBetaCombinedDelta;
	private Combination gammaCombinedDelta;
	private Combination alphaCombinedBeta;
	private Combination deltaCombinedGamma;
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
		referee = new SmallerClusterReferee();
	}

	@Test
	void withoutElements() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set());
		assertEquals(0, tiebreak.size());
	}

	@Test
	void chooseUnique() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(alphaBetaCombinedDelta));
		assertEquals(1, tiebreak.size());
		assertTrue(tiebreak.contains(alphaBetaCombinedDelta));
	}

	@Test
	void chooseFirst() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(gammaCombinedDelta, alphaBetaCombinedDelta));
		assertEquals(1, tiebreak.size());
		assertTrue(tiebreak.contains(gammaCombinedDelta));
	}

	@Test
	void chooseLast() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(alphaBetaCombinedDelta, gammaCombinedDelta));
		assertEquals(1, tiebreak.size());
		assertTrue(tiebreak.contains(gammaCombinedDelta));
	}

	@Test
	void chooseFirstAndLast() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(gammaCombinedDelta, alphaBetaCombinedDelta, alphaCombinedBeta));
		assertEquals(2, tiebreak.size());
		assertTrue(tiebreak.contains(gammaCombinedDelta));
		assertTrue(tiebreak.contains(alphaCombinedBeta));
	}

	@Test
	void chooseFirstAndLastEquals() throws Exception {
		Set<Combination> tiebreak = referee.untie(collectionUtils.set(gammaCombinedDelta, alphaBetaCombinedDelta, deltaCombinedGamma));
		assertEquals(1, tiebreak.size());
		assertTrue(tiebreak.contains(gammaCombinedDelta));
		assertTrue(tiebreak.contains(deltaCombinedGamma));
	}

}
