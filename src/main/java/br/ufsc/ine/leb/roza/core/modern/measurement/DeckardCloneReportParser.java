package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DeckardCloneReportParser {

	private static final Pattern ENTRY = Pattern.compile(
			"\\S+\\s+dist:[^\\s]+\\s+FILE\\s+(\\S+)\\s+LINE:(\\d+):(\\d+)\\s+NODE_KIND:(\\d+)\\s+.*\\bTBID:(\\d+)\\s+TEID:(\\d+)\\b");

	public List<CloneFragment> parse(String reportContent) {
		Objects.requireNonNull(reportContent);
		List<CloneFragment> fragments = new ArrayList<>();
		for (String cluster : reportContent.split("(?:\\R\\s*){2,}")) {
			List<DeckardCloneEntry> entries = parseCluster(cluster);
			for (DeckardCloneEntry source : entries) {
				for (DeckardCloneEntry target : entries) {
					if (!source.file().equals(target.file()) && source.sameTreePositionAs(target)) {
						fragments.add(new CloneFragment(source.file(), target.file(), source.startLine(), source.endLine()));
					}
				}
			}
		}
		return fragments;
	}

	private List<DeckardCloneEntry> parseCluster(String cluster) {
		List<DeckardCloneEntry> entries = new ArrayList<>();
		for (String line : cluster.split("\\R")) {
			if (!line.isBlank()) {
				entries.add(parseEntry(line));
			}
		}
		return entries;
	}

	private DeckardCloneEntry parseEntry(String line) {
		Matcher matcher = ENTRY.matcher(line.trim());
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Invalid Deckard clone report entry: " + line);
		}
		String file = matcher.group(1);
		int startLine = Integer.parseInt(matcher.group(2));
		int length = Integer.parseInt(matcher.group(3));
		int nodeKind = Integer.parseInt(matcher.group(4));
		int treeBeginId = Integer.parseInt(matcher.group(5));
		int treeEndId = Integer.parseInt(matcher.group(6));
		return new DeckardCloneEntry(file, startLine, length, nodeKind, treeBeginId, treeEndId);
	}

	private static final class DeckardCloneEntry {

		private final String file;
		private final int startLine;
		private final int length;
		private final int nodeKind;
		private final int treeBeginId;
		private final int treeEndId;

		private DeckardCloneEntry(String file, int startLine, int length, int nodeKind, int treeBeginId, int treeEndId) {
			this.file = file;
			this.startLine = startLine;
			this.length = length;
			this.nodeKind = nodeKind;
			this.treeBeginId = treeBeginId;
			this.treeEndId = treeEndId;
		}

		private String file() {
			return file;
		}

		private int startLine() {
			return startLine;
		}

		private int endLine() {
			return startLine + length - 1;
		}

		private boolean sameTreePositionAs(DeckardCloneEntry other) {
			return nodeKind == other.nodeKind && treeBeginId == other.treeBeginId && treeEndId == other.treeEndId;
		}
	}
}
