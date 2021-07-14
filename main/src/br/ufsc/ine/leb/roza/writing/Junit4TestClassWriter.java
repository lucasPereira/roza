package br.ufsc.ine.leb.roza.writing;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.github.javaparser.printer.PrettyPrinterConfiguration.IndentType;

import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class JavaTestClassWriter implements TestClassWriter {

	private FolderUtils folderUtils;

	public JavaTestClassWriter(String baseFolder) {
		folderUtils = new FolderUtils(baseFolder);
	}

	@Override
	public void write(List<TestClass> classes) {
		PrettyPrinterConfiguration configuration = new PrettyPrinterConfiguration();
		configuration.setIndentType(IndentType.TABS);
		configuration.setIndentSize(1);
		configuration.setOrderImports(true);
		classes.forEach(testClass -> {
			CompilationUnit unit = new CompilationUnit();
			String className = testClass.getName();
			ClassOrInterfaceDeclaration unitTestClass = unit.addClass(className).setPublic(true);
			testClass.getFields().forEach(field -> {
				if (field.getInitialization() == null) {
					unitTestClass.addField (field.getType(), field.getName()).setPrivate(true);
				} else {
					Expression initialization = JavaParser.parseExpression(field.getInitialization().getText());
					unitTestClass.addFieldWithInitializer(field.getType(), field.getName(), initialization).setPrivate(true);
				}
			});
			testClass.getSetupMethods().forEach(setupMethod -> {
				MethodDeclaration unitSetupMethod = unitTestClass.addMethod(setupMethod.getName()).setPublic(true).addAnnotation(Before.class);
				BlockStmt unitSetupMethodBody = new BlockStmt();
				setupMethod.getStatements().forEach((statement) -> {
					unitSetupMethodBody.addStatement(JavaParser.parseStatement(statement.getText()));
				});
				unitSetupMethod.setBody(unitSetupMethodBody);
			});
			testClass.getTestMethods().forEach(testMethod -> {
				MethodDeclaration unitTestMethod = unitTestClass.addMethod(testMethod.getName()).setPublic(true).addAnnotation(Test.class);
				BlockStmt unitTestMethodBody = new BlockStmt();
				testMethod.getStatements().forEach((statement) -> {
					unitTestMethodBody.addStatement(JavaParser.parseStatement(statement.getText()));
				});
				unitTestMethod.setBody(unitTestMethodBody);
			});
			String code = unit.toString(configuration);
			String fileName = String.format("%s.java", className);
			folderUtils.writeContetAsString(fileName, code);
		});
	}

}
