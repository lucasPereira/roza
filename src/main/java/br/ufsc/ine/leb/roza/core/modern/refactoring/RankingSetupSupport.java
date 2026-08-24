package br.ufsc.ine.leb.roza.core.modern.refactoring;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

final class RankingSetupSupport {

	private RankingSetupSupport() {
	}

	static List<TestClass> originalHelperClasses(List<TestCase> tests) {
		return OriginalHelperClassExtractor.helperClasses(singletonClusters(tests));
	}

	static TestClass originalArrangeClass(TestCase testCase) {
		TestClass source = testCase.sourceTestClass().orElseThrow(
				() -> new IllegalStateException("Ranking requires the original source class for " + testCase.name() + "."));
		TestMethod method = source.testMethods().stream()
				.filter(testMethod -> testMethod.name().equals(testCase.name()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException(
						"Ranking requires the original test method " + source.qualifiedName() + "#" + testCase.name() + "."));
		return new TestClass("Arrange", null, List.of(), null, List.of(), List.of(), List.of(), List.of(method));
	}

	static TestClass fieldsAndFixturesClass(TestClass source) {
		return new TestClass(
				source.name(),
				source.packageName().orElse(null),
				List.of(),
				source.setupAnnotation().orElse(null),
				source.fields(),
				source.fixtures(),
				List.of(),
				List.of());
	}

	private static TestCaseClusters singletonClusters(List<TestCase> tests) {
		List<TestCaseCluster> clusters = new ArrayList<>();
		for (int index = 0; index < tests.size(); index++) {
			clusters.add(new TestCaseCluster(index, tests.get(index)));
		}
		return new TestCaseClusters(clusters);
	}
}
