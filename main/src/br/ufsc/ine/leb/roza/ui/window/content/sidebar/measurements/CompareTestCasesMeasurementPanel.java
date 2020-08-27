package br.ufsc.ine.leb.roza.ui.window.content.sidebar.measurements;

import javax.swing.JSplitPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.CodePanel;

public class CompareTestCasesMeasurementPanel implements UiComponent {

	private Hub hub;
	private MeasurementsTab measurementsTab;
	private JSplitPane panel;

	public CompareTestCasesMeasurementPanel(Hub hub, MeasurementsTab measurementsTab) {
		this.hub = hub;
		this.measurementsTab = measurementsTab;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		CodePanel sourcePanel = new CodePanel();
		CodePanel targetPanel = new CodePanel();
		panel.setResizeWeight(0.5);
		panel.setLeftComponent(sourcePanel);
		panel.setRightComponent(targetPanel);
		hub.loadTestClassesSubscribe(testClasses -> {
			sourcePanel.clearTestCase();
			targetPanel.clearTestCase();
		});
		hub.extractTestCasesSubscribe(testCases -> {
			sourcePanel.clearTestCase();
			targetPanel.clearTestCase();
		});
		hub.compareTestCaseSubscribe((source, target) -> {
			sourcePanel.setTestCase(source);
			targetPanel.setTestCase(target);
		});
		measurementsTab.addBottomComponent(panel);
	}

	@Override
	public void createChilds() {}

}
