package br.ufsc.ine.leb.roza.parsing;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.LocalRecordDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.UnparsableStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.YieldStmt;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

import br.ufsc.ine.leb.roza.loading.TextFile;

public final class JavaTestClassParser implements TestClassParser {

	private Class<? extends Annotation> beforeAnnotation;
	private Class<? extends Annotation> testAnnotation;

	public JavaTestClassParser(Class<? extends Annotation> beforeAnnotation, Class<? extends Annotation> testAnnotation) {
		this.beforeAnnotation = beforeAnnotation;
		this.testAnnotation = testAnnotation;
	}

	@Override
	public List<TestClass> parse(List<TextFile> files) {
		TextFile file = files.get(0);
		List<TestClass> classes = new LinkedList<>();
		StaticJavaParser.getConfiguration().setAttributeComments(false);
		StaticJavaParser.getConfiguration().setLexicalPreservationEnabled(true);

		CompilationUnit compilationUnit = StaticJavaParser.parse(file.getContent());
		compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classOrInterfaceDeclaration -> {
			List<Field> fields = parseFields(classOrInterfaceDeclaration);
			List<SetupMethod> setups = parseSetups(classOrInterfaceDeclaration);
			List<TestMethod> tests = parseTests(classOrInterfaceDeclaration);
			if (!tests.isEmpty()) {
				String name = classOrInterfaceDeclaration.getNameAsString();
				TestClass testClass = new TestClass(name, fields, setups, tests);
				classes.add(testClass);
			}
		});
		return classes;
	}

	private List<Field> parseFields(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
		List<Field> fields = new LinkedList<>();
		classOrInterfaceDeclaration.findAll(FieldDeclaration.class).forEach(fieldDeclaration -> {
			fieldDeclaration.getVariables().forEach(variable -> {
				Type type = variable.getType();
				SimpleName name = variable.getName();
				Field field = new Field(type.asString(), name.asString());
				fields.add(field);
			});
		});
		return fields;
	}

	private List<SetupMethod> parseSetups(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
		List<SetupMethod> setups = new LinkedList<>();
		classOrInterfaceDeclaration.findAll(MethodDeclaration.class).forEach(declaration -> {
			if (declaration.getAnnotationByClass(beforeAnnotation).isPresent()) {
				List<RozaStatement> statements = parseStatements(declaration);
				String name = declaration.getNameAsString();
				SetupMethod setup = new SetupMethod(name, statements);
				setups.add(setup);
			}
		});
		return setups;
	}

	private List<TestMethod> parseTests(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
		List<TestMethod> tests = new LinkedList<>();
		classOrInterfaceDeclaration.findAll(MethodDeclaration.class).forEach(declaration -> {
			if (declaration.getAnnotationByClass(testAnnotation).isPresent()) {
				List<RozaStatement> statements = parseStatements(declaration);
				String name = declaration.getNameAsString();
				TestMethod test = new TestMethod(name, statements);
				tests.add(test);
			}
		});
		return tests;
	}

	private List<RozaStatement> parseStatements(MethodDeclaration declaration) {
		List<RozaStatement> statements = new LinkedList<>();
		declaration.getBody().get().getStatements().forEach(parsedStatement -> {
			if (parsedStatement.isExpressionStmt()) {
				Expression expression = parsedStatement.asExpressionStmt().getExpression();
				if (expression.isMethodCallExpr()) {
					MethodCallExpr methodCallExpression = expression.asMethodCallExpr();
					String statementCode = LexicalPreservingPrinter.print(parsedStatement);
					String methodName = methodCallExpression.getName().asString();
					RozaStatement statement = new MethodCallStatement(statementCode, methodName);
					statements.add(statement);
				} else if (expression.isAssignExpr()) {
					AssignExpr assignExpression = expression.asAssignExpr();
					String statementCode = LexicalPreservingPrinter.print(parsedStatement);
					if (assignExpression.getTarget().isNameExpr()) {
						NameExpr nameExpression = assignExpression.getTarget().asNameExpr();
						String variable = nameExpression.getNameAsString();
						String assignment = LexicalPreservingPrinter.print(assignExpression.getValue());
						RozaStatement statement = new AssignStatement(statementCode, variable, assignment);
						statements.add(statement);
					}
				} else if (expression.isVariableDeclarationExpr()) {
					VariableDeclarationExpr variableDeclarationExpression = expression.asVariableDeclarationExpr();
					Type type = variableDeclarationExpression.getCommonType();
					variableDeclarationExpression.getVariables().forEach(variableDeclarator -> {
						SimpleName variable = variableDeclarator.getName();
						if (variableDeclarator.getInitializer().isPresent()) {
							Expression initialization = variableDeclarator.getInitializer().get();
							VariableDeclarator newVariableDeclarator = new VariableDeclarator(type, variable, initialization);
							VariableDeclarationExpr newVariableDeclarationExpression = new VariableDeclarationExpr(newVariableDeclarator);
							ExpressionStmt newExpressionStatement = new ExpressionStmt(newVariableDeclarationExpression);
							String statementCode = LexicalPreservingPrinter.print(newExpressionStatement);
							RozaStatement statement = new VariableDeclarationStatement(statementCode, variable.asString());
							statements.add(statement);
						} else {
							VariableDeclarator newVariableDeclarator = new VariableDeclarator(type, variable);
							VariableDeclarationExpr newVariableDeclarationExpression = new VariableDeclarationExpr(newVariableDeclarator);
							ExpressionStmt newExpressionStatement = new ExpressionStmt(newVariableDeclarationExpression);
							String statementCode = LexicalPreservingPrinter.print(newExpressionStatement);
							RozaStatement statement = new VariableDeclarationStatement(statementCode, variable.asString());
							statements.add(statement);
						}
					});
				}
			}
		});
		return statements;
	}

	private class RozaVisitor extends VoidVisitorAdapter<List<RozaStatement>> {

		private Integer level;

		public RozaVisitor() {
			level = 0;
		}

		private void start(com.github.javaparser.ast.stmt.Statement parserdStatement, List<RozaStatement> statements) {
			level++;
		}

		private void end(com.github.javaparser.ast.stmt.Statement parserdStatement, List<RozaStatement> statements) {
			if (level == 2) {
				// if (parserdStatement.isExpressionStmt()) {
				// ExpressionStmt expression = parserdStatement.asExpressionStmt();
				// if (expression.asE
				// }
				parserdStatement.asExpressionStmt().getExpression().isMethodCallExpr();
				RozaStatement statement = new GenericStatement(LexicalPreservingPrinter.print(parserdStatement));
				statements.add(statement);
			}
			level--;
		}

		@Override
		public void visit(AssertStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(BlockStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(BreakStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(ContinueStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(DoStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(EmptyStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(ExplicitConstructorInvocationStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(ForEachStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(ForStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(IfStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(LabeledStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(LocalClassDeclarationStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(LocalRecordDeclarationStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(ReturnStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(SwitchStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(SynchronizedStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(ThrowStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(TryStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(UnparsableStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(WhileStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(YieldStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(ExpressionStmt statement, List<RozaStatement> statements) {
			start(statement, statements);
			super.visit(statement, statements);
			end(statement, statements);
		}

		@Override
		public void visit(NameExpr expression, List<RozaStatement> statements) {
			super.visit(expression, statements);
		}

		@Override
		public void visit(AssignExpr expression, List<RozaStatement> statements) {
			super.visit(expression, statements);
		}

		@Override
		public void visit(VariableDeclarationExpr expression, List<RozaStatement> statements) {
			super.visit(expression, statements);
		}

		@Override
		public void visit(MethodCallExpr expression, List<RozaStatement> statements) {
			super.visit(expression, statements);
		}

		@Override
		public void visit(MethodReferenceExpr expression, List<RozaStatement> statements) {
			super.visit(expression, statements);
		}

	}

}
