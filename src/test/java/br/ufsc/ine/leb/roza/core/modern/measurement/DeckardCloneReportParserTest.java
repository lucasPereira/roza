package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class DeckardCloneReportParserTest {

	private final DeckardCloneReportParser parser = new DeckardCloneReportParser();

	@Test
	void shouldPairOnlyEntriesAtTheSameTreePositionInsideACluster() {
		String report = ""
				+ "000000000\tdist:0.0\tFILE ../../output/materializer/Source.java LINE:3:1 NODE_KIND:121 nVARs:1 NUM_NODE:5 TBID:10 TEID:13\n"
				+ "000000001\tdist:0.0\tFILE ../../output/materializer/Source.java LINE:4:1 NODE_KIND:121 nVARs:1 NUM_NODE:5 TBID:14 TEID:17\n"
				+ "000000002\tdist:0.0\tFILE ../../output/materializer/Source.java LINE:5:1 NODE_KIND:121 nVARs:1 NUM_NODE:5 TBID:18 TEID:21\n"
				+ "000000003\tdist:0.0\tFILE ../../output/materializer/Target.java LINE:3:1 NODE_KIND:121 nVARs:1 NUM_NODE:5 TBID:10 TEID:13\n"
				+ "000000004\tdist:0.0\tFILE ../../output/materializer/Target.java LINE:4:1 NODE_KIND:121 nVARs:1 NUM_NODE:5 TBID:14 TEID:17\n";

		List<CloneFragment> fragments = parser.parse(report);

		assertEquals(4, fragments.size());
		assertFragment(fragments.get(0), "../../output/materializer/Source.java", "../../output/materializer/Target.java", 3, 3);
		assertFragment(fragments.get(1), "../../output/materializer/Source.java", "../../output/materializer/Target.java", 4, 4);
		assertFragment(fragments.get(2), "../../output/materializer/Target.java", "../../output/materializer/Source.java", 3, 3);
		assertFragment(fragments.get(3), "../../output/materializer/Target.java", "../../output/materializer/Source.java", 4, 4);
	}

	@Test
	void shouldParseMultipleClusters() {
		String report = ""
				+ "000000000\tdist:0.0\tFILE Source.java LINE:3:2 NODE_KIND:113 nVARs:2 NUM_NODE:20 TBID:4 TEID:12\n"
				+ "000000001\tdist:0.0\tFILE Target.java LINE:3:2 NODE_KIND:113 nVARs:2 NUM_NODE:20 TBID:4 TEID:12\n"
				+ "\n"
				+ "000000000\tdist:0.0\tFILE Source.java LINE:6:1 NODE_KIND:121 nVARs:1 NUM_NODE:5 TBID:20 TEID:23\n"
				+ "000000001\tdist:0.0\tFILE Target.java LINE:5:1 NODE_KIND:121 nVARs:1 NUM_NODE:5 TBID:20 TEID:23\n";

		List<CloneFragment> fragments = parser.parse(report);

		assertEquals(4, fragments.size());
		assertFragment(fragments.get(0), "Source.java", "Target.java", 3, 4);
		assertFragment(fragments.get(2), "Source.java", "Target.java", 6, 6);
	}

	@Test
	void shouldRejectInvalidReportEntries() {
		assertThrows(IllegalArgumentException.class, () -> parser.parse("not a deckard entry"));
	}

	private void assertFragment(CloneFragment fragment, String sourceFile, String targetFile, int startLine, int endLine) {
		assertEquals(sourceFile, fragment.sourceFile());
		assertEquals(targetFile, fragment.targetFile());
		assertEquals(startLine, fragment.startLine());
		assertEquals(endLine, fragment.endLine());
	}
}
