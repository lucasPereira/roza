package br.ufsc.ine.leb.roza.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;

public class Hub {

	private List<Consumer<List<TestClass>>> loadTestClassesListeners;
	private List<Consumer<TestClass>> selectTestClassListeners;
	private List<Consumer<List<TestCase>>> extractTestCasesListeners;
	private List<Consumer<TestCase>> selectTestCaseListeners;

	public Hub() {
		loadTestClassesListeners = new LinkedList<>();
		selectTestClassListeners = new LinkedList<>();
		extractTestCasesListeners = new LinkedList<>();
		selectTestCaseListeners = new LinkedList<>();
	}

	public void loadTestClassesPublish(List<TestClass> classes) {
		loadTestClassesListeners.forEach(listener -> listener.accept(classes));
	}

	public void loadTestClassesSubscribe(Consumer<List<TestClass>> listener) {
		loadTestClassesListeners.add(listener);
	}

	public void selectTestClassPublish(TestClass testClass) {
		selectTestClassListeners.forEach(listener -> listener.accept(testClass));
	}

	public void selectTestClassSubscribe(Consumer<TestClass> listener) {
		selectTestClassListeners.add(listener);
	}

	public void extractTestCasesPublish(List<TestCase> testCases) {
		extractTestCasesListeners.forEach(listener -> listener.accept(testCases));
	}

	public void extractTestCasesSubscribe(Consumer<List<TestCase>> listener) {
		extractTestCasesListeners.add(listener);
	}

	public void selectTestCasePublish(TestCase testCase) {
		selectTestCaseListeners.forEach(listener -> listener.accept(testCase));
	}

	public void selectTestCaseSubscribe(Consumer<TestCase> listener) {
		selectTestCaseListeners.add(listener);
	}

}
