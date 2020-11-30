package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import javax.swing.JButton;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class StartTestsDistributionButton implements UiComponent {

	private Hub hub;
	private Manager manager;
	private ClusteringTab toolbar;

	public StartTestsDistributionButton(Hub hub, Manager manager, ClusteringTab toolbar) {
		this.hub = hub;
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JButton button = new JButton("Start Tests Distribution");
		button.setEnabled(false);
		toolbar.addComponent(button);
		hub.loadTestClassesSubscribe(classes -> button.setEnabled(false));
		hub.extractTestCasesSubscribe(testCases -> button.setEnabled(false));
		hub.measureTestsSubscribe(similarityReport -> button.setEnabled(true));
		button.addActionListener(event -> {
			manager.distributeTests();
		});
	}

	@Override
	public void createChilds() {}

}
