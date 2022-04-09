package br.ufsc.ine.leb.roza.refactoring;

import java.util.List;

import br.ufsc.ine.leb.roza.parsing.Field;
import br.ufsc.ine.leb.roza.parsing.SetupMethod;
import br.ufsc.ine.leb.roza.parsing.TestMethod;

public class IncrementalTestClassNamingStrategy implements TestClassNamingStrategy {

	private Integer counter;

	public IncrementalTestClassNamingStrategy() {
		counter = 0;
	}

	@Override
	public String nominate(List<Field> fields, List<SetupMethod> setupMethods, List<TestMethod> testMethods) {
		return String.format("RefactoredTestClass%d", ++counter);
	}

}
