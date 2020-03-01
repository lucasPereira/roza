package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.NoNextLevelException;
import br.ufsc.ine.leb.roza.utils.CollectionUtils;

public class LevelTest {

	private TestCase alpha;
	private TestCase beta;
	private TestCase gamma;
	private Linkage linkage;
	private CollectionUtils collectionUtils;

	@BeforeEach
	void setup() {
		linkage = new LowerIdLinkage();
		collectionUtils = new CollectionUtils();
		alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
	}

	@Test
	void oneElement() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);

		Level one = new Level(linkage, collectionUtils.set(alphaCluster));
		assertFalse(one.hasNextLevel());
		assertEquals(1, one.getClusters().size());
		assertTrue(one.getClusters().contains(alphaCluster));
	}

	@Test
	void twoElements() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);

		Level one = new Level(linkage, collectionUtils.set(alphaCluster, betaCluster));
		assertTrue(one.hasNextLevel());
		assertEquals(2, one.getClusters().size());
		assertTrue(one.getClusters().contains(alphaCluster));
		assertTrue(one.getClusters().contains(betaCluster));

		Level two = one.generateNextLevel();
		assertFalse(two.hasNextLevel());
		assertEquals(1, two.getClusters().size());
		assertTrue(two.getClusters().contains(alphaBetaCluster));
	}

	@Test
	void threeElements() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster gammaCluster = new Cluster(gamma);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		Cluster alphaBetaGammaCluster = alphaBetaCluster.merge(gammaCluster);

		Level one = new Level(linkage, collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		assertTrue(one.hasNextLevel());
		assertTrue(one.getClusters().contains(alphaCluster));
		assertTrue(one.getClusters().contains(betaCluster));
		assertTrue(one.getClusters().contains(gammaCluster));

		Level two = one.generateNextLevel();
		assertTrue(two.hasNextLevel());
		assertEquals(2, two.getClusters().size());
		assertTrue(two.getClusters().contains(alphaBetaCluster));
		assertTrue(two.getClusters().contains(gammaCluster));

		Level three = two.generateNextLevel();
		assertFalse(three.hasNextLevel());
		assertEquals(1, three.getClusters().size());
		assertTrue(three.getClusters().contains(alphaBetaGammaCluster));
	}

	@Test
	void levelUpWithoutNextLevel() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		assertThrows(NoNextLevelException.class, () -> {
			new Level(linkage, collectionUtils.set(alphaCluster)).generateNextLevel();
		});
	}

}
