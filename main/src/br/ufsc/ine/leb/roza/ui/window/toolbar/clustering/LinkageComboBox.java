package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.util.List;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.clustering.dendrogram.AverageLinkageFactory;
import br.ufsc.ine.leb.roza.clustering.dendrogram.CompleteLinkageFactory;
import br.ufsc.ine.leb.roza.clustering.dendrogram.SingleLinkageFactory;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class LinkageComboBox implements UiComponent {

	private ClusteringTab toolbar;
	private JComboBox<String> combo;

	public LinkageComboBox(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		ComboBoxBuilder builder = new ComboBoxBuilder("Linkage Method");
		builder.add("Single Linkage", () -> manager.setLinkageFactory(new SingleLinkageFactory()));
		builder.add("Complete Linkage", () -> manager.setLinkageFactory(new CompleteLinkageFactory()));
		builder.add("Average Linkage", () -> manager.setLinkageFactory(new AverageLinkageFactory()));
		combo = builder.build();
		toolbar.addComponent(combo);
		hub.loadTestClassesSubscribe(classes -> combo.setEnabled(false));
		hub.extractTestCasesSubscribe(testCases -> combo.setEnabled(false));
		hub.measureTestsSubscribe(similarityReport -> combo.setEnabled(true));
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {
		combo.setSelectedIndex(1);
	}

}
