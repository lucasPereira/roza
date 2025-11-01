package br.ufsc.ine.leb.roza.measurement.matrix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;

class MatrixPairTest {

	@Test
	void create() {
		TestCase testCaseA = new TestCase("testA", List.of(), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(), List.of());
		MatrixPair<TestCase, Integer> pair = new MatrixPair<>(testCaseA, testCaseB, 10);
		assertEquals(testCaseA, pair.getSource());
		assertEquals(testCaseB, pair.getTarget());
		assertEquals(10, pair.getValue().intValue());
	}

}
