package br.ufsc.ine.leb.roza.core.modern.refactoring;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.ConfiguredJavaParser;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.SetupAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class SetupExtractionSupport {

	public static SetupExtraction extractSetup(List<CodeStatement> sharedStatements) {
		List<Field> fields = new ArrayList<>();
		List<CodeStatement> statements = new ArrayList<>();
		for (CodeStatement statement : sharedStatements) {
			Optional<VariableDeclarationExpr> declaration = variableDeclaration(statement);
			if (declaration.isPresent()) {
				for (int index = 0; index < declaration.get().getVariables().size(); index++) {
					VariableDeclarator variable = declaration.get().getVariable(index);
					fields.add(new Field(List.of("private"), variable.getType().asString(), variable.getNameAsString(), Optional.empty()));
					variable.getInitializer()
							.map(initializer -> setupAssignment(variable, initializer.toString()))
							.map(text -> new CodeStatement(text, text))
							.ifPresent(statements::add);
				}
			} else {
				statements.add(statement);
			}
		}
		return new SetupExtraction(fields, statements);
	}

	public static SetupAnnotation setupAnnotation(List<TestCase> testCases) {
		return testCases.stream()
				.map(testCase -> testCase.sourceTestClass().orElseThrow().setupAnnotation().orElseThrow())
				.filter(SetupAnnotation::isBeforeEach)
				.findFirst()
				.orElseGet(() -> testCases.get(0).sourceTestClass().orElseThrow().setupAnnotation().orElseThrow());
	}

	public static List<String> setupThrownExceptions(List<TestCase> testCases) {
		Set<String> thrownExceptions = new LinkedHashSet<>();
		testCases.stream().map(TestCase::thrownExceptions).forEach(thrownExceptions::addAll);
		return List.copyOf(thrownExceptions);
	}

	public static List<String> imports(List<TestCase> testCases, Optional<SetupAnnotation> setupAnnotation) {
		Set<String> imports = new LinkedHashSet<>();
		for (TestCase testCase : testCases) {
			imports.addAll(testCase.sourceTestClass().orElseThrow().imports());
		}
		setupAnnotation.flatMap(SetupAnnotation::importDeclaration).ifPresent(imports::add);
		return new ArrayList<>(imports);
	}

	public static List<TestMethod> testMethods(List<TestCase> testCases, int sharedPrefixSize) {
		Set<String> usedNames = new LinkedHashSet<>();
		List<TestMethod> testMethods = new ArrayList<>();
		for (TestCase testCase : testCases) {
			testMethods.add(testMethod(testCase, sharedPrefixSize, uniqueMethodName(testCase, usedNames)));
		}
		return testMethods;
	}

	public static int commonNonAssertionPrefixSize(List<TestCase> testCases) {
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

	private static TestMethod testMethod(TestCase testCase, int sharedPrefixSize, String methodName) {
		List<CodeStatement> statements = testCase.body().statements().subList(sharedPrefixSize, testCase.body().statements().size());
		return new TestMethod(methodName, testCase.annotations(), testCase.thrownExceptions(), new CodeBlock(statements));
	}

	private static String uniqueMethodName(TestCase testCase, Set<String> usedNames) {
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

	private static String prefixedMethodName(TestCase testCase) {
		String sourceClassName = testCase.sourceTestClass().orElseThrow().name();
		return decapitalize(sourceClassName) + capitalize(testCase.name());
	}

	private static String decapitalize(String text) {
		return Character.toLowerCase(text.charAt(0)) + text.substring(1);
	}

	private static String capitalize(String text) {
		return Character.toUpperCase(text.charAt(0)) + text.substring(1);
	}

	private static String setupAssignment(VariableDeclarator variable, String initializer) {
		if (variable.getType().isArrayType() && initializer.startsWith("{")) {
			return variable.getNameAsString() + " = new " + variable.getType().asString() + " " + initializer + ";";
		}
		return variable.getNameAsString() + " = " + initializer + ";";
	}

	private static Optional<VariableDeclarationExpr> variableDeclaration(CodeStatement statement) {
		try {
			ExpressionStmt expression = ConfiguredJavaParser.parseStatement(statement.normalizedText()).asExpressionStmt();
			return expression.getExpression().toVariableDeclarationExpr();
		} catch (RuntimeException exception) {
			return Optional.empty();
		}
	}

	public static final class SetupExtraction {

		private final List<Field> fields;
		private final List<CodeStatement> statements;

		public SetupExtraction(List<Field> fields, List<CodeStatement> statements) {
			this.fields = List.copyOf(fields);
			this.statements = List.copyOf(statements);
		}

		public List<Field> fields() {
			return fields;
		}

		public List<CodeStatement> statements() {
			return statements;
		}
	}
}
