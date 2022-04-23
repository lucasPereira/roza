package br.ufsc.ine.leb.roza.fixture;

import java.util.Arrays;

import org.hamcrest.core.IsNull;

import br.ufsc.ine.leb.roza.parsing.MethodCallStatement;
import br.ufsc.ine.leb.roza.parsing.RozaStatement;
import br.ufsc.ine.leb.roza.parsing.TestClass;
import br.ufsc.ine.leb.roza.parsing.TestMethod;

public class ExtractionFixtures {

	public static RozaStatement sutCallWithZeroParameter() {
		return ParsingFixtures.methodCallWithOneIntegerParameter("sut", 0);
	}

	public static RozaStatement assertEqualsWithZeroAndZero() {
		return ParsingFixtures.methodCallWithTwoIntegerParameters("assertEquals", 0, 0);
	}

	public static RozaStatement assertNotEqualsWithZeroAndOne() {
		return ParsingFixtures.methodCallWithTwoIntegerParameters("assertEquals", 0, 1);
	}

	public static RozaStatement assertTrueWithTrue() {
		return ParsingFixtures.methodCallWithOneBooleanParameter("assertTrue", true);
	}

	public static RozaStatement assertFalseWithFalse() {
		return ParsingFixtures.methodCallWithOneBooleanParameter("assertFalse", false);
	}

	public static RozaStatement assertNullWithNull() {
		return ParsingFixtures.methodCallWithOneNullParameter("assertNull");
	}

	public static RozaStatement assertNotNullWithNull() {
		return ParsingFixtures.methodCallWithOneNullParameter("assertNotNull");
	}

	public static RozaStatement assertSameWithNullAndNull() {
		return ParsingFixtures.methodCallWithOneNullParameter("assertNotNull");
	}

	public static RozaStatement assertNotSameWithNullAndNull() {
		return ParsingFixtures.methodCallWithOneNullParameter("assertNotSame");
	}

	public static RozaStatement assertThatWithNullAndIsNullDotNullValue() {
		return ParsingFixtures.methodCallWithNullAndMethodCallWithScopeParameters("assertThat", IsNull.class, "nullValue");
	}

	public static RozaStatement assertArrayEqualsWithZeroAndZero() {
		return ParsingFixtures.methodCallWithTwoIntegerArraysWithOneElementEachParameters("assertArrayEquals", 0, 0);
	}

	public static TestClass emptyTestClass() {
		TestClass testClass = ParsingFixtures.testClass("ExampleTest");
		return testClass;
	}

	public static TestClass testClassWintOneEmptyTestMethod() {
		TestMethod testMethod = ParsingFixtures.testMethod("test");
		TestClass testClass = ParsingFixtures.testClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		return testClass;
	}

	public static TestClass testClassWintOneTestMethodWithOneFixture() {
		MethodCallStatement fixture = ParsingFixtures.methodCallWithOneIntegerParameter("sut", 0);
		TestMethod testMethod = ParsingFixtures.testMethod("test", Arrays.asList(fixture));
		TestClass testClass = ParsingFixtures.testClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		return testClass;
	}

	public static TestClass testClassWintOneTestMethodWithOneAssert() {
		MethodCallStatement assertion = ParsingFixtures.methodCallWithTwoIntegerParameters("assertEquals", 0, 0);
		TestMethod testMethod = ParsingFixtures.testMethod("test", Arrays.asList(assertion));
		TestClass testClass = ParsingFixtures.testClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		return testClass;
	}

}
