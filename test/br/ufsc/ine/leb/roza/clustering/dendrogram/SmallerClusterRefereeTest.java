package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;

public class SmallerClusterRefereeTest {

	private Combination alphaBetaCombinedDelta;
	private Combination gammaCombinedDelta;
	private Combination deltaCombinedGamma;
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
		alphaBetaCombinedDelta = new Combination(alphaBetaCluster, deltaCluster);
		gammaCombinedDelta = new Combination(gammaCluster, deltaCluster);
		deltaCombinedGamma = new Combination(deltaCluster, gammaCluster);
		referee = new SmallerClusterReferee();
	}

	@Test
	void chooseFirst() throws Exception {
		Combination tiebreak = referee.untie(gammaCombinedDelta, alphaBetaCombinedDelta);
		assertEquals(tiebreak, gammaCombinedDelta);
	}

	@Test
	void chooseSecond() throws Exception {
		Combination tiebreak = referee.untie(alphaBetaCombinedDelta, gammaCombinedDelta);
		assertEquals(tiebreak, gammaCombinedDelta);
	}

	@Test
	void chooseNone() throws Exception {
		Combination tiebreak = referee.untie(gammaCombinedDelta, deltaCombinedGamma);
		assertNull(tiebreak);
	}

	@Test
	void firstNull() throws Exception {
		Combination tiebreak = referee.untie(null, gammaCombinedDelta);
		assertEquals(tiebreak, gammaCombinedDelta);
	}

	@Test
	void secondNull() throws Exception {
		Combination tiebreak = referee.untie(deltaCombinedGamma, null);
		assertEquals(tiebreak, deltaCombinedGamma);
	}

	@Test
	void bothNull() throws Exception {
		Combination tiebreak = referee.untie(null, null);
		assertNull(tiebreak);
	}

}
