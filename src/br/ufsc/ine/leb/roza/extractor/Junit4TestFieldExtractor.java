package br.ufsc.ine.leb.roza.extractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;

import br.ufsc.ine.leb.roza.Field;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestField;
import br.ufsc.ine.leb.roza.TestMethod;

public class Junit4TestFieldExtractor implements TestExtractor<TestField> {

	@Override
	public List<TestField> extract(List<TestClass> testClasses) {
		List<TestField> testFields = new LinkedList<>();
		testClasses.forEach((testClass) -> {
			extractTestField(testFields, testClass, testClass.getTestMethods());
		});
		return testFields;
	}

	private void extractTestField(List<TestField> testFields, TestClass testClass, List<TestMethod> testMethods) {
		List<Statement> fixtures = new LinkedList<>();
		List<Statement> assertions = new LinkedList<>();
		testMethods.forEach((testMethod) -> {
			List<Statement> statements = extractStatements(testClass, testMethod);
			statements.forEach((statement) -> {
				List<Statement> bucket = statementIsAssertion(statement) ? assertions : fixtures;
				bucket.add(statement);
			});
		});
		Set<Statement> singles = new HashSet<>(fixtures);
		testClass.getFields().forEach((field) -> {
			singles.add(new Statement(field.getText()));
		});
		List<Statement> sorted = new ArrayList<Statement>(singles);
		Collections.sort(sorted, new Comparator<Statement>() {
			@Override
			public int compare(Statement statement1, Statement statement2) {
				return statement1.getText().compareTo(statement2.getText());
			}
		});
		TestField testField = new TestField(testClass, sorted);
		testFields.add(testField);
	}

	private List<Statement> extractStatements(TestClass testClass, TestMethod testMethod) {
		List<Statement> statements = new LinkedList<Statement>();
		testClass.getSetupMethods().forEach((setupMethod) -> {
			setupMethod.getStatements().forEach((statement) -> {
				Statement addedStatement = statement;
				Optional<AssignExpr> assign = JavaParser.parseStatement(statement.getText()).toExpressionStmt().get()
						.getExpression().toAssignExpr();
				if (assign.isPresent()) {
					AssignExpr assignExpression = assign.get();
					for (Field field : testClass.getFields()) {
						if (assignExpression.getTarget().toString().equals(field.getName())) {
							addedStatement = new Statement(
									String.format("%s %s", field.getType(), statement.getText()));
						}
					}
				}
				statements.add(addedStatement);
			});
		});
		statements.addAll(testMethod.getStatements());
		return statements;
	}

	private Boolean statementIsAssertion(Statement statement) {
		Optional<MethodCallExpr> methodCall = JavaParser.parseStatement(statement.getText()).toExpressionStmt().get()
				.getExpression().toMethodCallExpr();
		if (methodCall.isPresent()) {
			MethodCallExpr methodCallExpression = methodCall.get();
			SimpleName name = methodCallExpression.getName();
			List<String> assertions = Arrays.asList("assertArrayEquals", "assertEquals", "assertFalse", "assertNotNull",
					"assertNotSame", "assertNull", "assertSame", "assertThat", "assertTrue");
			return assertions.contains(name.asString());
		}
		return false;
	}
}
