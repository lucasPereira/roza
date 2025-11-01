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

class BiggestClusterRefereeTest {

	private Referee referee;
	private Combination alphaBetaCombinedDelta;
	private Combination gammaCombinedDelta;
	private Combination deltaCombinedAlphaBeta;
	private Combination alphaBetaCombinedGamma;
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
		deltaCombinedAlphaBeta = new Combination(deltaCluster, alphaBetaCluster);
		alphaBetaCombinedGamma = new Combination(alphaBetaCluster, gammaCluster);
		gammaCombinedDelta = new Combination(gammaCluster, deltaCluster);
		collectionUtils = new CollectionUtils();
		referee = new BiggestClusterReferee();
	}

	@Test
	void withoutElements() {
		assertThrows(NoCombinationToChooseException.class, () -> referee.untie(collectionUtils.set()));
	}

	@Test
	void chooseUnique() {
		Combination chosen = referee.untie(collectionUtils.set(gammaCombinedDelta));
		assertEquals(chosen, gammaCombinedDelta);
	}

	@Test
	void chooseFirst() {
		Combination chosen = referee.untie(collectionUtils.set(gammaCombinedDelta, alphaBetaCombinedDelta));
		assertEquals(chosen, alphaBetaCombinedDelta);
	}

	@Test
	void chooseLast() {
		Combination chosen = referee.untie(collectionUtils.set(alphaBetaCombinedDelta, gammaCombinedDelta));
		assertEquals(chosen, alphaBetaCombinedDelta);
	}

	@Test
	void tiebreak() {
		assertThrows(TiebreakException.class, () -> referee.untie(collectionUtils.set(alphaBetaCombinedDelta, gammaCombinedDelta, alphaBetaCombinedGamma)));
	}

	@Test
	void chooseFirstAndLastEquals() {
		Combination chosen = referee.untie(collectionUtils.set(alphaBetaCombinedDelta, gammaCombinedDelta, deltaCombinedAlphaBeta));
		assertEquals(chosen, alphaBetaCombinedDelta);
		assertEquals(chosen, deltaCombinedAlphaBeta);
	}

}
