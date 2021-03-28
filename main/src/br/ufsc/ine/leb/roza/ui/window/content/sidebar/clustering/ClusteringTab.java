package br.ufsc.ine.leb.roza.ui.window.content.sidebar.clustering;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;

import br.ufsc.ine.leb.roza.clustering.dendrogram.Level;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;
import br.ufsc.ine.leb.roza.utils.FormatterUtils;

public class ClusteringTab implements UiComponent {

	private Sidebar sidebar;

	public ClusteringTab(Sidebar sidebar) {
		this.sidebar = sidebar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		sidebar.addComponent("Clustering", panel);
		hub.distributeTestsSubscribe(levels -> {
			panel.removeAll();
			JTable table = new JTable(levels.size(), 2);
			Integer number = 0;
			for (Level level : levels) {
				String name = number + "";
				BigDecimal evaluation = level.getEvaluationToNextLevel();
				String formatedEvaluation = evaluation == null ? "-" : new FormatterUtils().bigDecimal(evaluation);
				table.getModel().setValueAt(name, number, 0);
				table.getModel().setValueAt(formatedEvaluation, number, 1);
				number++;
			}
			panel.add(table, BorderLayout.PAGE_START);
			table.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent event) {
					Integer row = table.rowAtPoint(event.getPoint());
					Level level = levels.get(row);
					hub.selectLevelPublish(level );
				}

			});
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
