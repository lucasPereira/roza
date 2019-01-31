package br.ufsc.ine.leb.roza.extractor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;

public class Junit4TestCaseExtractor implements TestCaseExtractor {

	@Override
	public List<TestCase> extract(List<TestClass> testClasses) {
		List<TestCase> testCases = new LinkedList<>();
		testClasses.forEach((testClass) -> {
			testClass.getTestMethods().forEach((testMethod) -> {
				extractTestCase(testCases, testClass, testMethod);
			});
		});
		return testCases;
	}

	private void extractTestCase(List<TestCase> testCases, TestClass testClass, TestMethod testMethod) {
		String name = testMethod.getName();
		List<Statement> fixtures = new LinkedList<>();
		List<Statement> assertions = new LinkedList<>();
		List<Statement> statements = extractStatements(testClass, testMethod);
		statements.forEach((statement) -> {
			List<Statement> bucket = statementIsAssertion(statement) ? assertions : fixtures;
			bucket.add(statement);
		});
		TestCase testCase = new TestCase(name, fixtures, assertions);
		testCases.add(testCase);
	}

	private List<Statement> extractStatements(TestClass testClass, TestMethod testMethod) {
		List<Statement> statements = new LinkedList<Statement>();
		testClass.getSetupMethods().forEach((setupMethod) -> {
			statements.addAll(setupMethod.getStatements());
		});
		statements.addAll(testMethod.getStatements());
		return statements;
	}

	private Boolean statementIsAssertion(Statement statement) {
		com.github.javaparser.ast.stmt.Statement parsedStatement = JavaParser.parseStatement(statement.getText());
		if (parsedStatement.isExpressionStmt()) {
			ExpressionStmt parsedExpression = parsedStatement.asExpressionStmt();
			Expression expression = parsedExpression.getExpression();
			if (expression.isMethodCallExpr()) {
				MethodCallExpr methodCallExpression = expression.asMethodCallExpr();
				SimpleName name = methodCallExpression.getName();
				List<String> assertions = Arrays.asList("assertArrayEquals", "assertEquals", "assertFalse", "assertNotNull", "assertNotSame", "assertNull", "assertSame", "assertThat", "assertTrue");
				return assertions.contains(name.asString());
			}
		}
		return false;
	}

}
