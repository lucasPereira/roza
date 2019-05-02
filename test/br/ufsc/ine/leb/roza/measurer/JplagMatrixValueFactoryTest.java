package br.ufsc.ine.leb.roza.measurer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.measurer.JplagMatrixValueFactory;
import br.ufsc.ine.leb.roza.support.matrix.MatrixValueFactory;

public class JplagMatrixValueFactoryTest {

	private MatrixValueFactory<TestCaseMaterialization, BigDecimal> factory;

	@BeforeEach
	void setup() {
		factory = new JplagMatrixValueFactory();
	}

	@Test
	void same() throws Exception {
		TestCase testCase = new TestCase("test", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("Materialization.java"), 10, testCase);
		assertEquals(BigDecimal.ONE, factory.create(materialization, materialization));
	}

	@Test
	void notSame() throws Exception {
		TestCase testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materializationA = new TestCaseMaterialization(new File("MaterializationA.java"), 10, testCaseA);
		TestCaseMaterialization materializationB = new TestCaseMaterialization(new File("MaterializationB.java"), 10, testCaseB);
		assertEquals(BigDecimal.ZERO, factory.create(materializationA, materializationB));
		assertEquals(BigDecimal.ZERO, factory.create(materializationB, materializationA));
	}

}
