package br.ufsc.ine.leb.roza.measurement.matrix.simian;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.measurement.intersector.Intersector;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixValueFactory;

class SimianMatrixValueFactoryTest {

	private MatrixValueFactory<TestCaseMaterialization, Intersector> factory;

	@BeforeEach
	void setup() {
		factory = new SimianMatrixValueFactory();
	}

	@Test
	void same() {
		TestCase testCase = new TestCase("test", List.of(), List.of());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("Materialization.java"), 10, testCase);
		assertEquals(BigDecimal.ONE, factory.create(materialization, materialization).evaluate());
	}

	@Test
	void notSame() {
		TestCase testCaseA = new TestCase("testA", List.of(), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(), List.of());
		TestCaseMaterialization materializationA = new TestCaseMaterialization(new File("MaterializationA.java"), 10, testCaseA);
		TestCaseMaterialization materializationB = new TestCaseMaterialization(new File("MaterializationB.java"), 10, testCaseB);
		assertEquals(BigDecimal.ZERO, factory.create(materializationA, materializationB).evaluate());
		assertEquals(BigDecimal.ZERO, factory.create(materializationB, materializationA).evaluate());
	}

}
