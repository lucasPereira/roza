package br.ufsc.ine.leb.roza.parsing;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class TestClass {

	private ClassOrInterfaceDeclaration declaration;
	private List<SetupMethod> setup;
	private List<TestMethod> tests;
	private List<Field> fields;

	public TestClass(ClassOrInterfaceDeclaration declaration, List<Field> fields, List<SetupMethod> setups, List<TestMethod> tests) {
		this.declaration = declaration;
		this.fields = fields;
		this.setup = setups;
		this.tests = tests;
	}

	public String getName() {
		return declaration.getName().asString();
	}

	public List<Field> getFields() {
		return fields;
	}

	public List<SetupMethod> getSetupMethods() {
		return setup;
	}

	public List<TestMethod> getTestMethods() {
		return tests;
	}

}
