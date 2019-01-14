package br.ufsc.ine.leb.roza;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class Junit4TestClassSelector implements TestClassSelector {

	@Override
	public List<TestClass> select(List<TextFile> files) {
		List<TestClass> testClasses = new LinkedList<>();
		files.forEach((file) -> {
			CompilationUnit compilationUnit = JavaParser.parse(file.getContent());
			Optional<ClassOrInterfaceDeclaration> parsedTestClass = compilationUnit.findFirst(ClassOrInterfaceDeclaration.class);
			String name = parsedTestClass.get().getNameAsString();
			List<TestMethod> testMethods = new LinkedList<>();
			List<MethodDeclaration> parsedMethods = parsedTestClass.get().findAll(MethodDeclaration.class);
			parsedMethods.forEach((parsedMethod) -> {
				Boolean hasTestAnnotation = parsedMethod.getAnnotationByClass(Test.class).isPresent();
				if (hasTestAnnotation) {
					TestMethod testMethod = new TestMethod(parsedMethod.getNameAsString());
					testMethods.add(testMethod);
				}
			});
			TestClass testClass = new TestClass(name, testMethods);
			testClasses.add(testClass);
		});
		return testClasses;
	}

}
