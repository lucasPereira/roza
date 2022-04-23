package br.ufsc.ine.leb.roza.extraction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixture.ExtractionFixtures;
import br.ufsc.ine.leb.roza.parsing.RozaStatement;

public class JUnit4AssertionDetectorTest {

	@Test
	void notAssertion() throws Exception {
		RozaStatement statement = ExtractionFixtures.sutCallWithZeroParameter();
		AssertionDetector detector = new JUnit4AssertionDetector();
		assertFalse(detector.isAssertion(statement));
	}

	@Test
	void assertions() throws Exception {
		RozaStatement statement = ExtractionFixtures.assertEqualsWithZeroAndZero();
		AssertionDetector detector = new JUnit4AssertionDetector();
		assertTrue(detector.isAssertion(statement));
	}

}
