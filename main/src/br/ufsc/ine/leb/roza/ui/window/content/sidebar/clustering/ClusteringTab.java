package br.ufsc.ine.leb.roza.ui.window.content.sidebar.clustering;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class ClusteringTab implements UiComponent {

	private Sidebar sidebar;

	private JPanel container;
	private JTable table;
	private JButton previousLevelButton;
	private JButton nextLevelButton;

	public ClusteringTab(Sidebar sidebar) {
		this.sidebar = sidebar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		container = new JPanel();
		sidebar.addComponent("Clustering", container);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new PreviousLevelButton(this));
		childs.add(new NextLevelButton(this));
		childs.add(new ClusteringLevelsTable(this));
	}

	@Override
	public void start() {
		GroupLayout group = new GroupLayout(container);
		container.setLayout(group);
		group.setHorizontalGroup(group.createParallelGroup().addGroup(GroupLayout.Alignment.CENTER, group.createSequentialGroup().addComponent(previousLevelButton).addComponent(nextLevelButton)).addComponent(table));
		group.setVerticalGroup(group.createSequentialGroup().addGroup(group.createParallelGroup().addComponent(previousLevelButton).addComponent(nextLevelButton)).addComponent(table));
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public void setNextButton(JButton button) {
		nextLevelButton = button;
	}

	public void setPreviousButton(JButton button) {
		previousLevelButton = button;
	}

}
