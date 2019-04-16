package br.ufsc.ine.leb.roza.materializer;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.github.javaparser.printer.PrettyPrinterConfiguration.IndentType;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestField;
import br.ufsc.ine.leb.roza.TestMaterialization;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class ManyTestCasePerClassTestCaseMaterializer implements TestCaseMaterializer<TestField> {

	private FolderUtils foldereUtils;
	private int counter;

	public ManyTestCasePerClassTestCaseMaterializer(String baseFolder) {
		foldereUtils = new FolderUtils(baseFolder);
		counter = 0;
	}

	@Override
	public MaterializationReport<TestField> materialize(List<TestField> tests) {
		List<TestMaterialization<TestField>> materializations = new LinkedList<>();
		tests.forEach((testField) -> {
			String className = testField.getTestClass().getName();
			String classFileName = createClassFileName(className);
			PrettyPrinterConfiguration configuration = new PrettyPrinterConfiguration();
			configuration.setIndentType(IndentType.TABS);
			configuration.setIndentSize(1);
			File file = foldereUtils.writeContetAsString(classFileName, asString(testField.getFields()));
			TestMaterialization<TestField> materialization = new TestMaterialization<>(file, testField);
			materializations.add(materialization);
		});
		return new MaterializationReport<>(foldereUtils.getBaseFolder(), materializations);
	}

	private String asString(List<Statement> fields) {
		List<String> strings = new LinkedList<String>();
		for (Statement statement : fields) {
			com.github.javaparser.ast.stmt.Statement aStatement = JavaParser.parseStatement(statement.getText());
			if (aStatement.isExpressionStmt()) {
				Expression expression = aStatement.asExpressionStmt().getExpression();
				if (expression.isVariableDeclarationExpr()) {
					VariableDeclarationExpr varExpression = (VariableDeclarationExpr) expression;
					for (VariableDeclarator var : varExpression.getVariables()) {
						strings.add(var.getNameAsString());
					}
				}
			}
		}
		return strings.toString();
	}

	private String createClassFileName(String className) {
		return String.format("%s_%s.field", className, counter++);
	}

}
