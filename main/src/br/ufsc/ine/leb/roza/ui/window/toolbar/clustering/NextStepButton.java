package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.util.List;

import javax.swing.JButton;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class NextStepButton implements UiComponent {

	private ClusteringTab toolbar;

	public NextStepButton(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JButton button = new JButton("Next Step");
		button.setEnabled(false);
		toolbar.addComponent(button);
		hub.loadTestClassesSubscribe(classes -> button.setEnabled(false));
		hub.extractTestCasesSubscribe(testCases -> button.setEnabled(false));
		hub.measureTestsSubscribe(similarityReport -> button.setEnabled(false));
		hub.startTestsDistributionSubscribe(levels -> button.setEnabled(true));
		hub.resetTestsDistributionSubscribe(()-> button.setEnabled(false));
		button.addActionListener(event -> {});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
