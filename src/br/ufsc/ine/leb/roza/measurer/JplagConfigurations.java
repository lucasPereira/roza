package br.ufsc.ine.leb.roza.measurer;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.support.configuration.AbstractConfigurations;
import br.ufsc.ine.leb.roza.support.configuration.Configuration;
import br.ufsc.ine.leb.roza.support.configuration.Configurations;

public class JplagConfigurations extends AbstractConfigurations implements Configurations {

	private JplagIntegerConfiguration sensitivity;
	private JplagStringConfiguration results;
	private JplagStringConfiguration log;
	private JplagStringConfiguration sources;

	public JplagConfigurations() {
		sensitivity = new JplagIntegerConfiguration("t", 1, "Tune the sensitivity of the comparison");
		results = new JplagStringConfiguration("r", null, "Name of directory in which the web pages will be stored");
		log = new JplagStringConfiguration("o", null, "Output file of the parser log");
		sources = new JplagStringConfiguration("s", null, "Look at files in subdirs too");
	}

	@Override
	public List<Configuration> getAll() {
		List<Configuration> configurations = new LinkedList<>();
		configurations.add(sensitivity);
		configurations.add(new JplagStringConfiguration("l", "java17", "Language"));
		configurations.add(new JplagStringConfiguration("m", "0%", "All matches with more than % similarity will be saved"));
		configurations.add(new JplagBooleanConfiguration("vq", false, "No output"));
		configurations.add(new JplagBooleanConfiguration("vl", true, "Detailed output"));
		configurations.add(new JplagBooleanConfiguration("vp", false, "Parser messages output"));
		configurations.add(new JplagBooleanConfiguration("vd", false, "Details about each submission output"));
		configurations.add(new JplagBooleanConfiguration("d", false, "Non-parsable files will be stored"));
		configurations.add(log);
		configurations.add(results);
		configurations.add(sources);
		return configurations;
	}

	@Override
	protected Boolean hasAllConfigurations() {
		return results.getValue() != null && log.getValue() != null && sources.getValue() != null;
	}

	public JplagConfigurations sensitivity(Integer value) {
		ensureThat(value != null);
		ensureThat(value > 0);
		sensitivity.setValue(value);
		return this;
	}

	public JplagConfigurations results(String value) {
		ensureThat(value != null);
		results.setValue(value);
		log.setValue(new File(value, "log.txt").getPath());
		return this;
	}

	public JplagConfigurations sources(String value) {
		ensureThat(value != null);
		sources.setValue(value);
		return this;
	}

	public String results() {
		return results.getValue();
	}

}
