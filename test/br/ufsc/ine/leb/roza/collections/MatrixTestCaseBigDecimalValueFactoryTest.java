package br.ufsc.ine.leb.roza.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;

public class MatrixTestCaseBigDecimalValueFactoryTest {

	private MatrixValueFactory<TestCase, BigDecimal> factory;

	@BeforeEach
	void setup() {
		factory = new MatrixTestCaseBigDecimalValueFactory();
	}

	@Test
	void same() throws Exception {
		TestCase testCase = new TestCase("test", Arrays.asList(), Arrays.asList());
		assertEquals(BigDecimal.ONE, factory.create(testCase, testCase));
	}

	@Test
	void notSame() throws Exception {
		TestCase testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		assertEquals(BigDecimal.ZERO, factory.create(testCaseA, testCaseB));
		assertEquals(BigDecimal.ZERO, factory.create(testCaseB, testCaseA));
	}

}
