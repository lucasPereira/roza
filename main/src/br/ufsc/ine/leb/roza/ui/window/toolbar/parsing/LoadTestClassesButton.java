package br.ufsc.ine.leb.roza.ui.window.toolbar.parsing;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class LoadTestClassesButton implements UiComponent {

	private ParsingTab toolbar;
	private JButton button;

	public LoadTestClassesButton(ParsingTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		button = new JButton("Load Test Classes");
		toolbar.addComponent(button);
		button.addActionListener(event -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("expt/resources/c"));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setFileFilter(new FileNameExtensionFilter("Java", "java"));
			chooser.setMultiSelectionEnabled(true);
			Integer result = chooser.showOpenDialog(SwingUtilities.getRoot(button));
			if (result == JFileChooser.APPROVE_OPTION) {
				File[] files = chooser.getSelectedFiles();
				List<TestClass> classes = manager.loadClasses(Arrays.asList(files));
				hub.loadTestClassesPublish(classes);
				hub.infoMessagePublish(String.format("Loaded classes: %d", classes.size()));
			}
		});

	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
