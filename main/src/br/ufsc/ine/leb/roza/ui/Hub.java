package br.ufsc.ine.leb.roza.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.ui.model.DeckardSettingsConsumer;

public class Hub {

	private List<Consumer<List<TestClass>>> loadTestClassesListeners;
	private List<Consumer<TestClass>> selectTestClassListeners;
	private List<Consumer<List<TestCase>>> extractTestCasesListeners;
	private List<Consumer<TestCase>> selectTestCaseListeners;
	private List<Runnable> selectDeckardMetricListeners;
	private List<Runnable> unselectDeckardMetricListeners;
	private List<DeckardSettingsConsumer> changeDeckardSettingsListeners;
	private List<Runnable> selectJplagMetricListeners;
	private List<Runnable> unselectJplagMetricListeners;
	private List<Consumer<Integer>> changeJplagSettingsListeners;
	private List<Runnable> selectSimianMetricListeners;
	private List<Runnable> unselectSimianMetricListeners;
	private List<Consumer<Integer>> changeSimianSettingsListeners;
	private List<Consumer<SimilarityReport>> measureTestCaseListeners;
	private List<BiConsumer<TestCase, TestCase>> compareTestCaseListeners;

	public Hub() {
		loadTestClassesListeners = new LinkedList<>();
		selectTestClassListeners = new LinkedList<>();
		extractTestCasesListeners = new LinkedList<>();
		selectTestCaseListeners = new LinkedList<>();
		selectDeckardMetricListeners = new LinkedList<>();
		unselectDeckardMetricListeners = new LinkedList<>();
		changeDeckardSettingsListeners = new LinkedList<>();
		selectJplagMetricListeners = new LinkedList<>();
		unselectJplagMetricListeners = new LinkedList<>();
		changeJplagSettingsListeners = new LinkedList<>();
		selectSimianMetricListeners = new LinkedList<>();
		unselectSimianMetricListeners = new LinkedList<>();
		changeSimianSettingsListeners = new LinkedList<>();
		measureTestCaseListeners = new LinkedList<>();
		compareTestCaseListeners = new LinkedList<>();
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

	public void selectDeckardMetricPublish() {
		selectDeckardMetricListeners.forEach(listener -> listener.run());
	}

	public void selectDeckardMetricSubscribe(Runnable listener) {
		selectDeckardMetricListeners.add(listener);
	}

	public void unselectDeckardMetricPublish() {
		unselectDeckardMetricListeners.forEach(listener -> listener.run());
	}

	public void unselectDeckardMetricSubscribe(Runnable listener) {
		unselectDeckardMetricListeners.add(listener);
	}

	public void changeDeckardSettingsPublish(Integer minTokens, Integer stride, Double similarity) {
		changeDeckardSettingsListeners.forEach(listener -> listener.accept(minTokens, stride, similarity));
	}

	public void changeDeckardSettingsSubscribe(DeckardSettingsConsumer listener) {
		changeDeckardSettingsListeners.add(listener);
	}

	public void selectJplagMetricPublish() {
		selectJplagMetricListeners.forEach(listener -> listener.run());
	}

	public void selectJplagMetricSubscribe(Runnable listener) {
		selectJplagMetricListeners.add(listener);
	}

	public void unselectJplagMetricPublish() {
		unselectJplagMetricListeners.forEach(listener -> listener.run());
	}

	public void unselectJplagMetricSubscribe(Runnable listener) {
		unselectJplagMetricListeners.add(listener);
	}

	public void changeJplagSettingsPublish(Integer sensitivity) {
		changeJplagSettingsListeners.forEach(listener -> listener.accept(sensitivity));
	}

	public void changeJplagSettingsSubscribe(Consumer<Integer> listener) {
		changeJplagSettingsListeners.add(listener);
	}

	public void selectSimianMetricPublish() {
		selectSimianMetricListeners.forEach(listener -> listener.run());
	}

	public void selectSimianMetricSubscribe(Runnable listener) {
		selectSimianMetricListeners.add(listener);
	}

	public void unselectSimianMetricPublish() {
		unselectSimianMetricListeners.forEach(listener -> listener.run());
	}

	public void unselectSimianMetricSubscribe(Runnable listener) {
		unselectSimianMetricListeners.add(listener);
	}

	public void changeSimianSettingsPublish(Integer threshold) {
		changeSimianSettingsListeners.forEach(listener -> listener.accept(threshold));
	}

	public void changeSimianSettingsSubscribe(Consumer<Integer> listener) {
		changeSimianSettingsListeners.add(listener);
	}

	public void measureTestsPublish(SimilarityReport similarityReort) {
		measureTestCaseListeners.forEach(listener -> listener.accept(similarityReort));
	}

	public void measureTestsSubscribe(Consumer<SimilarityReport> listener) {
		measureTestCaseListeners.add(listener);
	}

	public void compareTestCasePublish(TestCase source, TestCase target) {
		compareTestCaseListeners.forEach(listener -> listener.accept(source, target));
	}

	public void compareTestCaseSubscribe(BiConsumer<TestCase, TestCase> listener) {
		compareTestCaseListeners.add(listener);
	}

}
