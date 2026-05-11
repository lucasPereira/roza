package br.ufsc.ine.leb.roza.ui.legacy.window.toolbar.refactoring;

import java.util.List;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.core.legacy.refactoring.IncrementalTestClassNamingStrategy;
import br.ufsc.ine.leb.roza.core.legacy.refactoring.SimpleClusterRefactor;
import br.ufsc.ine.leb.roza.ui.legacy.Hub;
import br.ufsc.ine.leb.roza.ui.legacy.Manager;
import br.ufsc.ine.leb.roza.ui.legacy.UiComponent;
import br.ufsc.ine.leb.roza.ui.legacy.shared.ComboBoxBuilder;

public class RefactorStrategyComboBox implements UiComponent {

	private final RefactoringTab toolbar;
	private JComboBox<String> combo;

	public RefactorStrategyComboBox(RefactoringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		combo = new ComboBoxBuilder("Refactor Strategy").add("Simple Cluster Refactor", () -> manager.setRefactorStrategy(new SimpleClusterRefactor(new IncrementalTestClassNamingStrategy()))).build();
		combo.setEnabled(true);
		toolbar.addComponent(combo);
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {
		combo.setSelectedIndex(0);
	}

}
