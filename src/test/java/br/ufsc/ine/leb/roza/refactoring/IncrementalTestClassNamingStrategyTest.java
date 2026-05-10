package br.ufsc.ine.leb.roza.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;


public class IncrementalTestClassNamingStrategyTest {

	@Test
	void oneTestClass() {
		TestClassNamingStrategy stragegy = new IncrementalTestClassNamingStrategy();
		assertEquals("RefactoredTestClass1", stragegy.nominate());
	}

	@Test
	void twoTestClasses() {
		TestClassNamingStrategy stragegy = new IncrementalTestClassNamingStrategy();
		stragegy.nominate();
		assertEquals("RefactoredTestClass2", stragegy.nominate());
	}

}
