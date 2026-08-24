package br.ufsc.ine.leb.roza.core.modern.arrangement;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

public final class ExtractableArrangeRun {

	private final List<CodeStatement> statements;
	private final List<Integer> starts;
	private final List<NamedTypedVariable> liveIns;
	private final Optional<NamedTypedVariable> liveOut;

	public ExtractableArrangeRun(List<CodeStatement> statements, List<Integer> starts, List<NamedTypedVariable> liveIns, Optional<NamedTypedVariable> liveOut) {
		this.statements = List.copyOf(Objects.requireNonNull(statements));
		this.starts = List.copyOf(Objects.requireNonNull(starts));
		this.liveIns = List.copyOf(Objects.requireNonNull(liveIns));
		this.liveOut = Objects.requireNonNull(liveOut);
	}

	public List<CodeStatement> statements() {
		return statements;
	}

	public int startFor(int testIndex) {
		return starts.get(testIndex);
	}

	public int length() {
		return statements.size();
	}

	public List<NamedTypedVariable> liveIns() {
		return liveIns;
	}

	public Optional<NamedTypedVariable> liveOut() {
		return liveOut;
	}

	public boolean overlaps(ExtractableArrangeRun other, int testIndex) {
		int thisStart = startFor(testIndex);
		int otherStart = other.startFor(testIndex);
		int thisEnd = thisStart + length();
		int otherEnd = otherStart + other.length();
		return thisStart < otherEnd && otherStart < thisEnd;
	}

	public boolean declares(NamedTypedVariable variable) {
		String prefix = variable.type() + " " + variable.name();
		return statements.stream().anyMatch(statement -> statement.normalizedText().startsWith(prefix));
	}

	public static int longestPairwiseLength(TestCase source, TestCase target, int minimumLength) {
		List<CodeStatement> sourceArrange = ArrangeProjection.arrangeStatements(source);
		List<CodeStatement> targetArrange = ArrangeProjection.arrangeStatements(target);
		int longest = 0;
		for (int sourceStart = 0; sourceStart < sourceArrange.size(); sourceStart++) {
			for (int targetStart = 0; targetStart < targetArrange.size(); targetStart++) {
				int length = 0;
				while (sourceStart + length < sourceArrange.size()
						&& targetStart + length < targetArrange.size()
						&& sourceArrange.get(sourceStart + length).normalizedText().equals(targetArrange.get(targetStart + length).normalizedText())) {
					length++;
					Optional<ExtractableArrangeRuns.Extractability> sourceExtractability = ExtractableArrangeRuns.extractable(source, sourceStart, length);
					Optional<ExtractableArrangeRuns.Extractability> targetExtractability = ExtractableArrangeRuns.extractable(target, targetStart, length);
					if (sourceExtractability.isEmpty() || targetExtractability.isEmpty()) {
						break;
					}
					if (length >= minimumLength && compatible(sourceExtractability.get(), targetExtractability.get())) {
						longest = Math.max(longest, length);
					}
				}
			}
		}
		return longest;
	}

	private static boolean compatible(ExtractableArrangeRuns.Extractability source, ExtractableArrangeRuns.Extractability target) {
		return source.liveIns().equals(target.liveIns())
				&& source.liveOut().equals(target.liveOut());
	}
}
