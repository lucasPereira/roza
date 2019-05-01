package br.ufsc.ine.leb.roza.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;

public class MatrixPairTest {

	@Test
	void create() throws Exception {
		TestCase testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		MatrixPair<TestCase, Integer> pair = new MatrixPair<>(testCaseA, testCaseB, 10);
		assertEquals(testCaseA, pair.getSource());
		assertEquals(testCaseB, pair.getTarget());
		assertEquals(10, pair.getValue().intValue());
	}

}
