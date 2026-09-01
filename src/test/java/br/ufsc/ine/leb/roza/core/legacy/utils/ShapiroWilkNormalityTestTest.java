package br.ufsc.ine.leb.roza.core.legacy.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.legacy.utils.ShapiroWilkNormalityTest.NormalityTestResult;

class ShapiroWilkNormalityTestTest {

	private final ShapiroWilkNormalityTest test = new ShapiroWilkNormalityTest();

	@Test
	void sequentialSampleAgreesWithRoystonReference() {
		double[] sample = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		NormalityTestResult result = test.test(sample);
		assertEquals(0.9703, result.getTestStatistic(), 0.002);
		assertEquals(0.8924, result.getPValue(), 0.02);
		assertTrue(result.isNormal());
	}

	@Test
	void skewedSampleRejectsNormality() {
		double[] sample = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 100, 200, 800 };
		NormalityTestResult result = test.test(sample);
		assertFalse(result.isNormal());
		assertTrue(result.getPValue() < 0.05);
	}
}
