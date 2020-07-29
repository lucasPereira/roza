package br.ufsc.ine.leb.roza.ui.window.toolbar.measuring;

import javax.swing.JButton;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class MeasureTestCasesButton implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Toolbar toolbar;

	public MeasureTestCasesButton(Hub hub, Manager manager, Toolbar toolbar) {
		this.hub = hub;
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
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
	public void createChilds() {}

}
