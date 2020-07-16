package br.ufsc.ine.leb.roza.ui.window.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.extraction.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.Junit5TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.JunitTestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class ExtractorComboBox implements UiComponent {

	private Manager manager;
	private Toolbar toolbar;

	public ExtractorComboBox(Manager manager, Toolbar toolbar) {
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("JUnit 4");
		combo.addItem("JUnit 4 - OMP/CAPES Project");
		combo.addItem("JUnit 5");
		combo.setMaximumSize(combo.getPreferredSize());
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				Integer selected = combo.getSelectedIndex();
				System.out.println(selected);
				switch (selected) {
					case 0:
						selectExtractor(new Junit4TestCaseExtractor());
						break;
					case 1:
						System.out.println("extractor");
						List<String> assertions = Arrays.asList("assegurarTexto", "assegurarValor", "assegurarQuantidadeDeElementos", "assegurarConteudoDeArquivoBaixado", "assegurarNaoMarcado", "assegurarMarcado", "assegurarMarcacao");
						selectExtractor(new JunitTestCaseExtractor(assertions));
						break;
					case 2:
						selectExtractor(new Junit5TestCaseExtractor());
						break;
					default:
						break;
				}
			}

			private void selectExtractor(TestCaseExtractor extractor) {
				manager.setTestCaseExtractor(extractor);
			}

		});
		toolbar.addComponent(combo);
	}

	@Override
	public void createChilds() {}

}
