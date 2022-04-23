package br.ufsc.ine.leb.roza.extraction;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixture.ExtractionFixtures;
import br.ufsc.ine.leb.roza.parsing.TestClass;

public class JUnitTestCaseExtractorTest {

	private JUnitTestCaseExtractor extractor;

	@BeforeEach
	void setup() {
		JUnit4AssertionDetector assertionDetector = new JUnit4AssertionDetector();
		extractor = new JUnitTestCaseExtractor(assertionDetector);
	}

	@Test
	void emptyTestClass() throws Exception {
		TestClass testClass = ExtractionFixtures.emptyTestClass();
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(0, testCases.size());
	}

	@Test
	void oneTest() throws Exception {
		TestClass testClass = ExtractionFixtures.testClassWintOneEmptyTestMethod();
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("test", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneFixture() throws Exception {
		TestClass testClass = ExtractionFixtures.testClassWintOneTestMethodWithOneFixture();
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("test", testCases.get(0).getName());
		assertEquals(1, testCases.get(0).getFixtures().size());
		assertEquals("sut(0);", testCases.get(0).getFixtures().get(0).toCode());
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneAssertion() throws Exception {
		TestClass testClass = ExtractionFixtures.testClassWintOneTestMethodWithOneAssert();
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("test", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals("assertEquals(0, 0);", testCases.get(0).getAsserts().get(0).toCode());
	}

}
