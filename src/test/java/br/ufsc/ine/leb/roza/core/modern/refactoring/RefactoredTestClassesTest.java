package br.ufsc.ine.leb.roza.core.modern.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;

class RefactoredTestClassesTest {

	@Test
	void shouldExposeRefactoredTestClassesImmutably() {
		TestClass testClass = new TestClass("Example", List.of(), List.of(), List.of(), List.of());
		RefactoredTestClasses refactored = new RefactoredTestClasses(List.of(testClass));

		assertEquals(List.of(testClass), refactored.testClasses());
		assertThrows(UnsupportedOperationException.class, () -> refactored.testClasses().add(testClass));
	}
}
