package br.ufsc.ine.leb.roza.uniformity;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.github.javaparser.printer.PrettyPrinterConfiguration.IndentType;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.materializer.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class ManyTestCasePerClassTestCaseMaterializer implements TestCaseMaterializer<TestClass> {

	private FolderUtils foldereUtils;

	public ManyTestCasePerClassTestCaseMaterializer(String baseFolder) {
		foldereUtils = new FolderUtils(baseFolder);
	}

	@Override
	public MaterializationReport<TestClass> materialize(List<TestClass> tests) {
		List<TestCaseMaterialization<TestClass>> materializations = new LinkedList<>();
		tests.forEach((testClass) -> {
			String className = testClass.getName();
			String classFileName = createClassFileName(className);
			CompilationUnit javaUnit = new CompilationUnit();
			ClassOrInterfaceDeclaration javaClass = javaUnit.addClass(className).setPublic(true);
			MethodDeclaration javaMethod = javaClass.addMethod(testClass.getName()).setPublic(true)
					.addAnnotation("Test");

			BlockStmt javaMethodBody = new BlockStmt();
			testClass.getSetupMethods().forEach((method) -> {
				method.getStatements().forEach((statement) -> {
					javaMethodBody.addStatement(JavaParser.parseStatement(statement.getText()));
				});
			});
			testClass.getTestMethods().forEach((method) -> {
				method.getStatements().forEach((statement) -> {
					javaMethodBody.addStatement(JavaParser.parseStatement(statement.getText()));
				});
			});
			javaMethod.setBody(javaMethodBody);
			PrettyPrinterConfiguration configuration = new PrettyPrinterConfiguration();
			configuration.setIndentType(IndentType.TABS);
			configuration.setIndentSize(1);
			File file = foldereUtils.writeContetAsString(classFileName, javaUnit.toString(configuration));
			TestCaseMaterialization<TestClass> materialization = new TestCaseMaterialization<>(file, testClass);
			materializations.add(materialization);
		});
		return new MaterializationReport<>(foldereUtils.getBaseFolder(), materializations);
	}

	private String createClassFileName(String className) {
		return String.format("%s.java", className);
	}

}
