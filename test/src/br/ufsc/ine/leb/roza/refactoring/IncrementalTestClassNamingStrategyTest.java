package br.ufsc.ine.leb.roza.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestMethod;

public class IncrementalTestClassNamingStrategyTest {

	@Test
	void oneTestClass() throws Exception {
		TestMethod testMethod = new TestMethod("test", Arrays.asList());
		TestClassNamingStrategy stragegy = new IncrementalTestClassNamingStrategy();
		assertEquals("RefactoredTestClass1", stragegy.nominate(Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod)));
	}

	@Test
	void twoTestClasses() throws Exception {
		TestClassNamingStrategy stragegy = new IncrementalTestClassNamingStrategy();
		stragegy.nominate(Arrays.asList(), Arrays.asList(), Arrays.asList());
		assertEquals("RefactoredTestClass2", stragegy.nominate(Arrays.asList(), Arrays.asList(), Arrays.asList()));
	}

}
