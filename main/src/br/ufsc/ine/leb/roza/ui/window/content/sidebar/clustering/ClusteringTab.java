package br.ufsc.ine.leb.roza.ui.window.content.sidebar.clustering;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import br.ufsc.ine.leb.roza.clustering.dendrogram.Level;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;
import br.ufsc.ine.leb.roza.utils.FormatterUtils;

public class ClusteringTab implements UiComponent {

	private Hub hub;

	private Sidebar sidebar;

	private JPanel tablePanel;
	private JTable table;
	private JButton previousLevelButton;
	private JButton nextLevelButton;

	private Integer levelIndex;

	public ClusteringTab(Sidebar sidebar) {
		this.sidebar = sidebar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		this.hub = hub;
		JPanel container = new JPanel();

		previousLevelButton = new JButton("<");
		nextLevelButton = new JButton(">");
		previousLevelButton.setEnabled(false);
		nextLevelButton.setEnabled(false);

		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());

		GroupLayout group = new GroupLayout(container);
		container.setLayout(group);
		group.setHorizontalGroup(group.createParallelGroup().addGroup(GroupLayout.Alignment.CENTER, group.createSequentialGroup().addComponent(previousLevelButton).addComponent(nextLevelButton)).addComponent(tablePanel));
		group.setVerticalGroup(group.createSequentialGroup().addGroup(group.createParallelGroup().addComponent(previousLevelButton).addComponent(nextLevelButton)).addComponent(tablePanel));

		sidebar.addComponent("Clustering", container);

		hub.distributeTestsSubscribe(levels -> buildTableOnDistribution(levels));
		hub.distributeTestsSubscribe(levels -> buildButtonsActionOnDistribution(levels));
	}

	private void buildButtonsActionOnDistribution(List<Level> levels) {
		levelIndex = 0;
		if (levels.size() > 1) {
			nextLevelButton.setEnabled(true);
		}
		nextLevelButton.addActionListener(event -> {
			levelIndex++;
			selectLevel(levels);
		});
		previousLevelButton.addActionListener(event -> {
			levelIndex--;
			selectLevel(levels);
		});
	}

	private void buildTableOnDistribution(List<Level> levels) {
		tablePanel.removeAll();
		table = new JTable(levels.size(), 2);
		Integer number = 0;
		for (Level level : levels) {
			String name = number + "";
			BigDecimal evaluation = level.getEvaluationToNextLevel();
			String formatedEvaluation = evaluation == null ? "-" : new FormatterUtils().bigDecimal(evaluation);
			table.getModel().setValueAt(name, number, 0);
			table.getModel().setValueAt(formatedEvaluation, number, 1);
			number++;
		}
		tablePanel.add(table, BorderLayout.PAGE_START);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				levelIndex = table.rowAtPoint(event.getPoint());
				selectLevel(levels);
			}

		});
	}

	private void selectLevel(List<Level> levels) {
		Level level = levels.get(levelIndex);
		table.setRowSelectionInterval(levelIndex, levelIndex);
		previousLevelButton.setEnabled(levelIndex > 0 ? true : false);
		nextLevelButton.setEnabled(levelIndex < levels.size() - 1 ? true : false);
		hub.selectLevelPublish(level);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
