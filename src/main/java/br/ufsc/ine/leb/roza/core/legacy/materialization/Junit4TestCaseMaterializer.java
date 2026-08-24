package br.ufsc.ine.leb.roza.core.legacy.materialization;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import com.github.javaparser.printer.configuration.DefaultConfigurationOption;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration.ConfigOption;
import com.github.javaparser.printer.configuration.Indentation;
import com.github.javaparser.printer.configuration.Indentation.IndentType;

import br.ufsc.ine.leb.roza.core.legacy.MaterializationReport;
import br.ufsc.ine.leb.roza.core.legacy.TestCase;
import br.ufsc.ine.leb.roza.core.legacy.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.core.legacy.utils.FolderUtils;

public abstract class Junit4TestCaseMaterializer implements TestCaseMaterializer {

	private Integer counter;
	private final FolderUtils folderUtils;

	public Junit4TestCaseMaterializer(String baseFolder) {
		counter = 1;
		folderUtils = new FolderUtils(baseFolder);
	}

	@Override
	public final MaterializationReport materialize(List<TestCase> tests) {
		List<TestCaseMaterialization> materializationList = new LinkedList<>();
		tests.forEach((testCase) -> {
			String className = createClassName(testCase.getName());
			String classFileName = createClassFileName(className);
			CompilationUnit javaUnit = new CompilationUnit();
			ClassOrInterfaceDeclaration javaClass = javaUnit.addClass(className).setPublic(true);
			MethodDeclaration javaMethod = javaClass.addMethod(testCase.getName()).setPublic(true).addAnnotation("Test");
			BlockStmt javaMethodBody = new BlockStmt();
			testCase.getFixtures().forEach((fixture) -> javaMethodBody.addStatement(StaticJavaParser.parseStatement(fixture.getText())));
			addAssertions(testCase, javaMethodBody);
			javaMethod.setBody(javaMethodBody);
			DefaultPrinterConfiguration configuration = new DefaultPrinterConfiguration();
			configuration.addOption(new DefaultConfigurationOption(ConfigOption.INDENTATION, new Indentation(IndentType.TABS, 1)));
			String code = new DefaultPrettyPrinter(configuration).print(javaUnit);
			Integer length = code.split("\n").length;
			File file = folderUtils.writeContetAsString(classFileName, code);
			TestCaseMaterialization materialization = new TestCaseMaterialization(file, length, testCase);
			materializationList.add(materialization);
		});
		return new MaterializationReport(folderUtils.getBaseFolder(), materializationList);
	}

	protected abstract void addAssertions(TestCase testCase, BlockStmt javaMethodBody);

	private String createClassFileName(String className) {
		return String.format("%s.java", className);
	}

	private String createClassName(String testName) {
		char firstLetter = testName.charAt(0);
		String otherLetters = testName.substring(1);
		return String.format("TestClass%d%s%sTest", counter++, Character.toString(firstLetter).toUpperCase(), otherLetters);
	}

}
