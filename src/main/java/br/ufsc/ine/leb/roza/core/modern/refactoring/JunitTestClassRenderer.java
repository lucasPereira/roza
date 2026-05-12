package br.ufsc.ine.leb.roza.core.modern.refactoring;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class JunitTestClassRenderer {

	public String render(TestClass testClass) {
		List<String> lines = new ArrayList<>();
		lines.addAll(testClass.imports());
		if (!testClass.imports().isEmpty()) {
			lines.add("");
		}
		lines.add("public class " + testClass.name() + " {");
		addFields(lines, testClass.fields());
		addFixtures(lines, testClass.fixtures());
		addTestMethods(lines, testClass.testMethods());
		lines.add("}");
		return String.join("\n", lines);
	}

	private void addFields(List<String> lines, List<Field> fields) {
		if (fields.isEmpty()) {
			return;
		}
		for (Field field : fields) {
			lines.add("\t" + fieldDeclaration(field));
		}
		lines.add("");
	}

	private String fieldDeclaration(Field field) {
		String modifiers = field.modifiers().isEmpty() ? "" : String.join(" ", field.modifiers()) + " ";
		String initialization = field.initialization().map(value -> " = " + value.normalizedText()).orElse("");
		return modifiers + field.type() + " " + field.name() + initialization + ";";
	}

	private void addFixtures(List<String> lines, List<FixtureMethod> fixtures) {
		for (FixtureMethod fixture : fixtures) {
			addMethod(lines, fixture.annotations(), fixture.name(), fixture.body().statements());
		}
	}

	private void addTestMethods(List<String> lines, List<TestMethod> testMethods) {
		for (TestMethod testMethod : testMethods) {
			addMethod(lines, testMethod.annotations(), testMethod.name(), testMethod.body().statements());
		}
	}

	private void addMethod(List<String> lines, List<CodeAnnotation> annotations, String name, List<CodeStatement> statements) {
		for (CodeAnnotation annotation : annotations) {
			lines.add("\t" + annotation.text());
		}
		lines.add("\tpublic void " + name + "() {");
		lines.addAll(statements.stream().map(statement -> "\t\t" + statement.normalizedText()).collect(Collectors.toList()));
		lines.add("\t}");
		lines.add("");
	}
}
