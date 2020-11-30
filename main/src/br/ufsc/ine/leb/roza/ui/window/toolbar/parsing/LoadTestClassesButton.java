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

	private Hub hub;
	private Manager manager;
	private ParsingTab toolbar;
	private JButton button;

	public LoadTestClassesButton(Hub hub, Manager manager, ParsingTab toolbar) {
		this.hub = hub;
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		button = new JButton("Load Test Classes");
		toolbar.addComponent(button);
		button.addActionListener(event -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("Java", "java"));
			chooser.setMultiSelectionEnabled(true);
			Integer result = chooser.showOpenDialog(SwingUtilities.getRoot(button));
			if (result == JFileChooser.APPROVE_OPTION) {
				File[] files = chooser.getSelectedFiles();
				List<TestClass> classes = manager.loadClasses(Arrays.asList(files));
				hub.loadTestClassesPublish(classes);
			}
		});
	}

	@Override
	public void createChilds() {}

}
