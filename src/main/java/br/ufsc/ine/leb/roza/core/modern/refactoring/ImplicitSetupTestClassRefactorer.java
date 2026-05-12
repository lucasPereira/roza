package br.ufsc.ine.leb.roza.core.modern.refactoring;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

	@Override
	public RefactoredTestClasses refactor(TestCaseClusters clusters) {
		List<TestClass> testClasses = new ArrayList<>();
		for (int index = 0; index < clusters.clusters().size(); index++) {
			testClasses.add(refactor(clusters.clusters().get(index), "TestClass" + (index + 1)));
		}
		return new RefactoredTestClasses(testClasses);
	}

	private TestClass refactor(TestCaseCluster cluster, String className) {
		List<TestCase> testCases = cluster.testCases();
		int sharedPrefixSize = commonNonAssertionPrefixSize(testCases);
		SetupAnnotation setupAnnotation = setupAnnotation(testCases);
		SetupExtraction setup = extractSetup(testCases.get(0).body().statements().subList(0, sharedPrefixSize));
		boolean hasSetup = !setup.statements().isEmpty();
		List<FixtureMethod> fixtures = !hasSetup
				? List.of()
				: List.of(new FixtureMethod(FixtureKind.BEFORE, "setup", List.of(setupAnnotation.annotation()), new CodeBlock(setup.statements())));
		List<TestMethod> testMethods = testCases.stream()
				.map(testCase -> testMethod(testCase, sharedPrefixSize))
				.collect(Collectors.toList());
		return new TestClass(className, imports(testCases, hasSetup ? Optional.of(setupAnnotation) : Optional.empty()), hasSetup ? setupAnnotation : null, setup.fields(), fixtures, List.<HelperMethod>of(), testMethods);
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

	private TestMethod testMethod(TestCase testCase, int sharedPrefixSize) {
		List<CodeStatement> statements = testCase.body().statements().subList(sharedPrefixSize, testCase.body().statements().size());
		return new TestMethod(testCase.name(), testCase.annotations(), new CodeBlock(statements));
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
