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

	private final ClusteringTab clusteringTab;

	private List<Level> levels;

	private JTable table;

	public ClusteringLevelsTable(ClusteringTab clusteringTab) {
		this.clusteringTab = clusteringTab;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		table = new JTable();
		hub.distributeTestsSubscribe(levels -> {
			this.levels = levels;
			showTable();
			selectLevel(table, levels.get(0));
			manager.selectCluster(levels.get(0).getClusters());
		});
		hub.selectLevelSubscribe(level -> selectLevel(table, level));
		clusteringTab.setTable(table);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				int index = table.rowAtPoint(event.getPoint());
				Level level = levels.get(index);
				hub.selectLevelPublish(level);
				manager.selectCluster(level.getClusters());
			}

		});
	}

	private void selectLevel(JTable table, Level level) {
		table.setRowSelectionInterval(level.getStep(), level.getStep());
	}

	private void showTable() {
		table.setModel(new DefaultTableModel(levels.size(), 2));
		int number = 0;
		for (Level level : levels) {
			String name = number + "";
			BigDecimal evaluation = level.getEvaluationToThisLevel();
			String formattedEvaluation = evaluation == null ? "-" : new FormatterUtils().fractionNumberForUi(evaluation);
			table.getModel().setValueAt(name, number, 0);
			table.getModel().setValueAt(formattedEvaluation, number, 1);
			number++;
		}
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {}

}
