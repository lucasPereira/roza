package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.util.List;
import java.util.Set;

import javax.swing.JButton;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class StartTestsDistributionButton implements UiComponent {

	private ClusteringTab toolbar;

	public StartTestsDistributionButton(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JButton button = new JButton("Start Tests Distribution");
		button.setEnabled(false);
		toolbar.addComponent(button);
		hub.loadTestClassesSubscribe(classes -> button.setEnabled(false));
		hub.extractTestCasesSubscribe(testCases -> button.setEnabled(false));
		hub.measureTestsSubscribe(similarityReport -> button.setEnabled(true));
		button.addActionListener(event -> {
			button.setEnabled(false);
			hub.startTestsDistributionPublish();
			Set<Cluster> clusters = manager.distributeTests();
			hub.updateClustersPublish(clusters);
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
