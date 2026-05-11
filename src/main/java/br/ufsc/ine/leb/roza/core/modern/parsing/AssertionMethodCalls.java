package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.Optional;
import java.util.Set;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;

/**
 * Recognizes JUnit 4 / Jupiter / Hamcrest assertion calls, including qualified forms such as
 * {@code Assert.assertTrue(...)}, {@code Assertions.assertEquals(...)}, and
 * {@code MatcherAssert.assertThat(...)}.
 */
final class AssertionMethodCalls {

	private static final Set<String> QUALIFIED_ASSERTION_TYPES = Set.of(
			"org.junit.Assert",
			"junit.framework.Assert",
			"org.junit.jupiter.api.Assertions",
			"org.hamcrest.MatcherAssert");

	private static final Set<String> SIMPLE_ASSERTION_RECEIVERS = Set.of(
			"Assert",
			"Assertions",
			"MatcherAssert");

	private AssertionMethodCalls() {
	}

	static boolean isAssertionMethod(MethodCallExpr call) {
		String name = call.getNameAsString();
		if (!JunitAssertionMethods.contains(name)) {
			return false;
		}
		Optional<Expression> scope = call.getScope();
		if (scope.isEmpty()) {
			return true;
		}
		return isAssertionReceiver(scope.get());
	}

	static boolean isAssertionReceiver(Expression scopeExpr) {
		String full = qualifierString(scopeExpr);
		if (full.isEmpty()) {
			return false;
		}
		if (QUALIFIED_ASSERTION_TYPES.contains(full)) {
			return true;
		}
		return SIMPLE_ASSERTION_RECEIVERS.contains(full);
	}

	private static String qualifierString(Expression expression) {
		if (expression instanceof NameExpr) {
			return ((NameExpr) expression).getNameAsString();
		}
		if (expression instanceof FieldAccessExpr) {
			FieldAccessExpr access = (FieldAccessExpr) expression;
			String prefix = qualifierString(access.getScope());
			if (prefix.isEmpty()) {
				return access.getNameAsString();
			}
			return prefix + "." + access.getNameAsString();
		}
		return "";
	}
}
