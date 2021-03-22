package br.ufsc.ine.leb.roza.ui.window.content.sidebar.measurements;

import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.utils.FormatterUtils;
import br.ufsc.ine.leb.roza.utils.ReportUtils;

public class MatrixMeasurementPanel implements UiComponent {

	private MeasurementsTab measurementsTab;

	public MatrixMeasurementPanel(MeasurementsTab measurementsTab) {
		this.measurementsTab = measurementsTab;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JTable table = new JTable();
		measurementsTab.addMiddleComponent(table);
		hub.loadTestClassesSubscribe(testClasses -> {
			table.setModel(new DefaultTableModel());
		});
		hub.extractTestCasesSubscribe(testCases -> {
			table.setModel(new DefaultTableModel());
		});
		hub.measureTestsSubscribe(similarityReport -> {
			List<TestCase> testCases = new ReportUtils().getUniqueTestCases(similarityReport);
			table.setModel(new DefaultTableModel(testCases.size(), testCases.size()));
			Integer col = 0;
			for (TestCase source : testCases) {
				Integer row = 0;
				for (TestCase target: testCases) {
					SimilarityAssessment measure = similarityReport.getPair(source, target);
					String score = new FormatterUtils().bigDecimal(measure.getScore());
					table.getModel().setValueAt(score, row, col);
					row++;
				}
				col++;
			}
		});
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
