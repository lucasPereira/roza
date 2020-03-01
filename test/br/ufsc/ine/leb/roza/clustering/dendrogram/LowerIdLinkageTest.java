package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.utils.CollectionUtils;

public class LowerIdLinkageTest {

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
	void twoSingleElementClusters() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Combination alphaBetaCombination = linkage.link(new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster)));
		assertEquals(alphaBetaCombination, new Combination(alphaCluster, betaCluster));
		assertEquals(alphaBetaCombination, new Combination(betaCluster, alphaCluster));
	}

	@Test
	void threeSingleElementClusters() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster gammaCluster = new Cluster(gamma);
		Combination alphaBetaCombination = linkage.link(new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster)));
		assertEquals(alphaBetaCombination, new Combination(alphaCluster, betaCluster));
		assertEquals(alphaBetaCombination, new Combination(betaCluster, alphaCluster));
	}

	@Test
	void oneTwoElementsClusterAndOneSingleElementCluster() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster gammaCluster = new Cluster(gamma);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		Combination alphaBetaGammaCombination= linkage.link(new ClustersToMerge(collectionUtils.set(alphaBetaCluster, gammaCluster)));
		assertEquals(alphaBetaGammaCombination, new Combination(alphaBetaCluster, gammaCluster));
		assertEquals(alphaBetaGammaCombination, new Combination(gammaCluster, alphaBetaCluster));
	}

}
