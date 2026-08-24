package br.ufsc.ine.leb.roza.core.modern.arrangement;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

public final class StatementDependencyAnalyzer {

	public Optional<Analysis> analyze(CodeStatement statement) {
		try {
			Statement parsedStatement = JavaParser.parseStatement(statement.originalText());
			DefUseVisitor visitor = new DefUseVisitor();
			parsedStatement.accept(visitor, null);
			return Optional.of(new Analysis(visitor.definitions, visitor.uses, visitor.declaredTypes));
		} catch (RuntimeException exception) {
			return Optional.empty();
		}
	}

	public static final class Analysis {

		private final Set<String> definitions;
		private final Set<String> uses;
		private final Map<String, String> declaredTypes;

		private Analysis(Set<String> definitions, Set<String> uses, Map<String, String> declaredTypes) {
			this.definitions = Set.copyOf(definitions);
			this.uses = Set.copyOf(uses);
			this.declaredTypes = Map.copyOf(declaredTypes);
		}

		public Set<String> definitions() {
			return definitions;
		}

		public Set<String> uses() {
			return uses;
		}

		public Map<String, String> declaredTypes() {
			return declaredTypes;
		}
	}

	private static final class DefUseVisitor extends VoidVisitorAdapter<Void> {

		private final Set<String> definitions = new LinkedHashSet<>();
		private final Set<String> uses = new LinkedHashSet<>();
		private final Map<String, String> declaredTypes = new LinkedHashMap<>();

		@Override
		public void visit(VariableDeclarationExpr declaration, Void argument) {
			declaration.getVariables().forEach(variable -> {
				definitions.add(variable.getNameAsString());
				declaredTypes.put(variable.getNameAsString(), variable.getType().asString());
				variable.getInitializer().ifPresent(initializer -> initializer.accept(this, argument));
			});
		}

		@Override
		public void visit(ExpressionStmt expressionStatement, Void argument) {
			Expression expression = expressionStatement.getExpression();
			if (expression instanceof AssignExpr) {
				visitAssignment((AssignExpr) expression, argument);
				return;
			}
			super.visit(expressionStatement, argument);
		}

		@Override
		public void visit(AssignExpr assignment, Void argument) {
			visitAssignment(assignment, argument);
		}

		@Override
		public void visit(UnaryExpr unary, Void argument) {
			if (isIncrementOrDecrement(unary.getOperator())) {
				defineAndUse(unary.getExpression(), argument);
				return;
			}
			super.visit(unary, argument);
		}

		@Override
		public void visit(NameExpr name, Void argument) {
			uses.add(name.getNameAsString());
		}

		@Override
		public void visit(FieldAccessExpr fieldAccess, Void argument) {
			uses.add(fieldName(fieldAccess));
			if (!fieldAccess.getScope().isThisExpr()) {
				fieldAccess.getScope().accept(this, argument);
			}
		}

		private void visitAssignment(AssignExpr assignment, Void argument) {
			define(assignment.getTarget(), argument);
			if (assignment.getOperator() != AssignExpr.Operator.ASSIGN) {
				use(assignment.getTarget(), argument);
			}
			assignment.getValue().accept(this, argument);
		}

		private void defineAndUse(Expression expression, Void argument) {
			define(expression, argument);
			use(expression, argument);
		}

		private void define(Expression expression, Void argument) {
			if (expression instanceof NameExpr) {
				definitions.add(((NameExpr) expression).getNameAsString());
				return;
			}
			if (expression instanceof FieldAccessExpr) {
				FieldAccessExpr fieldAccess = (FieldAccessExpr) expression;
				definitions.add(fieldName(fieldAccess));
				if (!fieldAccess.getScope().isThisExpr()) {
					fieldAccess.getScope().accept(this, argument);
				}
				return;
			}
			expression.accept(this, argument);
		}

		private void use(Expression expression, Void argument) {
			expression.accept(this, argument);
		}

		private boolean isIncrementOrDecrement(UnaryExpr.Operator operator) {
			return operator == UnaryExpr.Operator.PREFIX_INCREMENT
					|| operator == UnaryExpr.Operator.PREFIX_DECREMENT
					|| operator == UnaryExpr.Operator.POSTFIX_INCREMENT
					|| operator == UnaryExpr.Operator.POSTFIX_DECREMENT;
		}

		private String fieldName(FieldAccessExpr fieldAccess) {
			return fieldAccess.getScope().isThisExpr()
					? fieldAccess.getNameAsString()
					: fieldAccess.toString();
		}
	}
}
