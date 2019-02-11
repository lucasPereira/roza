package br.ufsc.ine.leb.roza.materializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.github.javaparser.printer.PrettyPrinterConfiguration.IndentType;

import br.ufsc.ine.leb.roza.TestCase;

public class OneTestCasePerClassTestCaseMaterializer implements TestCaseMaterializer {

	private String baseFolder;
	private Integer counter;

	public OneTestCasePerClassTestCaseMaterializer(String baseFolder) {
		this.baseFolder = baseFolder;
		counter = 1;
	}

	@Override
	public void materialize(List<TestCase> tests) {
		tests.forEach((testCase) -> {
			try {
				String className = createClassName(testCase.getName());
				String classFileName = createClassFileName(className);
				File testClassFile = new File(baseFolder, classFileName);
				testClassFile.createNewFile();
				FileWriter writer = new FileWriter(testClassFile);
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
				writer.write(javaUnit.toString(configuration));
				writer.close();
			} catch (IOException excecao) {
				new RuntimeException(excecao);
			}
		});
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
