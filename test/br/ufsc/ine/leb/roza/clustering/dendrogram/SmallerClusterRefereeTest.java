package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;

public class SmallerClusterRefereeTest {

	private Combination alphaBetaAndDelta;
	private Combination gammaAndDelta;
	private Combination deltaAndGamma;
	private Referee referee;

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
		alphaBetaAndDelta = new Combination(alphaBetaCluster, deltaCluster);
		gammaAndDelta = new Combination(gammaCluster, deltaCluster);
		deltaAndGamma = new Combination(deltaCluster, gammaCluster);
		referee = new SmallerClusterReferee();
	}

	@Test
	void chooseFirst() throws Exception {
		Combination tiebreak = referee.untie(gammaAndDelta, alphaBetaAndDelta);
		assertEquals(tiebreak, gammaAndDelta);
	}

	@Test
	void chooseSecond() throws Exception {
		Combination tiebreak = referee.untie(alphaBetaAndDelta, gammaAndDelta);
		assertEquals(tiebreak, gammaAndDelta);
	}

	@Test
	void chooseNone() throws Exception {
		Combination tiebreak = referee.untie(gammaAndDelta, deltaAndGamma);
		assertNull(tiebreak);
	}

	@Test
	void firstNull() throws Exception {
		Combination tiebreak = referee.untie(null, gammaAndDelta);
		assertEquals(tiebreak, gammaAndDelta);
	}

	@Test
	void secondNull() throws Exception {
		Combination tiebreak = referee.untie(deltaAndGamma, null);
		assertEquals(tiebreak, deltaAndGamma);
	}

	@Test
	void bothNull() throws Exception {
		Combination tiebreak = referee.untie(null, null);
		assertNull(tiebreak);
	}

}
