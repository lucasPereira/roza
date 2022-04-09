package br.ufsc.ine.leb.roza.fixtures;

import java.util.Arrays;

import br.ufsc.ine.leb.roza.parsing.Field;
import br.ufsc.ine.leb.roza.parsing.GenericStatement;
import br.ufsc.ine.leb.roza.parsing.MethodCallStatement;
import br.ufsc.ine.leb.roza.parsing.SetupMethod;
import br.ufsc.ine.leb.roza.parsing.RozaStatement;
import br.ufsc.ine.leb.roza.parsing.TestClass;
import br.ufsc.ine.leb.roza.parsing.TestMethod;

public class ParsingFixtures {

	public static RozaStatement buildFixtureStatement() {
		return new GenericStatement("sut(0);");
	}

	public static RozaStatement buildAssertStatement() {
		return new GenericStatement("assertEquals(0, 0);");
	}

	public static RozaStatement buildDirectMethodCallStatement() {
		return new MethodCallStatement("operation();", "operation");
	}

	public static RozaStatement buildDirectMethodCallWithParameterStatement() {
		return new MethodCallStatement("operation(0);", "operation");
	}

	public static RozaStatement buildVariableMethodCallStatement() {
		return new MethodCallStatement("object.operation();", "operation");
	}

	public static RozaStatement buildAttributeMethodCallStatement() {
		return new MethodCallStatement("object.attribute.operation();", "operation");
	}

	public static RozaStatement buildClassMethodCallStatement() {
		return new MethodCallStatement("SomeClass.operation();", "operation");
	}

	public static RozaStatement buildNewObjectMethodCallStatement() {
		return new MethodCallStatement("new SomeClass().operation();", "operation");
	}

	public static Field buildGenericField() {
		return new Field("Sut", "sut");
	}

	public static SetupMethod buildEmptySetupMethod() {
		return new SetupMethod("test", Arrays.asList());
	}

	public static TestMethod buildEmptyTestMethod() {
		return new TestMethod("test", Arrays.asList());
	}

	public static TestMethod buildTestMethodWithOneFixture() {
		RozaStatement statement = buildFixtureStatement();
		return new TestMethod("test", Arrays.asList(statement));
	}

	public static TestMethod buildTestMethodWithOneAssertion() {
		RozaStatement statement = buildAssertStatement();
		return new TestMethod("test", Arrays.asList(statement));
	}

	public static TestClass buildTestClassWithOneEmptyTest() {
		TestMethod test = buildEmptyTestMethod();
		return new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(test));
	}

	public static TestClass buildTestClassWithOneTestWithOneFixture() {
		TestMethod test = buildTestMethodWithOneFixture();
		return new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(test));
	}

	public static TestClass buildTestClassWithOneTestWithOneAssertion() {
		TestMethod test = buildTestMethodWithOneAssertion();
		return new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(test));
	}

}
