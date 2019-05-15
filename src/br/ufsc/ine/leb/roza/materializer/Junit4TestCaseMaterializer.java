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

public abstract class Junit4TestCaseMaterializer implements TestCaseMaterializer {

	private Integer counter;
	private FolderUtils foldereUtils;

	public Junit4TestCaseMaterializer(String baseFolder) {
		counter = 1;
		foldereUtils = new FolderUtils(baseFolder);
	}

	@Override
	public final MaterializationReport materialize(List<TestCase> tests) {
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
			addAssertions(testCase, javaMethodBody);
			javaMethod.setBody(javaMethodBody);
			PrettyPrinterConfiguration configuration = new PrettyPrinterConfiguration();
			configuration.setIndentType(IndentType.TABS);
			configuration.setIndentSize(1);
			String code = javaUnit.toString(configuration);
			Integer length = code.split(configuration.getEndOfLineCharacter()).length;
			File file = foldereUtils.writeContetAsString(classFileName, code);
			TestCaseMaterialization materialization = new TestCaseMaterialization(file, length, testCase);
			materializations.add(materialization);
		});
		return new MaterializationReport(foldereUtils.getBaseFolder(), materializations);
	}

	protected abstract void addAssertions(TestCase testCase, BlockStmt javaMethodBody);

	private String createClassFileName(String className) {
		return String.format("%s.java", className);
	}

	private String createClassName(String testName) {
		Character firstLetter = testName.charAt(0);
		String otherLetters = testName.substring(1);
		return String.format("TestClass%d%s%sTest", counter++, firstLetter.toString().toUpperCase(), otherLetters);
	}

}
