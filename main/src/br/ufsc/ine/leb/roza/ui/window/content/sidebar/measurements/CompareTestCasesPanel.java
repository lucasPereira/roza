package br.ufsc.ine.leb.roza.ui.window.content.sidebar.measurements;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.model.TestCaseRenderer;
import br.ufsc.ine.leb.roza.utils.ReportUtils;

public class CompareTestCasesPanel implements UiComponent {

	private Hub hub;
	private MeasurementsTab measurementsTab;

	public CompareTestCasesPanel(Hub hub, MeasurementsTab measurementsTab) {
		this.hub = hub;
		this.measurementsTab = measurementsTab;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
		panel.setLayout(layout);
		JComboBox<TestCase> sourceCombo = new JComboBox<>();
		JComboBox<TestCase> targetCombo = new JComboBox<>();
		DefaultComboBoxModel<TestCase> sourceModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<TestCase> targetModel = new DefaultComboBoxModel<>();
		sourceCombo.setModel(sourceModel);
		targetCombo.setModel(targetModel);
		sourceCombo.setRenderer(new TestCaseRenderer());
		targetCombo.setRenderer(new TestCaseRenderer());
		hub.loadTestClassesSubscribe(testClasses -> {
			sourceModel.removeAllElements();
			targetModel.removeAllElements();
		});
		hub.extractTestCasesSubscribe(testCases -> {
			sourceModel.removeAllElements();
			targetModel.removeAllElements();
		});
		hub.measureTestsSubscribe(similarityReport -> {
			List<TestCase> testCases = new ReportUtils().getUniqueTestCases(similarityReport);
			sourceModel.removeAllElements();
			targetModel.removeAllElements();
			testCases.forEach(testCase -> {
				sourceModel.addElement(testCase);
				targetModel.addElement(testCase);
			});
			sourceCombo.setMaximumSize(sourceCombo.getPreferredSize());
			targetCombo.setMaximumSize(targetCombo.getPreferredSize());
		});
		panel.add(new JScrollPane(sourceCombo));
		panel.add(new JScrollPane(targetCombo));
		measurementsTab.addComponent(panel);
	}

	@Override
	public void createChilds() {}

}
