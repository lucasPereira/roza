package br.ufsc.ine.leb.roza.ui.window.content.sidebar.clustering;

import java.util.List;

import javax.swing.JButton;

import br.ufsc.ine.leb.roza.clustering.Level;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class NextLevelButton implements UiComponent {

	private ClusteringTab clusteringTab;
	private Level selected;
	private List<Level> levels;

	public NextLevelButton(ClusteringTab clusteringTab) {
		this.clusteringTab = clusteringTab;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JButton button = new JButton(">");
		button.setEnabled(false);
		hub.distributeTestsSubscribe(levels -> {
			this.levels = levels;
			if (!levels.isEmpty()) {
				button.setEnabled(true);
				this.selected = levels.get(0);
			}
		});
		hub.selectLevelSubscribe(level -> {
			selected = level;
			button.setEnabled(selected.getStep() + 1 < levels.size());
		});
		button.addActionListener(event -> {
			Level next = levels.get(selected.getStep() + 1);
			hub.selectLevelPublish(next);
		});
		clusteringTab.setNextButton(button);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
