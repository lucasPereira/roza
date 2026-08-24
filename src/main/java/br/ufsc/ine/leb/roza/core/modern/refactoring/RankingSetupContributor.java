package br.ufsc.ine.leb.roza.core.modern.refactoring;

import java.util.List;

import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;

public interface RankingSetupContributor {

	List<TestClass> sharedRankingClasses(List<TestCase> tests);

	List<TestClass> clusterRankingClasses(TestCaseCluster cluster);

	default boolean countsResidualSourceSetupWhileSingletonsRemain() {
		return false;
	}

	default TestClass residualSourceSetupClass(TestClass source) {
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
}
