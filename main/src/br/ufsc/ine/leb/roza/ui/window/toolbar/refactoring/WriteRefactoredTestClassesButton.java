package br.ufsc.ine.leb.roza.ui.window.toolbar.refactoring;

import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class WriteRefactoredTestClassesButton implements UiComponent {

	private final RefactoringTab toolbar;
	private JButton button;

	public WriteRefactoredTestClassesButton(RefactoringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		button = new JButton("Write Refactored Test Classes");
		toolbar.addComponent(button);
		button.addActionListener(event -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("main/exec/writer"));
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setMultiSelectionEnabled(false);
			int result = chooser.showOpenDialog(SwingUtilities.getRoot(button));
			if (result == JFileChooser.APPROVE_OPTION) {
				String baseFolder = chooser.getSelectedFile().getAbsolutePath();
				new FolderUtils(baseFolder).createEmptyFolder();
				List<TestClass> refactoredClasses = manager.refactorClusters();
				manager.writeTestClasses(baseFolder);
				hub.infoMessagePublish(String.format("Refactored classes: %d", refactoredClasses.size()));
			}
		});
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {}

}
