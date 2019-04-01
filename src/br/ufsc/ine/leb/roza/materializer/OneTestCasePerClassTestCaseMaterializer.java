package br.ufsc.ine.leb.roza.materializer;

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
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class OneTestCasePerClassTestCaseMaterializer implements TestCaseMaterializer {

	private Integer counter;
	private FolderUtils foldereUtils;

	public OneTestCasePerClassTestCaseMaterializer(String baseFolder) {
		counter = 1;
		foldereUtils = new FolderUtils(baseFolder);
	}

	@Override
	public MaterializationReport materialize(List<TestCase> tests) {
		List<TestCaseMaterialization> materializations = new LinkedList<>();
		tests.forEach((testCase) -> {
			String className = createClassName(testCase.getName());
			String classFileName = createClassFileName(className);
			CompilationUnit javaUnit = new CompilationUnit();
			ClassOrInterfaceDeclaration javaClass = javaUnit.addClass(className).setPublic(true);
			MethodDeclaration javaMethod = javaClass.addMethod(testCase.getName()).setPublic(true).addAnnotation("Test");
			BlockStmt javaMethodBody = new BlockStmt();
			testCase.getFixtures().forEach((fixture) -> {
				javaMethodBody.addStatement(JavaParser.parseStatement(fixture.getText()));
			});
			testCase.getAsserts().forEach((assertion) -> {
				javaMethodBody.addStatement(JavaParser.parseStatement(assertion.getText()));
			});
			javaMethod.setBody(javaMethodBody);
			PrettyPrinterConfiguration configuration = new PrettyPrinterConfiguration();
			configuration.setIndentType(IndentType.TABS);
			configuration.setIndentSize(1);
			File file = foldereUtils.writeContetAsString(classFileName, javaUnit.toString(configuration));
			TestCaseMaterialization materialization = new TestCaseMaterialization(file, testCase);
			materializations.add(materialization);
		});
		return new MaterializationReport(foldereUtils.getBaseFolder(), materializations);
	}

	private String createClassFileName(String className) {
		return String.format("%s.java", className);
	}

	private String createClassName(String testName) {
		Character firstLetter = testName.charAt(0);
		String otherLetters = testName.substring(1);
		return String.format("TestClass%d%s%sTest", counter++, firstLetter.toString().toUpperCase(), otherLetters);
	}

}
