package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class SimianCloneReportParserTest {

	private final SimianCloneReportParser parser = new SimianCloneReportParser();

	@Test
	void shouldParseBlocksInsideCloneSets() {
		String report = ""
				+ "Similarity Analyser 2.5.10\n"
				+ "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<simian><check><set lineCount=\"3\">"
				+ "<block sourceFile=\"Source.java\" startLineNumber=\"3\" endLineNumber=\"5\"/>"
				+ "<block sourceFile=\"Target.java\" startLineNumber=\"4\" endLineNumber=\"6\"/>"
				+ "</set></check></simian>";

		List<CloneFragment> fragments = parser.parse(report);

		assertEquals(2, fragments.size());
		assertFragment(fragments.get(0), "Source.java", "Target.java", 3, 5);
		assertFragment(fragments.get(1), "Target.java", "Source.java", 4, 6);
	}

	@Test
	void shouldParseMultipleCloneSets() {
		String report = ""
				+ "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<simian><check>"
				+ "<set><block sourceFile=\"A.java\" startLineNumber=\"3\" endLineNumber=\"4\"/>"
				+ "<block sourceFile=\"B.java\" startLineNumber=\"3\" endLineNumber=\"4\"/></set>"
				+ "<set><block sourceFile=\"A.java\" startLineNumber=\"6\" endLineNumber=\"6\"/>"
				+ "<block sourceFile=\"C.java\" startLineNumber=\"5\" endLineNumber=\"5\"/></set>"
				+ "</check></simian>";

		List<CloneFragment> fragments = parser.parse(report);

		assertEquals(4, fragments.size());
		assertFragment(fragments.get(0), "A.java", "B.java", 3, 4);
		assertFragment(fragments.get(2), "A.java", "C.java", 6, 6);
	}

	@Test
	void shouldRejectReportWithoutXml() {
		assertThrows(IllegalArgumentException.class, () -> parser.parse("not xml"));
	}

	private void assertFragment(CloneFragment fragment, String sourceFile, String targetFile, int startLine, int endLine) {
		assertEquals(sourceFile, fragment.sourceFile());
		assertEquals(targetFile, fragment.targetFile());
		assertEquals(startLine, fragment.startLine());
		assertEquals(endLine, fragment.endLine());
	}
}
