package br.ufsc.ine.leb.roza.extraction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;

import br.ufsc.ine.leb.roza.parsing.Field;
import br.ufsc.ine.leb.roza.parsing.RozaStatement;
import br.ufsc.ine.leb.roza.parsing.TestClass;
import br.ufsc.ine.leb.roza.parsing.TestMethod;

public class JunitTestCaseExtractor implements TestCaseExtractor {

	private List<String> assertions;

	public JunitTestCaseExtractor(List<String> assertions) {
		this.assertions = assertions;
	}

	@Override
	public final List<TestCase> extract(List<TestClass> testClasses) {
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
		List<RozaStatement> fixtures = new ArrayList<>();
		List<RozaStatement> assertions = new LinkedList<>();
		List<RozaStatement> statements = extractStatements(testClass, testMethod);
		statements.forEach((statement) -> {
			List<RozaStatement> bucket = statementIsAssertion(statement) ? assertions : fixtures;
			bucket.add(statement);
		});
		TestCase testCase = new TestCase(name, fixtures, assertions);
		testCases.add(testCase);
	}

	private List<RozaStatement> extractStatements(TestClass testClass, TestMethod testMethod) {
		List<RozaStatement> statements = new LinkedList<RozaStatement>();
		testClass.getSetupMethods().forEach((setupMethod) -> {
			setupMethod.getStatements().forEach((statement) -> {
				RozaStatement addedStatement = statement;
				Optional<AssignExpr> assign = JavaParser.parseStatement(statement.getText()).toExpressionStmt().get().getExpression().toAssignExpr();
				if (assign.isPresent()) {
					AssignExpr assignExpression = assign.get();
					for (Field field : testClass.getFields()) {
						if (assignExpression.getTarget().toString().equals(field.getName())) {
							addedStatement = new SimpleStatement(String.format("%s %s", field.getType(), statement.getText()));
						}
					}
				}
				statements.add(addedStatement);
			});
		});
		statements.addAll(testMethod.getStatements());
		return statements;
	}

	private Boolean statementIsAssertion(RozaStatement statement) {
		Optional<MethodCallExpr> methodCall = JavaParser.parseStatement(statement.getText()).toExpressionStmt().get().getExpression().toMethodCallExpr();
		if (methodCall.isPresent()) {
			MethodCallExpr methodCallExpression = methodCall.get();
			SimpleName name = methodCallExpression.getName();
			return assertions.contains(name.asString());
		}
		return false;
	}

}
