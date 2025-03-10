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
	private Boolean isDockerMode;

	private static String SRC_DIR_PATH = "exec/materializer";
	private static String RESULTS_DIR_PATH = "exec/measurer";
	private static String VECTOR_DIR_PATH =  RESULTS_DIR_PATH + "/vectors";
	private static String CLUSTER_DIR_PATH = RESULTS_DIR_PATH + "/cluster";
	private static String TIME_DIR_PATH= RESULTS_DIR_PATH + "/times";

	public DeckardConfigurations(boolean isDockerMode) {
		this.isDockerMode = isDockerMode;
		minTokens = new DeckardIntegerConfiguration("MIN_TOKENS", 1, "Minimum token count to suppress vectors for small sub-trees");
		stride = new DeckardIntegerConfiguration("STRIDE", 0, "Width of the sliding window and how far it moves in each step");
		similarity = new DeckardDoubleConfiguration("SIMILARITY", 1.0, "Similarity thresold based on editing distance");
	}

	@Override
	public List<Configuration> getAll() {
		List<Configuration> configurations = new LinkedList<>();
		configurations.add(minTokens);
		configurations.add(stride);
		configurations.add(similarity);
		configurations.add(new DeckardStringConfiguration("FILE_PATTERN", "*.java", "Input file name pattern"));
		configurations.add(new DeckardStringConfiguration("SRC_DIR", getPath(SRC_DIR_PATH, isDockerMode), "The root directory containing the source files"));
		configurations.add(new DeckardStringConfiguration("VECTOR_DIR", getPath(VECTOR_DIR_PATH, isDockerMode), "Where to output detected clone clusters"));
		configurations.add(new DeckardStringConfiguration("CLUSTER_DIR", getPath(CLUSTER_DIR_PATH, isDockerMode), "Where to output detected clone clusters"));
		configurations.add(new DeckardStringConfiguration("TIME_DIR", getPath(TIME_DIR_PATH, isDockerMode), "Where to output timing/debugging info"));
		configurations.add(new DeckardStringConfiguration("DECKARD_DIR", "tool", "Where is the home directory of Deckard"));
		configurations.add(new DeckardStringConfiguration("VGEN_EXEC", "tool/src/main/jvecgen", "Where is the vector generator"));
		configurations.add(new DeckardStringConfiguration("GROUPING_EXEC", "tool/src/vgen/vgrouping/runvectorsort", "How to divide the vectors into groups"));
		configurations.add(new DeckardStringConfiguration("CLUSTER_EXEC", "tool/src/lsh/bin/enumBuckets", "Where is the LSH for vector clustering"));
		configurations.add(new DeckardStringConfiguration("QUERY_EXEC", "tool/src/lsh/bin/queryBuckets", "Where is the LSH for vector querying"));
		configurations.add(new DeckardStringConfiguration("POSTPRO_EXEC", "tool/scripts/clonedetect/post_process_groupfile", "How to post process clone groups"));
		configurations.add(new DeckardIntegerConfiguration("GROUPING_S", 1, "The maximal vector size for the first group"));
		configurations.add(new DeckardIntegerConfiguration("MAX_PROCS", 1, "The maximal number of processes to be used"));
		return configurations;
	}

	@Override
	protected Boolean hasAllConfigurations() {
		return true;
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

	private String getPath(String path, Boolean useRelativePath) {
		if (useRelativePath) {
			return new File("../../" + path).getPath();
		} else {
			return new File("main/" + path).getAbsolutePath();
		}
	}

	public String clusterDir() {
		return getPath(CLUSTER_DIR_PATH, false);
	}

	public Boolean isDockerMode() {
		return isDockerMode;
	}

}
