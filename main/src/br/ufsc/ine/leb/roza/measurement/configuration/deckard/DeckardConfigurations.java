package br.ufsc.ine.leb.roza.measurement.configuration.deckard;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.measurement.configuration.AbstractConfigurations;
import br.ufsc.ine.leb.roza.measurement.configuration.Configuration;
import br.ufsc.ine.leb.roza.measurement.configuration.Configurations;

public class DeckardConfigurations extends AbstractConfigurations implements Configurations {

	private DeckardIntegerConfiguration minTokens;
	private DeckardIntegerConfiguration stride;
	private DeckardDoubleConfiguration similarity;
	private DeckardStringConfiguration srcDir;
	private DeckardStringConfiguration vectorDir;
	private DeckardStringConfiguration clusterDir;
	private DeckardStringConfiguration timeDir;

	public DeckardConfigurations() {
		minTokens = new DeckardIntegerConfiguration("MIN_TOKENS", 1, "Minimum token count to suppress vectors for small sub-trees");
		stride = new DeckardIntegerConfiguration("STRIDE", 0, "Width of the sliding window and how far it moves in each step");
		similarity = new DeckardDoubleConfiguration("SIMILARITY", 1.0, "Similarity thresold based on editing distance");
		srcDir = new DeckardStringConfiguration("SRC_DIR", null, "The root directory containing the source files");
		vectorDir = new DeckardStringConfiguration("VECTOR_DIR", null, "Where to output detected clone clusters");
		clusterDir = new DeckardStringConfiguration("CLUSTER_DIR", null, "Where to output detected clone clusters");
		timeDir = new DeckardStringConfiguration("TIME_DIR", null, "Where to output timing/debugging info");
		srcDir("main/exec/materializer");
		results("main/exec/measurer");
	}

	@Override
	public List<Configuration> getAll() {
		List<Configuration> configurations = new LinkedList<>();
		configurations.add(minTokens);
		configurations.add(stride);
		configurations.add(similarity);
		configurations.add(srcDir);
		configurations.add(new DeckardStringConfiguration("FILE_PATTERN", "*.java", "Input file name pattern"));
		configurations.add(vectorDir);
		configurations.add(clusterDir);
		configurations.add(timeDir);
		configurations.add(new DeckardStringConfiguration("DECKARD_DIR", "tool", "Where is the home directory of Deckard"));
		configurations.add(new DeckardStringConfiguration("VGEN_EXEC", "tool/src/main/jvecgen", "Where is the vector generator"));
		configurations.add(new DeckardStringConfiguration("GROUPING_EXEC", "tool/src/vgen/vgrouping/runvectorsort", "How to divide the vectors into groups"));
		configurations.add(new DeckardStringConfiguration("CLUSTER_EXEC", "tool/src/lsh/bin/enumBuckets", "Where is the LSH for vector clustering"));
		configurations.add(new DeckardStringConfiguration("QUERY_EXEC", "tool/src/lsh/bin/queryBuckets", "Where is the LSH for vector querying"));
		configurations.add(new DeckardStringConfiguration("POSTPRO_EXEC", "tool/scripts/clonedetect/post_process_groupfile", "How to post process clone groups"));
		configurations.add(new DeckardIntegerConfiguration("GROUPING_S", 1, "The maximal vector size for the first group"));
		configurations.add(new DeckardIntegerConfiguration("MAX_PROC", 1, "The maximal number of processes to be used"));
		return configurations;
	}

	@Override
	protected Boolean hasAllConfigurations() {
		return srcDir.getValue() != null && vectorDir.getValue() != null && clusterDir.getValue() != null && timeDir.getValue() != null;
	}

	public DeckardConfigurations minTokens(Integer value) {
		ensureThat(value != null);
		ensureThat(value > 0);
		minTokens.setValue(value);
		return this;
	}

	public DeckardConfigurations stride(Integer value) {
		ensureThat(value != null);
		stride.setValue(value);
		ensureThat(value >= 0);
		return this;
	}

	public DeckardConfigurations similarity(Double value) {
		ensureThat(value != null);
		similarity.setValue(value);
		ensureThat(value >= 0);
		ensureThat(value <= 1);
		return this;
	}

	private DeckardConfigurations srcDir(String value) {
		ensureThat(value != null);
		srcDir.setValue(new File(value).getAbsolutePath());
		return this;
	}

	private DeckardConfigurations results(String value) {
		ensureThat(value != null);
		vectorDir.setValue(new File(value, "vectors").getAbsolutePath());
		clusterDir.setValue(new File(value, "cluster").getAbsolutePath());
		timeDir.setValue(new File(value, "times").getAbsolutePath());
		return this;
	}

	public String clusterDir() {
		return clusterDir.getValue();
	}

}
