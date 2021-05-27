package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

public class WinnerCombination {

	private Combination combination;
	private BigDecimal evaluation;

	public WinnerCombination(Combination combination, BigDecimal evaluation) {
		this.combination = combination;
		this.evaluation = evaluation;
	}

	public Combination getCombination() {
		return combination;
	}

	public BigDecimal getEvaluation() {
		return evaluation;
	}

}
