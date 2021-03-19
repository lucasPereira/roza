package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.util.List;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.clustering.dendrogram.BiggestClusterReferee;
import br.ufsc.ine.leb.roza.clustering.dendrogram.InsecureReferee;
import br.ufsc.ine.leb.roza.clustering.dendrogram.SmallestClusterReferee;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class RefereeComboBox implements UiComponent {

	private ClusteringTab toolbar;
	private JComboBox<String> combo;

	public RefereeComboBox(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		ComboBoxBuilder builder = new ComboBoxBuilder("Referee Strategy");
		builder.add("Smallest Cluster Referee", () -> manager.setReferee(new BiggestClusterReferee()));
		builder.add("Biggest Cluster Referee", () -> manager.setReferee(new SmallestClusterReferee()));
		builder.add("Insecure Referee", () -> manager.setReferee(new InsecureReferee()));
		combo = builder.build();
		toolbar.addComponent(combo);
		hub.loadTestClassesSubscribe(classes -> combo.setEnabled(false));
		hub.extractTestCasesSubscribe(testCases -> combo.setEnabled(false));
		hub.measureTestsSubscribe(similarityReport -> combo.setEnabled(true));
		hub.startTestsDistributionSubscribe(() -> combo.setEnabled(false));
		hub.resetTestsDistributionSubscribe(()-> combo.setEnabled(true));
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {
		combo.setSelectedIndex(2);
	}

}
