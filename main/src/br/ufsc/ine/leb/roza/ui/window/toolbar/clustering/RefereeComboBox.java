package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.clustering.dendrogram.BiggestClusterReferee;
import br.ufsc.ine.leb.roza.clustering.dendrogram.InsecureReferee;
import br.ufsc.ine.leb.roza.clustering.dendrogram.SmallestClusterReferee;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class RefereeComboBox implements UiComponent {

	private Hub hub;
	private Manager manager;
	private ClusteringTab toolbar;

	public RefereeComboBox(Hub hub, Manager manager, ClusteringTab toolbar) {
		this.hub = hub;
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JComboBox<String> combo = new ComboBoxBuilder("Referee Strategy").add("Smallest Cluster Referee", () -> {
			manager.setReferee(new BiggestClusterReferee());
		}).add("Biggest Cluster Referee", () -> {
			manager.setReferee(new SmallestClusterReferee());
		}).add("Insecure Referee", () -> {
			manager.setReferee(new InsecureReferee());
		}).build();
		hub.loadTestClassesSubscribe(classes -> combo.setEnabled(false));
		hub.extractTestCasesSubscribe(testCases -> combo.setEnabled(false));
		hub.measureTestsSubscribe(similarityReport -> combo.setEnabled(true));
		toolbar.addComponent(combo);
	}

	@Override
	public void createChilds() {}

}
