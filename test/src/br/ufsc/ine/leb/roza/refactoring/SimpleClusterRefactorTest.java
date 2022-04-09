package br.ufsc.ine.leb.roza.refactoring;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.clustering.Cluster;
import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.parsing.RozaStatement;
import br.ufsc.ine.leb.roza.parsing.TestClass;

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
		Cluster alphaCluster = new Cluster(alpha);

		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
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
		Cluster alphaCluster = new Cluster(alpha);

		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
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

	@Test
	void oneClusterWithOneNonEmptyTest() throws Exception {
		RozaStatement fixture = new RozaStatement("Integer value = 10;");
		RozaStatement assertion = new RozaStatement("assertEquals(10, value);");
		TestCase alpha = new TestCase("alpha", Arrays.asList(fixture), Arrays.asList(assertion));
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
		assertEquals(2, classes.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals(fixture, classes.get(0).getTestMethods().get(0).getStatements().get(0));
		assertEquals(assertion, classes.get(0).getTestMethods().get(0).getStatements().get(1));
	}

	@Test
	void oneClustersWithTwoNonEmptyTests() throws Exception {
		RozaStatement fixtureAlpha = new RozaStatement("Integer value = 10;");
		RozaStatement assertionAlpha = new RozaStatement("assertEquals(10, value);");
		TestCase alpha = new TestCase("alpha", Arrays.asList(fixtureAlpha), Arrays.asList(assertionAlpha));
		Cluster alphaCluster = new Cluster(alpha);

		RozaStatement fixtureBeta = new RozaStatement("Integer value = 20;");
		RozaStatement assertionBeta = new RozaStatement("assertEquals(20, value);");
		TestCase beta = new TestCase("beta", Arrays.asList(fixtureBeta), Arrays.asList(assertionBeta));
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
		assertEquals(2, classes.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals(fixtureAlpha, classes.get(0).getTestMethods().get(0).getStatements().get(0));
		assertEquals(assertionAlpha, classes.get(0).getTestMethods().get(0).getStatements().get(1));

		assertEquals("beta", classes.get(0).getTestMethods().get(1).getName());
		assertEquals(2, classes.get(0).getTestMethods().get(1).getStatements().size());
		assertEquals(fixtureBeta, classes.get(0).getTestMethods().get(1).getStatements().get(0));
		assertEquals(assertionBeta, classes.get(0).getTestMethods().get(1).getStatements().get(1));
	}

	@Test
	void oneClustersWithTwoNonEmptyTestsAndReusableStatement() throws Exception {
		RozaStatement fixtureAlpha = new RozaStatement("System.out.println(0);");
		RozaStatement assertionAlpha = new RozaStatement("assertTrue(true);");
		TestCase alpha = new TestCase("alpha", Arrays.asList(fixtureAlpha), Arrays.asList(assertionAlpha));
		Cluster alphaCluster = new Cluster(alpha);

		RozaStatement fixtureBeta = new RozaStatement("System.out.println(0);");
		RozaStatement assertionBeta = new RozaStatement("assertTrue(true);");
		TestCase beta = new TestCase("beta", Arrays.asList(fixtureBeta), Arrays.asList(assertionBeta));
		Cluster betaCluster = new Cluster(beta);

		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		Set<Cluster> clusters = new HashSet<>();
		clusters.add(alphaBetaCluster);
		List<TestClass> classes = refactor.refactor(clusters);

		assertEquals(1, classes.size());

		assertEquals("RefactoredTestClass1", classes.get(0).getName());
		assertEquals(0, classes.get(0).getFields().size());
		assertEquals(1, classes.get(0).getSetupMethods().size());
		assertEquals("setup", classes.get(0).getSetupMethods().get(0).getName());
		assertEquals(1, classes.get(0).getSetupMethods().get(0).getStatements().size());
		assertEquals(fixtureAlpha, classes.get(0).getSetupMethods().get(0).getStatements().get(0));
		assertEquals(fixtureBeta, classes.get(0).getSetupMethods().get(0).getStatements().get(0));
		assertEquals(2, classes.get(0).getTestMethods().size());

		assertEquals("alpha", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(1, classes.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals(assertionAlpha, classes.get(0).getTestMethods().get(0).getStatements().get(0));

		assertEquals("beta", classes.get(0).getTestMethods().get(1).getName());
		assertEquals(1, classes.get(0).getTestMethods().get(1).getStatements().size());
		assertEquals(assertionBeta, classes.get(0).getTestMethods().get(1).getStatements().get(0));
	}

	@Test
	void oneClustersWithTwoNonEmptyTestsAndReusableStatementAndFixture() throws Exception {
		RozaStatement fixtureAlpha = new RozaStatement("Integer value = 10;");
		RozaStatement assertionAlpha = new RozaStatement("assertEquals(value, 10);");
		TestCase alpha = new TestCase("alpha", Arrays.asList(fixtureAlpha), Arrays.asList(assertionAlpha));
		Cluster alphaCluster = new Cluster(alpha);

		RozaStatement fixtureBeta = new RozaStatement("Integer value = 10;");
		RozaStatement assertionBeta = new RozaStatement("assertEquals(\"10\", value.toString());");
		TestCase beta = new TestCase("beta", Arrays.asList(fixtureBeta), Arrays.asList(assertionBeta));
		Cluster betaCluster = new Cluster(beta);

		RozaStatement fixture = new RozaStatement("value = 10;");

		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		Set<Cluster> clusters = new HashSet<>();
		clusters.add(alphaBetaCluster);
		List<TestClass> classes = refactor.refactor(clusters);

		assertEquals(1, classes.size());

		assertEquals("RefactoredTestClass1", classes.get(0).getName());
		assertEquals(1, classes.get(0).getFields().size());
		assertEquals("Integer", classes.get(0).getFields().get(0).getType());
		assertEquals("value", classes.get(0).getFields().get(0).getName());
		assertEquals(1, classes.get(0).getSetupMethods().size());
		assertEquals("setup", classes.get(0).getSetupMethods().get(0).getName());
		assertEquals(1, classes.get(0).getSetupMethods().get(0).getStatements().size());
		assertEquals(fixture, classes.get(0).getSetupMethods().get(0).getStatements().get(0));
		assertEquals(2, classes.get(0).getTestMethods().size());

		assertEquals("alpha", classes.get(0).getTestMethods().get(0).getName());
		assertEquals(1, classes.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals(assertionAlpha, classes.get(0).getTestMethods().get(0).getStatements().get(0));

		assertEquals("beta", classes.get(0).getTestMethods().get(1).getName());
		assertEquals(1, classes.get(0).getTestMethods().get(1).getStatements().size());
		assertEquals(assertionBeta, classes.get(0).getTestMethods().get(1).getStatements().get(0));
	}

}
