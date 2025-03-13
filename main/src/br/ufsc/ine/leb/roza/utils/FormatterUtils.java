package br.ufsc.ine.leb.roza.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import br.ufsc.ine.leb.roza.retrieval.RecallLevel;

public class FormatterUtils {

	public String fractionNumberForCsv(BigDecimal value) {
		DecimalFormat formatter = new DecimalFormat();
		formatter.setMaximumFractionDigits(Integer.MAX_VALUE);
		formatter.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.of("pt", "BR")));
		return formatter.format(value);
	}

	public String fractionNumberForUi(BigDecimal value) {
		DecimalFormat formatter = new DecimalFormat();
		formatter.setMaximumFractionDigits(6);
		formatter.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.of("pt", "BR")));
		return formatter.format(value);
	}

	public String fractionNumberForFileName(Double value) {
		return String.format(Locale.of("pt", "BR"), "%.1f", value);
	}

	public String fractionNumberForDeckardConfiguration(Double value) {
		return String.format(Locale.ENGLISH, "%.1f", value);
	}

	public String recallLevel(RecallLevel value) {
		return String.format("%s%%", value.getLevel() * 10);
	}

}
