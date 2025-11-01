package br.ufsc.ine.leb.roza.ui.window.toolbar.refactoring;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class RefactoringTab implements UiComponent {

	private final Toolbar toolbar;
	private JPanel panel;

	public RefactoringTab(Toolbar toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JPanel();
		toolbar.addComponent("Refactoring", panel);
	}

	@Override
	public void addChildren(List<UiComponent> children) {
		children.add(new RefactorStrategyComboBox(this));
		children.add(new WriteRefactoredTestClassesButton(this));
	}

	@Override
	public void start() {}

	public void addComponent(Component component) {
		panel.add(component);
	}

}
