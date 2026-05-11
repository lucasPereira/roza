package br.ufsc.ine.leb.roza.ui.legacy.window.toolbar.clustering;

import java.util.List;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.core.legacy.clustering.AverageLinkageFactory;
import br.ufsc.ine.leb.roza.core.legacy.clustering.CompleteLinkageFactory;
import br.ufsc.ine.leb.roza.core.legacy.clustering.SingleLinkageFactory;
import br.ufsc.ine.leb.roza.core.legacy.clustering.SumOfIdsLinkageFactory;
import br.ufsc.ine.leb.roza.ui.legacy.Hub;
import br.ufsc.ine.leb.roza.ui.legacy.Manager;
import br.ufsc.ine.leb.roza.ui.legacy.UiComponent;
import br.ufsc.ine.leb.roza.ui.legacy.shared.ComboBoxBuilder;

public class LinkageComboBox implements UiComponent {

	private final ClusteringTab toolbar;
	private JComboBox<String> combo;

	public LinkageComboBox(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		ComboBoxBuilder builder = new ComboBoxBuilder("Linkage Method");
		builder.add("Sum of Identifiers Linkage", () -> manager.setLinkageFactory(new SumOfIdsLinkageFactory()));
		builder.add("Single Linkage", () -> manager.setLinkageFactory(new SingleLinkageFactory()));
		builder.add("Complete Linkage", () -> manager.setLinkageFactory(new CompleteLinkageFactory()));
		builder.add("Average Linkage", () -> manager.setLinkageFactory(new AverageLinkageFactory()));
		combo = builder.build();
		toolbar.addComponent(combo);
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {
		combo.setSelectedIndex(0);
	}

}
