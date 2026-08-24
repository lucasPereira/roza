package br.ufsc.ine.leb.roza.ui.modern;

import java.util.Comparator;

final class NaturalNameOrder implements Comparator<String> {

	static final NaturalNameOrder INSTANCE = new NaturalNameOrder();
	static final Comparator<String> PATH = NaturalNameOrder::comparePath;

	@Override
	public int compare(String left, String right) {
		return compareText(left, right);
	}

	static int comparePath(String left, String right) {
		String[] leftParts = splitPath(left);
		String[] rightParts = splitPath(right);
		int limit = Math.min(leftParts.length, rightParts.length);
		for (int index = 0; index < limit; index++) {
			int comparison = compareText(leftParts[index], rightParts[index]);
			if (comparison != 0) {
				return comparison;
			}
		}
		return Integer.compare(leftParts.length, rightParts.length);
	}

	private static String[] splitPath(String path) {
		return path.replace('\\', '/').split("/");
	}

	private static int compareText(String left, String right) {
		int leftIndex = 0;
		int rightIndex = 0;
		while (leftIndex < left.length() && rightIndex < right.length()) {
			boolean leftDigit = Character.isDigit(left.charAt(leftIndex));
			boolean rightDigit = Character.isDigit(right.charAt(rightIndex));
			if (leftDigit && rightDigit) {
				int leftEnd = endOfDigits(left, leftIndex);
				int rightEnd = endOfDigits(right, rightIndex);
				int comparison = compareDigits(left, leftIndex, leftEnd, right, rightIndex, rightEnd);
				if (comparison != 0) {
					return comparison;
				}
				leftIndex = leftEnd;
				rightIndex = rightEnd;
				continue;
			}
			int comparison = Character.compare(
					Character.toLowerCase(left.charAt(leftIndex)),
					Character.toLowerCase(right.charAt(rightIndex)));
			if (comparison != 0) {
				return comparison;
			}
			leftIndex++;
			rightIndex++;
		}
		return Integer.compare(left.length() - leftIndex, right.length() - rightIndex);
	}

	private static int endOfDigits(String value, int start) {
		int index = start;
		while (index < value.length() && Character.isDigit(value.charAt(index))) {
			index++;
		}
		return index;
	}

	private static int compareDigits(String left, int leftStart, int leftEnd, String right, int rightStart, int rightEnd) {
		int leftSignificant = skipLeadingZeros(left, leftStart, leftEnd);
		int rightSignificant = skipLeadingZeros(right, rightStart, rightEnd);
		int leftDigits = leftEnd - leftSignificant;
		int rightDigits = rightEnd - rightSignificant;
		if (leftDigits != rightDigits) {
			return Integer.compare(leftDigits, rightDigits);
		}
		int comparison = left.substring(leftSignificant, leftEnd).compareTo(right.substring(rightSignificant, rightEnd));
		if (comparison != 0) {
			return comparison;
		}
		return Integer.compare(leftEnd - leftStart, rightEnd - rightStart);
	}

	private static int skipLeadingZeros(String value, int start, int end) {
		int index = start;
		while (index < end - 1 && value.charAt(index) == '0') {
			index++;
		}
		return index;
	}
}
