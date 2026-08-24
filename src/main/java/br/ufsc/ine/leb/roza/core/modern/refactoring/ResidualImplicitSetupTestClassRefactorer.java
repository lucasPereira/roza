package br.ufsc.ine.leb.roza.core.modern.refactoring;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class ResidualImplicitSetupTestClassRefactorer implements TestClassRefactorer {

	private final ImplicitSetupTestClassRefactorer implicitSetup = new ImplicitSetupTestClassRefactorer();

	@Override
	public RefactoredTestClasses refactor(TestCaseClusters clusters) {
		List<TestClass> extracted = new ArrayList<>();
		Map<String, ResidualSource> residuals = new LinkedHashMap<>();
		int classIndex = 1;
		for (TestCaseCluster cluster : clusters.clusters()) {
			if (cluster.size() > 1) {
				extracted.add(implicitSetup.refactor(cluster.testCases(), "TestClass" + classIndex));
				classIndex++;
				continue;
			}
			TestCase leftover = cluster.testCases().get(0);
			TestClass source = leftover.sourceTestClass().orElseThrow(
					() -> new IllegalStateException("Leftover test " + leftover.name() + " must keep its source test class."));
			residuals.computeIfAbsent(source.qualifiedName(), key -> new ResidualSource(source)).add(leftover);
		}
		List<TestClass> testClasses = new ArrayList<>();
		for (ResidualSource residual : residuals.values()) {
			testClasses.add(residual.toTestClass());
		}
		testClasses.addAll(extracted);
		return new RefactoredTestClasses(testClasses);
	}

	private static final class ResidualSource {

		private final TestClass source;
		private final Set<String> leftoverNames = new LinkedHashSet<>();

		private ResidualSource(TestClass source) {
			this.source = source;
		}

		private void add(TestCase leftover) {
			leftoverNames.add(leftover.name());
		}

		private TestClass toTestClass() {
			List<TestMethod> methods = source.testMethods().stream()
					.filter(method -> leftoverNames.contains(method.name()))
					.collect(Collectors.toList());
			if (methods.size() != leftoverNames.size()) {
				throw new IllegalStateException(
						"Source class " + source.qualifiedName() + " is missing original methods for leftover tests " + leftoverNames);
			}
			return new TestClass(
					source.name(),
					source.packageName().orElse(null),
					source.imports(),
					source.setupAnnotation().orElse(null),
					source.fields(),
					source.fixtures(),
					source.helperMethods(),
					methods);
		}
	}
}
