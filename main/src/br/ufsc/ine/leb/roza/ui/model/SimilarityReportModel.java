package br.ufsc.ine.leb.roza.ui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.utils.ReportUtils;

public class SimilarityReportModel extends AbstractTableModel implements TableModel {

	private static final long serialVersionUID = 1L;

	private SimilarityReport report;

	private List<TestCase> tests;

	public SimilarityReportModel(SimilarityReport report) {
		this.report = report;
		this.tests = new ReportUtils().getUniqueTestCases(report);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return SimilarityAssessment.class;
	}

	@Override
	public int getRowCount() {
		return tests.size();
	}

	@Override
	public int getColumnCount() {
		return tests.size();
	}

	@Override
	public SimilarityAssessment getValueAt(int row, int column) {
		TestCase source = tests.get(row);
		TestCase target = tests.get(column);
		return report.getPair(source, target);
	}

}
