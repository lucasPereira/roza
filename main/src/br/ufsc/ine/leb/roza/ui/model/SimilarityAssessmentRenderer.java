package br.ufsc.ine.leb.roza.ui.model;

import java.awt.Component;
import java.math.BigDecimal;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.utils.FormatterUtils;

public class SimilarityAssessmentRenderer implements TableCellRenderer {

	private DefaultTableCellRenderer renderer;

	public SimilarityAssessmentRenderer() {
		renderer = new DefaultTableCellRenderer();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		String score = null;
		if (value instanceof SimilarityAssessment) {
			SimilarityAssessment assessment = (SimilarityAssessment) value;
			BigDecimal measure = assessment.getScore();
			score = new FormatterUtils().fractionNumberForUi(measure);
		}
		return renderer.getTableCellRendererComponent(table, score, isSelected, hasFocus, row, column);
	}

}
