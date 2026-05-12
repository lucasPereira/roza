package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class JplagReportParserTest {

	private final JplagReportParser parser = new JplagReportParser();

	@Test
	void shouldParseDirectionalSimilarityHeader() {
		String report = ""
				+ "<html><body>"
				+ "<table>"
				+ "<tr><th><th>Source.java (85.71429%)<th>Target.java (66.666664%)<th>Tokens"
				+ "<tr><td><td>Source.java(4-5)<td>Target.java(2-3)<td>3"
				+ "</table>"
				+ "</body></html>";

		JplagSimilarityReport similarity = parser.parse(report);

		assertEquals("Source.java", similarity.sourceFile());
		assertEquals("Target.java", similarity.targetFile());
		assertEquals(0.8571429, similarity.sourceSimilarity(), 0.000001);
		assertEquals(0.66666664, similarity.targetSimilarity(), 0.000001);
	}

	@Test
	void shouldRejectReportWithoutSimilarityHeader() {
		assertThrows(IllegalArgumentException.class, () -> parser.parse("<html><body>No match table</body></html>"));
	}

	@Test
	void shouldRejectInvalidSimilarityHeader() {
		String report = "<html><body><table><tr><th><th>Source.java<th>Target.java (100.0%)</table></body></html>";

		assertThrows(IllegalArgumentException.class, () -> parser.parse(report));
	}
}
