package br.ufsc.ine.leb.roza.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;

public class SimpleClusterRefactorTest {

	private TestClassNamingStrategy namingStrategy;
	private ClusterRefactor refactor;

	@BeforeEach
	void setup() {
		namingStrategy = new IncrementalTestClassNamingStrategy();
		refactor = new SimpleClusterRefactor(namingStrategy);
	}

	@Test
	void withoutClusters() throws Exception {
		List<TestClass> classes = refactor.refactor(new HashSet<>());
		assertEquals(0, classes.size());
	}

	@Test
	void oneClusterWithOneEmptyTest() throws Exception {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		Cluster alphaCluster = new Cluster(alpha);
		Set<Cluster> clusters = new HashSet<>();
		clusters.add(alphaCluster);
		List<TestClass> classes = refactor.refactor(clusters);

		assertEquals(1, classes.size());
		assertEquals("RefactoredTestClass1", classes.get(0).getName());
		assertEquals(0, classes.get(0).getFields().size());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(1, classes.get(0).getTestMethods().size());
		assertEquals("alpha", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(0, classes.get(0).getTestMethods().get(0).getStatements().size());
	}

	@Test
	void oneClusterWithTwoEmptyTests() throws Exception {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		Set<Cluster> clusters = new HashSet<>();
		clusters.add(alphaBetaCluster);
		List<TestClass> classes = refactor.refactor(clusters);

		assertEquals(1, classes.size());
		assertEquals("RefactoredTestClass1", classes.get(0).getName());
		assertEquals(0, classes.get(0).getFields().size());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(2, classes.get(0).getTestMethods().size());
		assertEquals("alpha", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(0, classes.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("beta", classes.get(0).getTestMethods().get(1).getName());
		assertEquals(0, classes.get(0).getTestMethods().get(1).getStatements().size());
	}

	@Test
	void twoClustersWithOneEmptyTestEach() throws Exception {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Set<Cluster> clusters = new HashSet<>();
		clusters.add(alphaCluster);
		clusters.add(betaCluster);
		List<TestClass> classes = refactor.refactor(clusters);

		assertEquals(2, classes.size());

		assertEquals("RefactoredTestClass1", classes.get(0).getName());
		assertEquals(0, classes.get(0).getFields().size());
		assertEquals(0, classes.get(0).getSetupMethods().size());
		assertEquals(1, classes.get(0).getTestMethods().size());
		assertEquals("alpha", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(0, classes.get(0).getTestMethods().get(0).getStatements().size());

		assertEquals("RefactoredTestClass2", classes.get(1).getName());
		assertEquals(0, classes.get(1).getFields().size());
		assertEquals(0, classes.get(1).getSetupMethods().size());
		assertEquals(1, classes.get(1).getTestMethods().size());
		assertEquals("beta", classes.get(1).getTestMethods().get(0).getName());
		assertEquals(0, classes.get(1).getTestMethods().get(0).getStatements().size());
	}

}
