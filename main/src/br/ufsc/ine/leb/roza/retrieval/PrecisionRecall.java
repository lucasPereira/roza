package br.ufsc.ine.leb.roza.retrieval;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class PrecisionRecall<T> {

	private final List<T> ranking;
	private final List<T> relevantSet;

	public PrecisionRecall(List<T> ranking, List<T> relevantSet) {
		this.ranking = ranking;
		this.relevantSet = relevantSet;
	}

	public BigDecimal precisionAtRecallLevel(RecallLevel level) {
		Integer amountOfElementsNecessaryForRecallLevel = level.getAmountOfElementsInRelevantSet(relevantSet.size());
		if (amountOfElementsNecessaryForRecallLevel == 0) {
			return BigDecimal.ONE;
		}
		int amountOfElementsFound = 0;
		int index = 0;
		while (index < ranking.size() && amountOfElementsFound < amountOfElementsNecessaryForRecallLevel) {
			T nextElement = ranking.get(index);
			if (relevantSet.contains(nextElement)) {
				amountOfElementsFound++;
			}
			index++;
		}
		boolean foundAllElementsNecessary = amountOfElementsFound == amountOfElementsNecessaryForRecallLevel;
		return foundAllElementsNecessary ? new BigDecimal(amountOfElementsFound).divide(new BigDecimal(index), MathContext.DECIMAL32) : BigDecimal.ZERO;
	}

}
