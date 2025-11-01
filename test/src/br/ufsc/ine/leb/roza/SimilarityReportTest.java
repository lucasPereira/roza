package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.MissingPairException;
import br.ufsc.ine.leb.roza.utils.comparator.SimilarityAssessmentComparatorByScoreSourceNameAndTargetName;

class SimilarityReportTest {

	private SimilarityAssessment assessmentAA;
	private SimilarityAssessment assessmentAB;
	private SimilarityAssessment assessmentAC;
	private SimilarityAssessment assessmentBA;
	private SimilarityAssessment assessmentBB;
	private SimilarityAssessment assessmentBC;
	private SimilarityAssessment assessmentCA;
	private SimilarityAssessment assessmentCB;
	private SimilarityAssessment assessmentCC;
	private TestCase testCaseA;
	private TestCase testCaseB;
	private TestCase testCaseC;

	@BeforeEach
	void setup() {
		testCaseA = new TestCase("testA", List.of(), List.of());
		testCaseB = new TestCase("testB", List.of(), List.of());
		testCaseC = new TestCase("testC", List.of(), List.of());
		assessmentAA = new SimilarityAssessment(testCaseA, testCaseA, BigDecimal.ONE);
		assessmentAB = new SimilarityAssessment(testCaseA, testCaseB, new BigDecimal("0.2"));
		assessmentAC = new SimilarityAssessment(testCaseA, testCaseC, new BigDecimal("0.9"));
		assessmentBA = new SimilarityAssessment(testCaseB, testCaseA, new BigDecimal("0.3"));
		assessmentBB = new SimilarityAssessment(testCaseB, testCaseB, BigDecimal.ONE);
		assessmentBC = new SimilarityAssessment(testCaseB, testCaseC, new BigDecimal("0.5"));
		assessmentCA = new SimilarityAssessment(testCaseC, testCaseA, new BigDecimal("0.8"));
		assessmentCB = new SimilarityAssessment(testCaseC, testCaseB, new BigDecimal("0.5"));
		assessmentCC = new SimilarityAssessment(testCaseC, testCaseC, BigDecimal.ONE);
	}

	@Test
	void create() {
		SimilarityReport report = new SimilarityReport(List.of(assessmentAA, assessmentAB));
		report.sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());
		assertEquals(2, report.getAssessments().size());
		assertEquals(assessmentAA, report.getAssessments().get(0));
		assertEquals(assessmentAB, report.getAssessments().get(1));
	}

	@Test
	void removeReflexives() {
		SimilarityReport report = new SimilarityReport(List.of(assessmentAA, assessmentAB));
		SimilarityReport filtered = report.removeReflexives();
		assertEquals(2, report.getAssessments().size());
		assertEquals(1, filtered.getAssessments().size());
		assertEquals(assessmentAB, filtered.getAssessments().get(0));
	}

	@Test
	void removeNonReflexives() {
		SimilarityReport report = new SimilarityReport(List.of(assessmentAA, assessmentAB));
		SimilarityReport filtered = report.removeNonReflexives();
		assertEquals(2, report.getAssessments().size());
		assertEquals(1, filtered.getAssessments().size());
		assertEquals(assessmentAA, filtered.getAssessments().get(0));
	}

	@Test
	void selectSource() {
		SimilarityReport report = new SimilarityReport(List.of(assessmentBB, assessmentAA, assessmentAB, assessmentAC, assessmentBA, assessmentBC, assessmentCA, assessmentCB, assessmentCC));
		SimilarityReport filtered = report.selectSource(testCaseA);
		assertEquals(9, report.getAssessments().size());
		assertEquals(3, filtered.getAssessments().size());
		assertEquals(assessmentAA, filtered.getAssessments().get(0));
		assertEquals(assessmentAB, filtered.getAssessments().get(1));
		assertEquals(assessmentAC, filtered.getAssessments().get(2));
	}

	@Test
	void unsorted() {
		SimilarityReport unsorted = new SimilarityReport(List.of(assessmentBB, assessmentAA, assessmentAB, assessmentAC, assessmentBA, assessmentBC, assessmentCA, assessmentCB, assessmentCC));
		assertEquals(9, unsorted.getAssessments().size());
		assertEquals(assessmentBB, unsorted.getAssessments().get(0));
		assertEquals(assessmentAA, unsorted.getAssessments().get(1));
		assertEquals(assessmentAB, unsorted.getAssessments().get(2));
		assertEquals(assessmentAC, unsorted.getAssessments().get(3));
		assertEquals(assessmentBA, unsorted.getAssessments().get(4));
		assertEquals(assessmentBC, unsorted.getAssessments().get(5));
		assertEquals(assessmentCA, unsorted.getAssessments().get(6));
		assertEquals(assessmentCB, unsorted.getAssessments().get(7));
		assertEquals(assessmentCC, unsorted.getAssessments().get(8));
	}

	@Test
	void sorted() {
		SimilarityReport unsorted = new SimilarityReport(List.of(assessmentBB, assessmentAA, assessmentAB, assessmentAC, assessmentBA, assessmentBC, assessmentCA, assessmentCB, assessmentCC));
		SimilarityReport sorted = unsorted.sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());
		assertEquals(9, sorted.getAssessments().size());
		assertEquals(assessmentAA, sorted.getAssessments().get(0));
		assertEquals(assessmentBB, sorted.getAssessments().get(1));
		assertEquals(assessmentCC, sorted.getAssessments().get(2));
		assertEquals(assessmentAC, sorted.getAssessments().get(3));
		assertEquals(assessmentCA, sorted.getAssessments().get(4));
		assertEquals(assessmentBC, sorted.getAssessments().get(5));
		assertEquals(assessmentCB, sorted.getAssessments().get(6));
		assertEquals(assessmentBA, sorted.getAssessments().get(7));
		assertEquals(assessmentAB, sorted.getAssessments().get(8));
	}

	@Test
	void existingPair() {
		SimilarityReport report = new SimilarityReport(List.of(assessmentBB, assessmentAA, assessmentAB, assessmentBA));
		assertEquals(assessmentAA, report.getPair(testCaseA, testCaseA));
		assertEquals(assessmentAB, report.getPair(testCaseA, testCaseB));
		assertEquals(assessmentBA, report.getPair(testCaseB, testCaseA));
		assertEquals(assessmentBB, report.getPair(testCaseB, testCaseB));
	}

	@Test
	void nonExistentPair() {
		SimilarityReport report = new SimilarityReport(List.of(assessmentBB, assessmentAA, assessmentAB, assessmentBA));
		assertThrows(MissingPairException.class, () -> report.getPair(testCaseA, testCaseC));
		assertThrows(MissingPairException.class, () -> report.getPair(testCaseC, testCaseA));
		assertThrows(MissingPairException.class, () -> report.getPair(testCaseB, testCaseC));
		assertThrows(MissingPairException.class, () -> report.getPair(testCaseC, testCaseB));
	}

}
