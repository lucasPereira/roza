package br.ufsc.ine.leb.roza.core.modern.refactoring;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;

public final class OriginalHelperClassExtractor {

	private OriginalHelperClassExtractor() {
	}

	public static List<TestClass> helperClasses(TestCaseClusters clusters) {
		Map<String, TestClass> sources = new LinkedHashMap<>();
		for (TestCaseCluster cluster : clusters.clusters()) {
			for (TestCase testCase : cluster.testCases()) {
				testCase.sourceTestClass().ifPresent(source -> {
					if (!source.helperMethods().isEmpty()) {
						sources.putIfAbsent(source.qualifiedName(), source);
					}
				});
			}
		}
		List<TestClass> helpers = new ArrayList<>();
		Set<String> usedNames = new LinkedHashSet<>();
		for (TestClass source : sources.values()) {
			helpers.add(new TestClass(
					uniqueName(source.name() + "Helpers", usedNames),
					null,
					source.imports(),
					null,
					List.of(),
					List.of(),
					source.helperMethods(),
					List.of()));
		}
		return List.copyOf(helpers);
	}

	private static String uniqueName(String base, Set<String> usedNames) {
		String name = base;
		int suffix = 2;
		while (!usedNames.add(name)) {
			name = base + suffix;
			suffix++;
		}
		return name;
	}
}
