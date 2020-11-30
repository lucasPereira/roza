package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class LinkageMethodComboBox implements UiComponent {

	private Hub hub;
	private ClusteringTab toolbar;

	public LinkageMethodComboBox(Hub hub, ClusteringTab toolbar) {
		this.hub = hub;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JComboBox<String> combo = new JComboBox<>();
		combo.setToolTipText("Linkage Method");
		combo.addItem("Complete Linkage");
		combo.addItem("Single Linkage");
		combo.addItem("Average Linkage");
		combo.setMaximumSize(combo.getPreferredSize());
		combo.setEnabled(false);
		hub.loadTestClassesSubscribe(classes -> combo.setEnabled(false));
		hub.extractTestCasesSubscribe(testCases -> combo.setEnabled(false));
		hub.measureTestsSubscribe(similarityReport -> combo.setEnabled(true));
		toolbar.addComponent(combo);
	}

	@Override
	public void createChilds() {}

}
