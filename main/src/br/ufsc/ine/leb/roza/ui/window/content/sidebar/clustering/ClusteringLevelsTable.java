package br.ufsc.ine.leb.roza.ui.window.content.sidebar.clustering;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.ufsc.ine.leb.roza.clustering.Level;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.utils.FormatterUtils;

public class ClusteringLevelsTable implements UiComponent {

	private ClusteringTab clusteringTab;

	public ClusteringLevelsTable(ClusteringTab clusteringTab) {
		this.clusteringTab = clusteringTab;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JTable table = new JTable();
		hub.distributeTestsSubscribe(levels -> showTable(hub, table, levels));
		hub.selectLevelSubscribe(level -> selectLevel(table, level));
		clusteringTab.setTable(table);
	}

	private void selectLevel(JTable table, Level level) {
		table.setRowSelectionInterval(level.getStep(), level.getStep());
	}

	private void showTable(Hub hub, JTable table, List<Level> levels) {
		table.setModel(new DefaultTableModel(levels.size(), 2));
		Integer number = 0;
		for (Level level : levels) {
			String name = number + "";
			BigDecimal evaluation = level.getEvaluationToNextLevel();
			String formattedEvaluation = evaluation == null ? "-" : new FormatterUtils().bigDecimal(evaluation);
			table.getModel().setValueAt(name, number, 0);
			table.getModel().setValueAt(formattedEvaluation, number, 1);
			number++;
		}
		table.addMouseListener(createTableListener(hub, table, levels));
	}

	private MouseAdapter createTableListener(Hub hub, JTable table, List<Level> levels) {
		return new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				Integer index = table.rowAtPoint(event.getPoint());
				Level level = levels.get(index);
				hub.selectLevelPublish(level);
			}

		};
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
