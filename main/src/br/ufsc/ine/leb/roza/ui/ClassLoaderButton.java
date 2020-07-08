package br.ufsc.ine.leb.roza.ui;

import java.io.File;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ClassLoaderButton implements UiComponent {

	private Hub hub;
	private Toolbar toolbar;
	private JButton button;

	public ClassLoaderButton(Hub hub, Toolbar toolbar) {
		this.hub = hub;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		button = new JButton("Load classes");
		toolbar.addComponent(button);
		button.addActionListener(event -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("Java", "java"));
			chooser.setMultiSelectionEnabled(true);
			Integer result = chooser.showOpenDialog(SwingUtilities.getRoot(button));
			if (result == JFileChooser.APPROVE_OPTION) {
				File[] files = chooser.getSelectedFiles();
				hub.classesLoadedPublish(Arrays.asList(files));
			}
		});
	}

	@Override
	public void createChilds() {}

}
