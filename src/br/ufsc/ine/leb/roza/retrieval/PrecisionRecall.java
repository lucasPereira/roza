package br.ufsc.ine.leb.roza.retrieval;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class PrecisionRecall<T> {

	private List<T> ranking;
	private List<T> relevantSet;

	public PrecisionRecall(List<T> ranking, List<T> relevantSet) {
		this.ranking = ranking;
		this.relevantSet = relevantSet;
	}

	public BigDecimal precisionAtRecallLevel(RecallLevel level) {
		Integer amountOfElementsNecessaryForRecallLevel = level.getAmountOfElementsInRelevantSet(relevantSet.size());
		if (amountOfElementsNecessaryForRecallLevel == 0) {
			return BigDecimal.ONE;
		}
		Integer amountOfElementsFound = 0;
		Integer index = 0;
		while (index < ranking.size() && amountOfElementsFound < amountOfElementsNecessaryForRecallLevel) {
			T nextElement = ranking.get(index);
			if (relevantSet.contains(nextElement)) {
				amountOfElementsFound++;
			}
			index++;
		}
		Boolean foundAllElementsNecessary = amountOfElementsFound == amountOfElementsNecessaryForRecallLevel;
		return foundAllElementsNecessary ? new BigDecimal(amountOfElementsFound).divide(new BigDecimal(index), MathContext.DECIMAL32) : BigDecimal.ZERO;
	}

}
