package br.ufsc.ine.leb.roza.clustering;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.InsufficientRefereeException;
import br.ufsc.ine.leb.roza.exceptions.NoCombinationToChooseException;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;
import br.ufsc.ine.leb.roza.utils.CollectionUtils;

public class ComposedRefereeTest {

	private Combination gammaCombinedDelta;
	private Combination alphaBetaCombinedDelta;
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
		alphaBetaCombinedGamma = new Combination(alphaBetaCluster, gammaCluster);
		gammaCombinedDelta = new Combination(gammaCluster, deltaCluster);
		collectionUtils = new CollectionUtils();
	}

	@Test
	void withoutReferees() {
		assertThrows(InsufficientRefereeException.class, ComposedReferee::new);
	}

	@Test
	void onlyOneReferee() {
		assertThrows(InsufficientRefereeException.class, () -> new ComposedReferee(new AnyClusterReferee()));
	}

	@Test
	void withoutElements() {
		Referee referee = new ComposedReferee(new SmallestClusterReferee(), new BiggestClusterReferee());
		assertThrows(NoCombinationToChooseException.class, () -> referee.untie(collectionUtils.set()));
	}

	@Test
	void stopAtFirst() {
		Referee referee = new ComposedReferee(new SmallestClusterReferee(), new InsecureReferee());
		Combination chosen = referee.untie(collectionUtils.set(alphaBetaCombinedDelta, gammaCombinedDelta));
		assertEquals(chosen, gammaCombinedDelta);
	}

	@Test
	void stopAtLast() {
		Referee referee = new ComposedReferee(new InsecureReferee(), new BiggestClusterReferee());
		Combination chosen = referee.untie(collectionUtils.set(alphaBetaCombinedDelta, gammaCombinedDelta));
		assertEquals(chosen, alphaBetaCombinedDelta);
	}

	@Test
	void doesNotStopAtAll() {
		Referee referee = new ComposedReferee(new SmallestClusterReferee(), new BiggestClusterReferee());
		assertThrows(TiebreakException.class, () -> referee.untie(collectionUtils.set(alphaBetaCombinedDelta, alphaBetaCombinedGamma)));
	}

}
