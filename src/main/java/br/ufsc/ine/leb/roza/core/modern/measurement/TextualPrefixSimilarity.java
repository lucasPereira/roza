package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.List;

public final class TextualPrefixSimilarity {

	public static int commonPrefixSize(List<String> source, List<String> target) {
		int commonPrefixSize = 0;
		while (commonPrefixSize < source.size()
				&& commonPrefixSize < target.size()
				&& source.get(commonPrefixSize).equals(target.get(commonPrefixSize))) {
			commonPrefixSize++;
		}
		return commonPrefixSize;
	}
}
