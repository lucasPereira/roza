package br.ufsc.ine.leb.roza.utils;

import java.math.BigDecimal;
import java.math.MathContext;

public class MathUtils {

	public BigDecimal oneOver(Integer... values) {
		BigDecimal sum = BigDecimal.ZERO;
		for (Integer value : values) {
			sum = sum.add(new BigDecimal(value));
		}
		return BigDecimal.ONE.divide(sum, new MathContext(1000));
	}

}
