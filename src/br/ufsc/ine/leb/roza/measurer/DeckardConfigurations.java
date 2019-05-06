package br.ufsc.ine.leb.roza.measurer;

import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.support.configuration.AbstractConfigurations;
import br.ufsc.ine.leb.roza.support.configuration.Configuration;
import br.ufsc.ine.leb.roza.support.configuration.Configurations;

public class DeckardConfigurations extends AbstractConfigurations implements Configurations {

	@Override
	public List<Configuration> getAll() {
		List<Configuration> configurations = new LinkedList<>();
		configurations.add(new DeckardIntegerConfiguration("MIN_TOKES", 1, "Minimum token count to suppress vectors for small sub-trees"));
		configurations.add(new DeckardIntegerConfiguration("STRIDE", 0, "Width of the sliding window and how far it moves in each step"));
		configurations.add(new DeckardDoubleConfiguration("SIMILARITY", 1.0, "Similarity thresold based on editing distance"));
		configurations.add(new DeckardStringConfiguration("SRC_DIR", "../../execution/materializer", "The root directory containing the source files"));
		configurations.add(new DeckardStringConfiguration("FILE_PATTERN", "*.java", "Input file name pattern"));
		configurations.add(new DeckardStringConfiguration("DECKARD_DIR", "tool", "Where is the home directory of Deckard"));
		configurations.add(new DeckardStringConfiguration("VECTOR_DIR", "../../execution/measurer/vectors", "Where to output detected clone clusters"));
		configurations.add(new DeckardStringConfiguration("CLUSTER_DIR", "../../execution/measurer/cluster", "Where to output detected clone clusters"));
		configurations.add(new DeckardStringConfiguration("TIME_DIR", "../../execution/measurer/times", "Where to output timing/debugging info"));
		configurations.add(new DeckardStringConfiguration("VGEN_EXEC", "tool/src/main/jvecgen", "Where is the vector generator"));
		configurations.add(new DeckardStringConfiguration("GROUPING_EXEC", "tool/src/vgen/vgrouping/runvectorsort", "How to divide the vectors into groups"));
		configurations.add(new DeckardStringConfiguration("CLUSTER_EXEC", "tool/src/lsh/bin/enumBuckets", "Where is the LSH for vector clustering"));
		configurations.add(new DeckardStringConfiguration("QUERY_EXEC", "tool/src/lsh/bin/queryBuckets", "Where is the LSH for vector querying"));
		configurations.add(new DeckardStringConfiguration("POSTPRO_EXEC", "tool/scripts/clonedetect/post_process_groupfile", "How to post process clone groups"));
		configurations.add(new DeckardIntegerConfiguration("GROUPING_S", 1, "The maximal vector size for the first group"));
		configurations.add(new DeckardIntegerConfiguration("MAX_PROC", 1, "The maximal number of processes to be used"));
		return configurations;
	}

}
