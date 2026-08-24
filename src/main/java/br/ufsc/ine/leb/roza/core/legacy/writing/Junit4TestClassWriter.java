package br.ufsc.ine.leb.roza.core.legacy.writing;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import com.github.javaparser.printer.configuration.DefaultConfigurationOption;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration.ConfigOption;
import com.github.javaparser.printer.configuration.Indentation;
import com.github.javaparser.printer.configuration.Indentation.IndentType;

import br.ufsc.ine.leb.roza.core.legacy.TestClass;
import br.ufsc.ine.leb.roza.core.legacy.utils.FolderUtils;

public class Junit4TestClassWriter implements TestClassWriter {

	private final FolderUtils folderUtils;

	public Junit4TestClassWriter(String baseFolder) {
		folderUtils = new FolderUtils(baseFolder);
	}

	@Override
	public void write(List<TestClass> classes) {
		DefaultPrinterConfiguration configuration = new DefaultPrinterConfiguration();
		configuration.addOption(new DefaultConfigurationOption(ConfigOption.INDENTATION, new Indentation(IndentType.TABS, 1)));
		configuration.addOption(new DefaultConfigurationOption(ConfigOption.ORDER_IMPORTS, true));
		DefaultPrettyPrinter printer = new DefaultPrettyPrinter(configuration);
		classes.forEach(testClass -> {
			CompilationUnit unit = new CompilationUnit();
			String className = testClass.getName();
			ClassOrInterfaceDeclaration unitTestClass = unit.addClass(className).setPublic(true);
			testClass.getFields().forEach(field -> {
				if (field.getInitialization() == null) {
					unitTestClass.addField (field.getType(), field.getName()).setPrivate(true);
				} else {
					Expression initialization = StaticJavaParser.parseExpression(field.getInitialization().getText());
					unitTestClass.addFieldWithInitializer(field.getType(), field.getName(), initialization).setPrivate(true);
				}
			});
			testClass.getSetupMethods().forEach(setupMethod -> {
				MethodDeclaration unitSetupMethod = unitTestClass.addMethod(setupMethod.getName()).setPublic(true).addAnnotation(Before.class);
				BlockStmt unitSetupMethodBody = new BlockStmt();
				setupMethod.getStatements().forEach((statement) -> unitSetupMethodBody.addStatement(StaticJavaParser.parseStatement(statement.getText())));
				unitSetupMethod.setBody(unitSetupMethodBody);
			});
			testClass.getTestMethods().forEach(testMethod -> {
				MethodDeclaration unitTestMethod = unitTestClass.addMethod(testMethod.getName()).setPublic(true).addAnnotation(Test.class);
				BlockStmt unitTestMethodBody = new BlockStmt();
				testMethod.getStatements().forEach((statement) -> unitTestMethodBody.addStatement(StaticJavaParser.parseStatement(statement.getText())));
				unitTestMethod.setBody(unitTestMethodBody);
			});
			String code = printer.print(unit);
			String fileName = String.format("%s.java", className);
			folderUtils.writeContetAsString(fileName, code);
		});
	}

}
