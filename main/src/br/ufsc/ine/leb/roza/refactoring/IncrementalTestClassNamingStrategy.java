package br.ufsc.ine.leb.roza.refactoring;



public class IncrementalTestClassNamingStrategy implements TestClassNamingStrategy {

	private Integer counter;

	public IncrementalTestClassNamingStrategy() {
		counter = 0;
	}

	@Override
	public String nominate() {
		return String.format("RefactoredTestClass%d", ++counter);
	}

}
