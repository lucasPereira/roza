package br.ufsc.ine.leb.roza.core.modern.refactoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.HelperMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.SetupAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;
import br.ufsc.ine.leb.roza.core.modern.refactoring.SetupExtractionSupport.SetupExtraction;

public final class ImplicitSetupTestClassRefactorer implements TestClassRefactorer {

	@Override
	public RefactoredTestClasses refactor(TestCaseClusters clusters) {
		List<TestClass> testClasses = new ArrayList<>();
		int classIndex = 1;
		for (TestCaseCluster cluster : clusters.clusters()) {
			testClasses.add(refactor(cluster.testCases(), "TestClass" + classIndex));
			classIndex++;
		}
		return new RefactoredTestClasses(testClasses);
	}

	TestClass refactor(List<TestCase> testCases, String className) {
		int sharedPrefixSize = testCases.size() > 1 ? SetupExtractionSupport.commonNonAssertionPrefixSize(testCases) : 0;
		SetupExtraction extracted = sharedPrefixSize > 0
				? SetupExtractionSupport.extractSetup(testCases.get(0).body().statements().subList(0, sharedPrefixSize))
				: new SetupExtraction(List.of(), List.of());
		Optional<TestClass> source = homogeneousSource(testCases);
		if (source.isPresent() && usesOriginalMethodBodies(testCases, source.get())) {
			return refactorKeepingSourceSetup(testCases, className, source.get(), extracted, sharedPrefixSize);
		}
		boolean hasSetup = sharedPrefixSize > 0 && !extracted.statements().isEmpty();
		SetupAnnotation setupAnnotation = hasSetup ? SetupExtractionSupport.setupAnnotation(testCases) : null;
		List<FixtureMethod> fixtures = !hasSetup
				? List.of()
				: List.of(new FixtureMethod(
						FixtureKind.BEFORE,
						"setup",
						List.of(setupAnnotation.annotation()),
						SetupExtractionSupport.setupThrownExceptions(testCases),
						new CodeBlock(extracted.statements())));
		List<TestMethod> testMethods = testCases.size() > 1
				? SetupExtractionSupport.testMethods(testCases, sharedPrefixSize)
				: List.of(singleTestMethod(testCases.get(0)));
		return new TestClass(
				className,
				null,
				SetupExtractionSupport.imports(testCases, hasSetup ? Optional.of(setupAnnotation) : Optional.empty()),
				setupAnnotation,
				hasSetup ? extracted.fields() : List.of(),
				fixtures,
				List.<HelperMethod>of(),
				testMethods);
	}

	private TestClass refactorKeepingSourceSetup(
			List<TestCase> testCases,
			String className,
			TestClass source,
			SetupExtraction extracted,
			int sharedPrefixSize) {
		List<Field> fields = new ArrayList<>(source.fields());
		fields.addAll(extracted.fields());
		List<CodeStatement> setupStatements = new ArrayList<>();
		source.fixtures().stream()
				.filter(fixture -> fixture.kind() == FixtureKind.BEFORE)
				.map(fixture -> fixture.body().statements())
				.forEach(setupStatements::addAll);
		setupStatements.addAll(extracted.statements());
		boolean hasSetup = !setupStatements.isEmpty();
		SetupAnnotation setupAnnotation = !hasSetup
				? null
				: source.setupAnnotation().orElseGet(() -> SetupExtractionSupport.setupAnnotation(testCases));
		List<FixtureMethod> fixtures = !hasSetup
				? List.of()
				: List.of(new FixtureMethod(
						FixtureKind.BEFORE,
						"setup",
						List.of(setupAnnotation.annotation()),
						SetupExtractionSupport.setupThrownExceptions(testCases),
						new CodeBlock(setupStatements)));
		List<TestMethod> testMethods = testCases.size() > 1
				? SetupExtractionSupport.testMethods(testCases, sharedPrefixSize)
				: List.of(singleTestMethod(testCases.get(0)));
		return new TestClass(
				className,
				null,
				SetupExtractionSupport.imports(testCases, hasSetup ? Optional.of(setupAnnotation) : Optional.empty()),
				setupAnnotation,
				fields,
				fixtures,
				source.helperMethods(),
				testMethods);
	}

	private Optional<TestClass> homogeneousSource(List<TestCase> testCases) {
		Optional<TestClass> source = testCases.get(0).sourceTestClass();
		if (source.isEmpty()) {
			return Optional.empty();
		}
		String qualifiedName = source.get().qualifiedName();
		for (TestCase testCase : testCases) {
			if (testCase.sourceTestClass().isEmpty() || !qualifiedName.equals(testCase.sourceTestClass().get().qualifiedName())) {
				return Optional.empty();
			}
		}
		return source;
	}

	private boolean usesOriginalMethodBodies(List<TestCase> testCases, TestClass source) {
		for (TestCase testCase : testCases) {
			Optional<TestMethod> method = source.testMethods().stream()
					.filter(testMethod -> testMethod.name().equals(testCase.name()))
					.findFirst();
			if (method.isEmpty() || !sameStatements(method.get().body(), testCase.body())) {
				return false;
			}
		}
		return true;
	}

	private boolean sameStatements(CodeBlock left, CodeBlock right) {
		if (left.statements().size() != right.statements().size()) {
			return false;
		}
		for (int index = 0; index < left.statements().size(); index++) {
			if (!left.statements().get(index).normalizedText().equals(right.statements().get(index).normalizedText())) {
				return false;
			}
		}
		return true;
	}

	private TestMethod singleTestMethod(TestCase testCase) {
		return new TestMethod(
				testCase.name(),
				testCase.annotations(),
				testCase.thrownExceptions(),
				testCase.body());
	}
}
