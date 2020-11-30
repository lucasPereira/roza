package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.clustering.dendrogram.AverageLinkageFactory;
import br.ufsc.ine.leb.roza.clustering.dendrogram.CompleteLinkageFactory;
import br.ufsc.ine.leb.roza.clustering.dendrogram.SingleLinkageFactory;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class LinkageComboBox implements UiComponent {

	private Hub hub;
	private Manager manager;
	private ClusteringTab toolbar;

	public LinkageComboBox(Hub hub, Manager manager, ClusteringTab toolbar) {
		this.hub = hub;
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JComboBox<String> combo = new ComboBoxBuilder("Linkage Method").add("Complete Linkage", () -> {
			manager.setLinkageFactory(new CompleteLinkageFactory());
		}).add("Single Linkage", () -> {
			manager.setLinkageFactory(new SingleLinkageFactory());
		}).add("Average Linkage", () -> {
			manager.setLinkageFactory(new AverageLinkageFactory());
		}).build();
		hub.loadTestClassesSubscribe(classes -> combo.setEnabled(false));
		hub.extractTestCasesSubscribe(testCases -> combo.setEnabled(false));
		hub.measureTestsSubscribe(similarityReport -> combo.setEnabled(true));
		toolbar.addComponent(combo);
	}

	@Override
	public void createChilds() {}

}
