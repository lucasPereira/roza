package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.util.List;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.clustering.AnyClusterReferee;
import br.ufsc.ine.leb.roza.clustering.BiggestClusterReferee;
import br.ufsc.ine.leb.roza.clustering.ComposedReferee;
import br.ufsc.ine.leb.roza.clustering.InsecureReferee;
import br.ufsc.ine.leb.roza.clustering.SmallestClusterReferee;
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
		builder.add("Any Cluster Referee", () -> manager.setReferee(new AnyClusterReferee()));
		builder.add("Smallest Cluster Referee", () -> manager.setReferee(new BiggestClusterReferee()));
		builder.add("Smallest and Any Cluster Referee", () -> manager.setReferee(new ComposedReferee(new BiggestClusterReferee(), new AnyClusterReferee())));
		builder.add("Biggest Cluster Referee", () -> manager.setReferee(new SmallestClusterReferee()));
		builder.add("Biggest and Any Cluster Referee", () -> manager.setReferee(new ComposedReferee(new SmallestClusterReferee(), new AnyClusterReferee())));
		builder.add("Insecure Referee", () -> manager.setReferee(new InsecureReferee()));
		combo = builder.build();
		toolbar.addComponent(combo);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {
		combo.setSelectedIndex(5);
	}

}
