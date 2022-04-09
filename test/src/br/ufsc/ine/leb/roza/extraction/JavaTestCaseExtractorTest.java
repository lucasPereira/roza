package br.ufsc.ine.leb.roza.extraction;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixtures.ParsingFixtures;
import br.ufsc.ine.leb.roza.parsing.TestClass;

public class JavaTestCaseExtractorTest {

	private JavaTestCaseExtractor extractor;

	@BeforeEach
	void setup() {
		extractor = new JavaTestCaseExtractor();
	}

	@Test
	void oneTest() throws Exception {
		TestClass testClass = ParsingFixtures.buildTestClassWithOneEmptyTest();
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("test", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneFixture() throws Exception {
		TestClass testClass = ParsingFixtures.buildTestClassWithOneTestWithOneFixture();
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("test", testCases.get(0).getName());
		assertEquals(1, testCases.get(0).getFixtures().size());
		assertEquals("sut(0);", testCases.get(0).getFixtures().get(0).getCode());
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneAssertion() throws Exception {
		TestClass testClass = ParsingFixtures.buildTestClassWithOneTestWithOneAssertion();
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("test", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals("assertEquals(0, 0);", testCases.get(0).getFixtures().get(0).getCode());
	}

}
