package br.ufsc.ine.leb.roza.core.modern.refactoring;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.HelperMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.SetupAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class ImplicitSetupTestClassRefactorer implements TestClassRefactorer {

	private final ImplicitSetupPackagePolicy packagePolicy;

	public ImplicitSetupTestClassRefactorer() {
		this(ImplicitSetupPackagePolicy.DEFAULT_PACKAGE);
	}

	public ImplicitSetupTestClassRefactorer(ImplicitSetupPackagePolicy packagePolicy) {
		this.packagePolicy = Objects.requireNonNull(packagePolicy);
	}

	@Override
	public RefactoredTestClasses refactor(TestCaseClusters clusters) {
		List<TestClass> testClasses = new ArrayList<>();
		int classIndex = 1;
		for (TestCaseCluster cluster : clusters.clusters()) {
			for (List<TestCase> partition : partitions(cluster)) {
				testClasses.add(refactor(partition, "TestClass" + classIndex, packageName(partition)));
				classIndex++;
			}
		}
		return new RefactoredTestClasses(testClasses);
	}

	private List<List<TestCase>> partitions(TestCaseCluster cluster) {
		if (packagePolicy == ImplicitSetupPackagePolicy.DEFAULT_PACKAGE) {
			return List.of(cluster.testCases());
		}
		Map<String, List<TestCase>> partitions = new LinkedHashMap<>();
		for (TestCase testCase : cluster.testCases()) {
			partitions.computeIfAbsent(testCase.sourceTestClass().orElseThrow().packageName().orElse(""), packageName -> new ArrayList<>()).add(testCase);
		}
		return new ArrayList<>(partitions.values());
	}

	private TestClass refactor(List<TestCase> testCases, String className, Optional<String> packageName) {
		int sharedPrefixSize = commonNonAssertionPrefixSize(testCases);
		SetupAnnotation setupAnnotation = setupAnnotation(testCases);
		SetupExtraction setup = extractSetup(testCases.get(0).body().statements().subList(0, sharedPrefixSize));
		boolean hasSetup = !setup.statements().isEmpty();
		List<FixtureMethod> fixtures = !hasSetup
				? List.of()
				: List.of(new FixtureMethod(FixtureKind.BEFORE, "setup", List.of(setupAnnotation.annotation()), new CodeBlock(setup.statements())));
		List<TestMethod> testMethods = testMethods(testCases, sharedPrefixSize);
		return new TestClass(className, packageName.orElse(null), imports(testCases, hasSetup ? Optional.of(setupAnnotation) : Optional.empty()), hasSetup ? setupAnnotation : null, setup.fields(), fixtures, List.<HelperMethod>of(), testMethods);
	}

	private int commonNonAssertionPrefixSize(List<TestCase> testCases) {
		if (testCases.isEmpty()) {
			return 0;
		}
		int size = testCases.get(0).body().statements().size();
		for (TestCase testCase : testCases) {
			size = Math.min(size, testCase.body().statements().size());
		}
		for (int index = 0; index < size; index++) {
			CodeStatement first = testCases.get(0).body().statements().get(index);
			if (first.isAssertion()) {
				return index;
			}
			for (int testCaseIndex = 1; testCaseIndex < testCases.size(); testCaseIndex++) {
				CodeStatement current = testCases.get(testCaseIndex).body().statements().get(index);
				if (current.isAssertion() || !first.normalizedText().equals(current.normalizedText())) {
					return index;
				}
			}
		}
		return size;
	}

	private SetupAnnotation setupAnnotation(List<TestCase> testCases) {
		return testCases.stream()
				.map(testCase -> testCase.sourceTestClass().orElseThrow().setupAnnotation().orElseThrow())
				.filter(SetupAnnotation::isBeforeEach)
				.findFirst()
				.orElseGet(() -> testCases.get(0).sourceTestClass().orElseThrow().setupAnnotation().orElseThrow());
	}

	private SetupExtraction extractSetup(List<CodeStatement> sharedPrefix) {
		List<Field> fields = new ArrayList<>();
		List<CodeStatement> statements = new ArrayList<>();
		for (CodeStatement statement : sharedPrefix) {
			Optional<VariableDeclarationExpr> declaration = variableDeclaration(statement);
			if (declaration.isPresent()) {
				for (int index = 0; index < declaration.get().getVariables().size(); index++) {
					VariableDeclarator variable = declaration.get().getVariable(index);
					fields.add(new Field(List.of("private"), variable.getType().asString(), variable.getNameAsString(), Optional.empty()));
					variable.getInitializer()
							.map(initializer -> variable.getNameAsString() + " = " + initializer + ";")
							.map(text -> new CodeStatement(text, text))
							.ifPresent(statements::add);
				}
			} else {
				statements.add(statement);
			}
		}
		return new SetupExtraction(fields, statements);
	}

	private Optional<VariableDeclarationExpr> variableDeclaration(CodeStatement statement) {
		try {
			ExpressionStmt expression = JavaParser.parseStatement(statement.normalizedText()).asExpressionStmt();
			return expression.getExpression().toVariableDeclarationExpr();
		} catch (RuntimeException exception) {
			return Optional.empty();
		}
	}

	private List<TestMethod> testMethods(List<TestCase> testCases, int sharedPrefixSize) {
		Set<String> usedNames = new LinkedHashSet<>();
		List<TestMethod> testMethods = new ArrayList<>();
		for (TestCase testCase : testCases) {
			testMethods.add(testMethod(testCase, sharedPrefixSize, uniqueMethodName(testCase, usedNames)));
		}
		return testMethods;
	}

	private String uniqueMethodName(TestCase testCase, Set<String> usedNames) {
		String name = testCase.name();
		if (!usedNames.add(name)) {
			name = prefixedMethodName(testCase);
			int suffix = 2;
			while (!usedNames.add(name)) {
				name = prefixedMethodName(testCase) + suffix;
				suffix++;
			}
		}
		return name;
	}

	private String prefixedMethodName(TestCase testCase) {
		String sourceClassName = testCase.sourceTestClass().orElseThrow().name();
		return decapitalize(sourceClassName) + capitalize(testCase.name());
	}

	private String decapitalize(String text) {
		return Character.toLowerCase(text.charAt(0)) + text.substring(1);
	}

	private String capitalize(String text) {
		return Character.toUpperCase(text.charAt(0)) + text.substring(1);
	}

	private TestMethod testMethod(TestCase testCase, int sharedPrefixSize, String methodName) {
		List<CodeStatement> statements = testCase.body().statements().subList(sharedPrefixSize, testCase.body().statements().size());
		return new TestMethod(methodName, testCase.annotations(), new CodeBlock(statements));
	}

	private Optional<String> packageName(List<TestCase> testCases) {
		if (packagePolicy == ImplicitSetupPackagePolicy.DEFAULT_PACKAGE) {
			return Optional.empty();
		}
		String packageName = testCases.get(0).sourceTestClass().orElseThrow().packageName().orElse("");
		return packageName.isEmpty() ? Optional.empty() : Optional.of(packageName);
	}

	private List<String> imports(List<TestCase> testCases, Optional<SetupAnnotation> setupAnnotation) {
		Set<String> imports = new LinkedHashSet<>();
		for (TestCase testCase : testCases) {
			imports.addAll(testCase.sourceTestClass().orElseThrow().imports());
		}
		setupAnnotation.flatMap(SetupAnnotation::importDeclaration).ifPresent(imports::add);
		return new ArrayList<>(imports);
	}

	private static final class SetupExtraction {

		private final List<Field> fields;
		private final List<CodeStatement> statements;

		private SetupExtraction(List<Field> fields, List<CodeStatement> statements) {
			this.fields = List.copyOf(fields);
			this.statements = List.copyOf(statements);
		}

		private List<Field> fields() {
			return fields;
		}

		private List<CodeStatement> statements() {
			return statements;
		}
	}
}
