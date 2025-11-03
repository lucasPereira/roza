package br.ufsc.ine.leb.roza.expt.c;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.clustering.AverageLinkage;
import br.ufsc.ine.leb.roza.clustering.BiggestClusterReferee;
import br.ufsc.ine.leb.roza.clustering.CompleteLinkage;
import br.ufsc.ine.leb.roza.clustering.AgglomerativeHierarchicalClusteringTestCaseClusterer;
import br.ufsc.ine.leb.roza.clustering.InsecureReferee;
import br.ufsc.ine.leb.roza.clustering.LevelBasedCriterion;
import br.ufsc.ine.leb.roza.clustering.Linkage;
import br.ufsc.ine.leb.roza.clustering.Referee;
import br.ufsc.ine.leb.roza.clustering.SimilarityBasedCriterion;
import br.ufsc.ine.leb.roza.clustering.SingleLinkage;
import br.ufsc.ine.leb.roza.clustering.SmallestClusterReferee;
import br.ufsc.ine.leb.roza.clustering.TestCaseClusterer;
import br.ufsc.ine.leb.roza.clustering.TestsPerClassCriterion;
import br.ufsc.ine.leb.roza.clustering.ThresholdCriterion;
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
import br.ufsc.ine.leb.roza.utils.RozaLogger;

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
		List<ThresholdCriterion> criteria = createThresholdCriteria();
		execute(linkages, referees, criteria, report);
	}

	private static List<Linkage> createLinkages(SimilarityReport report) {
		SingleLinkage single = new SingleLinkage(report);
		CompleteLinkage complete = new CompleteLinkage(report);
		AverageLinkage average = new AverageLinkage(report);
		return List.of(single, complete, average);
	}

	private static List<Referee> createReferees() {
		InsecureReferee insecure = new InsecureReferee();
		BiggestClusterReferee biggest = new BiggestClusterReferee();
		SmallestClusterReferee smallest = new SmallestClusterReferee();
		return List.of(insecure, biggest, smallest);
	}

	private static List<ThresholdCriterion> createThresholdCriteria() {
		List<ThresholdCriterion> criteria = new LinkedList<>();
		addLevelBasedCriterion(criteria);
		addSimilarityBasedCriterion(criteria);
		addTestPerClassCriterion(criteria);
		return criteria;
	}

	private static void addLevelBasedCriterion(List<ThresholdCriterion> criteria) {
		for (int index = 1; index <= 46; index++) {
			ThresholdCriterion level = new LevelBasedCriterion(index);
			criteria.add(level);
		}
	}

	private static void addSimilarityBasedCriterion(List<ThresholdCriterion> criteria) {
		criteria.add(new SimilarityBasedCriterion(BigDecimal.ZERO));
		for (int index = 1; index <= 10; index++) {
			BigDecimal degree = new MathUtils().oneOver(index);
			ThresholdCriterion similarity = new SimilarityBasedCriterion(degree);
			criteria.add(similarity);
		}
	}

	private static void addTestPerClassCriterion(List<ThresholdCriterion> criteria) {
		for (int index = 1; index <= 46; index++) {
			ThresholdCriterion level = new TestsPerClassCriterion(index);
			criteria.add(level);
		}
	}

	private static void execute(List<Linkage> linkages, List<Referee> referees, List<ThresholdCriterion> criteria,
			SimilarityReport report) {
		for (Linkage linkage : linkages) {
			for (Referee referee : referees) {
				for (ThresholdCriterion criterion : criteria) {
					TestCaseClusterer clusterer = new AgglomerativeHierarchicalClusteringTestCaseClusterer(linkage, referee, criterion);
					Set<Cluster> clusters = clusterer.cluster(report);
					RozaLogger.getInstance().info(String.format("%s %s %s %s", linkage, referee, criterion, clusters.size()));
				}
			}
		}
	}

}
