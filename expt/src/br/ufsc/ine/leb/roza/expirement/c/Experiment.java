package br.ufsc.ine.leb.roza.expirement.c;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.clustering.TestCaseClusterer;
import br.ufsc.ine.leb.roza.clustering.dendrogram.AverageLinkage;
import br.ufsc.ine.leb.roza.clustering.dendrogram.BiggestClusterReferee;
import br.ufsc.ine.leb.roza.clustering.dendrogram.CompleteLinkage;
import br.ufsc.ine.leb.roza.clustering.dendrogram.DendogramTestCaseClusterer;
import br.ufsc.ine.leb.roza.clustering.dendrogram.InsecureReferee;
import br.ufsc.ine.leb.roza.clustering.dendrogram.LevelBasedCriteria;
import br.ufsc.ine.leb.roza.clustering.dendrogram.Linkage;
import br.ufsc.ine.leb.roza.clustering.dendrogram.Referee;
import br.ufsc.ine.leb.roza.clustering.dendrogram.SimilarityBasedCriteria;
import br.ufsc.ine.leb.roza.clustering.dendrogram.SingleLinkage;
import br.ufsc.ine.leb.roza.clustering.dendrogram.SmallestClusterReferee;
import br.ufsc.ine.leb.roza.clustering.dendrogram.TestsPerClassCriteria;
import br.ufsc.ine.leb.roza.clustering.dendrogram.ThresholdCriteria;
import br.ufsc.ine.leb.roza.extraction.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.loading.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.materialization.Junit4WithAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.measurement.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.parsing.Junit4TestClassParser;
import br.ufsc.ine.leb.roza.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.selection.JavaExtensionTextFileSelector;
import br.ufsc.ine.leb.roza.selection.TextFileSelector;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.utils.MathUtils;

public class Experiment {

	public static void main(String[] args) {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		TextFileLoader loader = new RecursiveTextFileLoader("expt/resources/c");
		TextFileSelector selector = new JavaExtensionTextFileSelector();
		TestClassParser parser = new Junit4TestClassParser();
		TestCaseExtractor extractor = new Junit4TestCaseExtractor();
		TestCaseMaterializer materializer = new Junit4WithAssertionsTestCaseMaterializer("main/exec/materializer");
		SimilarityMeasurer measurer = new LccssSimilarityMeasurer();
		List<TextFile> files = loader.load();
		List<TextFile> selected = selector.select(files);
		List<TestClass> classes = parser.parse(selected);
		List<TestCase> tests = extractor.extract(classes);
		MaterializationReport materialization = materializer.materialize(tests);
		SimilarityReport report = measurer.measure(materialization);

		List<Linkage> linkages = createLinkages(report);
		List<Referee> referees = createReferees();
		List<ThresholdCriteria> criterias = createThresholdCriterias();
		execute(linkages, referees, criterias, report);
	}

	private static List<Linkage> createLinkages(SimilarityReport report) {
		SingleLinkage single = new SingleLinkage(report);
		CompleteLinkage complete = new CompleteLinkage(report);
		AverageLinkage average = new AverageLinkage(report);
		List<Linkage> linkages = Arrays.asList(single, complete, average);
		return linkages;
	}

	private static List<Referee> createReferees() {
		InsecureReferee insecure = new InsecureReferee();
		BiggestClusterReferee biggest = new BiggestClusterReferee();
		SmallestClusterReferee smallest = new SmallestClusterReferee();
		List<Referee> referees = Arrays.asList(insecure, biggest, smallest);
		return referees;
	}

	private static List<ThresholdCriteria> createThresholdCriterias() {
		List<ThresholdCriteria> criterias = new LinkedList<ThresholdCriteria>();
		addLevelBasedCriteria(criterias);
		addSimilarityBasedCriteria(criterias);
		addTestPerClassCriteria(criterias);
		return criterias;
	}

	private static void addLevelBasedCriteria(List<ThresholdCriteria> criterias) {
		for (Integer index = 1; index <= 46; index++) {
			ThresholdCriteria level = new LevelBasedCriteria(index);
			criterias.add(level);
		}
	}

	private static void addSimilarityBasedCriteria(List<ThresholdCriteria> criterias) {
		criterias.add(new SimilarityBasedCriteria(BigDecimal.ZERO));
		for (Integer index = 1; index <= 10; index++) {
			BigDecimal degree = new MathUtils().oneOver(index);
			ThresholdCriteria similarity = new SimilarityBasedCriteria(degree);
			criterias.add(similarity);
		}
	}

	private static void addTestPerClassCriteria(List<ThresholdCriteria> criterias) {
		for (Integer index = 1; index <= 46; index++) {
			ThresholdCriteria level = new TestsPerClassCriteria(index);
			criterias.add(level);
		}
	}

	private static void execute(List<Linkage> linkages, List<Referee> referees, List<ThresholdCriteria> criterias,
			SimilarityReport report) {
		for (Linkage linkage : linkages) {
			for (Referee referee : referees) {
				for (ThresholdCriteria criteria : criterias) {
					TestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
					Set<Cluster> clusters = clusterer.cluster(report);
					System.out.println(String.format("%s %s %s %s", linkage, referee, criteria, clusters.size()));
				}
			}
		}
	}

}
