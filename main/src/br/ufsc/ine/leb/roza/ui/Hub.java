package br.ufsc.ine.leb.roza.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.clustering.Level;
import br.ufsc.ine.leb.roza.ui.model.DeckardSettingsConsumer;

public class Hub {

	private final List<Consumer<List<TestClass>>> loadTestClassesListeners;
	private final List<Consumer<TestClass>> selectTestClassListeners;

	private final List<Consumer<List<TestCase>>> extractTestCasesListeners;
	private final List<Consumer<TestCase>> selectTestCaseListeners;

	private final List<Runnable> selectDeckardMetricListeners;
	private final List<Runnable> unselectDeckardMetricListeners;
	private final List<DeckardSettingsConsumer> changeDeckardSettingsListeners;
	private final List<Runnable> selectJplagMetricListeners;
	private final List<Runnable> unselectJplagMetricListeners;
	private final List<Consumer<Integer>> changeJplagSettingsListeners;
	private final List<Runnable> selectSimianMetricListeners;
	private final List<Runnable> unselectSimianMetricListeners;
	private final List<Consumer<Integer>> changeSimianSettingsListeners;
	private final List<Consumer<SimilarityReport>> measureTestCaseListeners;
	private final List<Consumer<SimilarityAssessment>> compareTestCaseListeners;

	private final List<Runnable> selectLevelBasedCriteriaListeners;
	private final List<Runnable> selectTestsPerClassCriteriaListeners;
	private final List<Runnable> selectSimilarityBasedCriteriaListeners;
	private final List<Runnable> selectNeverStopCriteriaListeners;

	private final List<Consumer<List<Level>>> distributeTestsListeners;
	private final List<Consumer<Level>> selectLevelListeners;

	private final List<Consumer<String>> infoMessageListeners;
	private final List<Consumer<String>> errorMessageListeners;

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

		selectLevelBasedCriteriaListeners = new LinkedList<>();
		selectTestsPerClassCriteriaListeners = new LinkedList<>();
		selectSimilarityBasedCriteriaListeners = new LinkedList<>();
		selectNeverStopCriteriaListeners = new LinkedList<>();

		distributeTestsListeners = new LinkedList<>();
		selectLevelListeners = new LinkedList<>();

		infoMessageListeners = new LinkedList<>();
		errorMessageListeners = new LinkedList<>();
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
		selectDeckardMetricListeners.forEach(Runnable::run);
	}

	public void selectDeckardMetricSubscribe(Runnable listener) {
		selectDeckardMetricListeners.add(listener);
	}

	public void unselectDeckardMetricPublish() {
		unselectDeckardMetricListeners.forEach(Runnable::run);
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
		selectJplagMetricListeners.forEach(Runnable::run);
	}

	public void selectJplagMetricSubscribe(Runnable listener) {
		selectJplagMetricListeners.add(listener);
	}

	public void unselectJplagMetricPublish() {
		unselectJplagMetricListeners.forEach(Runnable::run);
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
		selectSimianMetricListeners.forEach(Runnable::run);
	}

	public void selectSimianMetricSubscribe(Runnable listener) {
		selectSimianMetricListeners.add(listener);
	}

	public void unselectSimianMetricPublish() {
		unselectSimianMetricListeners.forEach(Runnable::run);
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

	public void compareTestCasePublish(SimilarityAssessment assessment) {
		compareTestCaseListeners.forEach(listener -> listener.accept(assessment));
	}

	public void compareTestCaseSubscribe(Consumer<SimilarityAssessment> listener) {
		compareTestCaseListeners.add(listener);
	}

	public void selectLevelBasedCriteriaPublish() {
		selectLevelBasedCriteriaListeners.forEach(Runnable::run);
	}

	public void selectLevelBasedCriteriaSubscribe(Runnable listener) {
		selectLevelBasedCriteriaListeners.add(listener);
	}

	public void selectTestsPerClassCriteriaPublish() {
		selectTestsPerClassCriteriaListeners.forEach(Runnable::run);
	}

	public void selectTestsPerClassCriteriaSubscribe(Runnable listener) {
		selectTestsPerClassCriteriaListeners.add(listener);
	}

	public void selectSimilarityBasedCriteriaPublish() {
		selectSimilarityBasedCriteriaListeners.forEach(Runnable::run);
	}

	public void selectSimilarityBasedCriteriaSubscribe(Runnable listener) {
		selectSimilarityBasedCriteriaListeners.add(listener);
	}

	public void selectNeverStopCriteriaPublish() {
		selectNeverStopCriteriaListeners.forEach(Runnable::run);
	}

	public void selectNeverStopCriteriaSubscribe(Runnable listener) {
		selectNeverStopCriteriaListeners.add(listener);
	}

	public void distributeTestsPublish(List<Level> levels) {
		distributeTestsListeners.forEach(listener -> listener.accept(levels));
	}

	public void distributeTestsSubscribe(Consumer<List<Level>> listener) {
		distributeTestsListeners.add(listener);
	}

	public void selectLevelPublish(Level level) {
		selectLevelListeners.forEach(listener -> listener.accept(level));
	}

	public void selectLevelSubscribe(Consumer<Level> listener) {
		selectLevelListeners.add(listener);
	}

	public void infoMessagePublish(String message) {
		infoMessageListeners.forEach(listener -> listener.accept(message));
	}

	public void infoMessageSubscribe(Consumer<String> listener) {
		infoMessageListeners.add(listener);
	}

	public void errorMessagePublish(String message) {
		errorMessageListeners.forEach(listener -> listener.accept(message));
	}

	public void errorMessageSubscribe(Consumer<String> listener) {
		errorMessageListeners.add(listener);
	}

}
