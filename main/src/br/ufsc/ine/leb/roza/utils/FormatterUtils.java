package br.ufsc.ine.leb.roza.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import br.ufsc.ine.leb.roza.retrieval.RecallLevel;

public class FormatterUtils {

	public String bigDecimal(BigDecimal value) {
		DecimalFormat formatter = new DecimalFormat();
		formatter.setMaximumFractionDigits(2);
		return formatter.format(value);
	}

	public String recallLevel(RecallLevel value) {
		return String.format("%s%%", value.getLevel() * 10);
	}

}
