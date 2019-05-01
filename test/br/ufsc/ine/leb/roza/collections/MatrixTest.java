package br.ufsc.ine.leb.roza.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;

public class MatrixTest {

	@Test
	void zeroTestCaseMaterializations() throws Exception {
		List<TestCaseMaterialization> materializations = Arrays.asList();

		MatrixValueFactory<TestCaseMaterialization, Intersector> factory = new MatrixTestCaseMaterializationIntersectorValueFactory();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new MatrixTestCaseMaterializationToStringConverter();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);

		assertEquals(0, matrix.getPairs().size());
	}

	@Test
	void oneTestCaseMaterializations() throws Exception {
		TestCase testCase = new TestCase("test", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("Materialization.java"), 10, testCase);
		List<TestCaseMaterialization> materializations = Arrays.asList(materialization);

		MatrixTestCaseMaterializationIntersectorValueFactory factory = new MatrixTestCaseMaterializationIntersectorValueFactory();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new MatrixTestCaseMaterializationToStringConverter();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);

		assertEquals(1, matrix.getPairs().size());
		assertEquals(BigDecimal.ONE, matrix.getPair(materialization.getAbsoluteFilePath(), materialization.getAbsoluteFilePath()).evaluate());
	}

	@Test
	void twoTestCaseMaterializationsWithDefaultValues() throws Exception {
		TestCase testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materializationA = new TestCaseMaterialization(new File("MaterializationA.java"), 10, testCaseA);
		TestCaseMaterialization materializationB = new TestCaseMaterialization(new File("MaterializationB.java"), 10, testCaseB);
		List<TestCaseMaterialization> materializations = Arrays.asList(materializationA, materializationB);

		MatrixTestCaseMaterializationIntersectorValueFactory factory = new MatrixTestCaseMaterializationIntersectorValueFactory();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new MatrixTestCaseMaterializationToStringConverter();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);

		assertEquals(4, matrix.getPairs().size());
		assertEquals(BigDecimal.ONE, matrix.getPair(materializationA.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath()).evaluate());
		assertEquals(BigDecimal.ZERO, matrix.getPair(materializationA.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath()).evaluate());
		assertEquals(BigDecimal.ZERO, matrix.getPair(materializationB.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath()).evaluate());
		assertEquals(BigDecimal.ONE, matrix.getPair(materializationB.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath()).evaluate());
	}

	@Test
	void twoTestCaseMaterializationsSettingValues() throws Exception {
		TestCase testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materializationA = new TestCaseMaterialization(new File("MaterializationA.java"), 10, testCaseA);
		TestCaseMaterialization materializationB = new TestCaseMaterialization(new File("MaterializationB.java"), 10, testCaseB);
		List<TestCaseMaterialization> materializations = Arrays.asList(materializationA, materializationB);

		Intersector intersectorAB = new Intersector(10);
		Intersector intersectorBA = new Intersector(10);
		intersectorAB.addSegment(1, 4);
		intersectorBA.addSegment(5, 10);

		MatrixTestCaseMaterializationIntersectorValueFactory factory = new MatrixTestCaseMaterializationIntersectorValueFactory();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new MatrixTestCaseMaterializationToStringConverter();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);

		matrix.setValue(materializationA.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath(), intersectorAB);
		matrix.setValue(materializationB.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath(), intersectorBA);

		assertEquals(4, matrix.getPairs().size());
		assertEquals(BigDecimal.ONE, matrix.getPair(materializationA.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath()).evaluate());
		assertEquals(new BigDecimal("0.4"), matrix.getPair(materializationA.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath()).evaluate());
		assertEquals(new BigDecimal("0.6"), matrix.getPair(materializationB.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath()).evaluate());
		assertEquals(BigDecimal.ONE, matrix.getPair(materializationB.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath()).evaluate());
	}

}
