package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.NoCombinationToChooseException;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;
import br.ufsc.ine.leb.roza.utils.CollectionUtils;

class SmallestClusterRefereeTest {

	private Referee referee;
	private Combination alphaBetaCombinedDelta;
	private Combination gammaCombinedDelta;
	private Combination alphaCombinedBeta;
	private Combination deltaCombinedGamma;
	private CollectionUtils collectionUtils;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", List.of(), List.of());
		TestCase beta = new TestCase("beta", List.of(), List.of());
		TestCase gamma = new TestCase("gamma", List.of(), List.of());
		TestCase delta = new TestCase("delta", List.of(), List.of());
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
		referee = new SmallestClusterReferee();
	}

	@Test
	void withoutElements() {
		assertThrows(NoCombinationToChooseException.class, () -> referee.untie(collectionUtils.set()));
	}

	@Test
	void chooseUnique() {
		Combination chosen = referee.untie(collectionUtils.set(alphaBetaCombinedDelta));
		assertEquals(chosen, alphaBetaCombinedDelta);
	}

	@Test
	void chooseFirst() {
		Combination chosen = referee.untie(collectionUtils.set(gammaCombinedDelta, alphaBetaCombinedDelta));
		assertEquals(chosen, gammaCombinedDelta);
	}

	@Test
	void chooseLast() {
		Combination chosen = referee.untie(collectionUtils.set(alphaBetaCombinedDelta, gammaCombinedDelta));
		assertEquals(chosen, gammaCombinedDelta);
	}

	@Test
	void tiebreak() {
		assertThrows(TiebreakException.class, () -> referee.untie(collectionUtils.set(gammaCombinedDelta, alphaBetaCombinedDelta, alphaCombinedBeta)));
	}

	@Test
	void chooseFirstAndLastEquals() {
		Combination chosen = referee.untie(collectionUtils.set(gammaCombinedDelta, alphaBetaCombinedDelta, deltaCombinedGamma));
		assertEquals(chosen, gammaCombinedDelta);
		assertEquals(chosen, deltaCombinedGamma);
	}

}
