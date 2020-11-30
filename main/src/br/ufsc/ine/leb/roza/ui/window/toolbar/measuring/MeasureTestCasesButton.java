package br.ufsc.ine.leb.roza.ui.window.toolbar.measuring;

import java.util.List;

import javax.swing.JButton;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class MeasureTestCasesButton implements UiComponent {

	private MeasuringTab toolbar;

	public MeasureTestCasesButton(MeasuringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JButton button = new JButton("Measure Tests");
		button.setEnabled(false);
		toolbar.addComponent(button);
		hub.loadTestClassesSubscribe(classes -> button.setEnabled(false));
		hub.extractTestCasesSubscribe(testCases -> button.setEnabled(true));
		button.addActionListener(listner -> {
			SimilarityReport similarityReort = manager.measureTestCases();
			hub.measureTestsPublish(similarityReort);
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
