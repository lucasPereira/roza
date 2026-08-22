package br.ufsc.ine.leb.roza.core.modern.measurement;

public final class DiceMatchSimilarity {

	public static double score(int matchSize, int sourceSize, int targetSize) {
		int totalSize = sourceSize + targetSize;
		if (totalSize == 0) {
			return 0.0;
		}
		return (2.0 * matchSize) / totalSize;
	}
}
