package br.ufsc.ine.leb.roza.measurement.matrix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.measurement.intersector.Intersector;
import br.ufsc.ine.leb.roza.measurement.matrix.simian.SimianMatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.measurement.matrix.simian.SimianMatrixValueFactory;

class MatrixTest {

	@Test
	void zeroTestCaseMaterializations() throws Exception {
		List<TestCaseMaterialization> materializations = Arrays.asList();

		MatrixValueFactory<TestCaseMaterialization, Intersector> factory = new SimianMatrixValueFactory();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new SimianMatrixElementToKeyConverter();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);

		assertEquals(0, matrix.getPairs().size());
	}

	@Test
	void oneTestCaseMaterializations() throws Exception {
		TestCase testCase = new TestCase("test", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("Materialization.java"), 10, testCase);
		List<TestCaseMaterialization> materializations = Arrays.asList(materialization);

		SimianMatrixValueFactory factory = new SimianMatrixValueFactory();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new SimianMatrixElementToKeyConverter();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);

		assertEquals(1, matrix.getPairs().size());
		assertEquals(BigDecimal.ONE, matrix.get(materialization.getAbsoluteFilePath(), materialization.getAbsoluteFilePath()).evaluate());
	}

	@Test
	void twoTestCaseMaterializationsWithDefaultValues() throws Exception {
		TestCase testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materializationA = new TestCaseMaterialization(new File("MaterializationA.java"), 10, testCaseA);
		TestCaseMaterialization materializationB = new TestCaseMaterialization(new File("MaterializationB.java"), 10, testCaseB);
		List<TestCaseMaterialization> materializations = Arrays.asList(materializationA, materializationB);

		SimianMatrixValueFactory factory = new SimianMatrixValueFactory();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new SimianMatrixElementToKeyConverter();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);

		assertEquals(4, matrix.getPairs().size());
		assertEquals(BigDecimal.ONE, matrix.get(materializationA.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath()).evaluate());
		assertEquals(BigDecimal.ZERO, matrix.get(materializationA.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath()).evaluate());
		assertEquals(BigDecimal.ZERO, matrix.get(materializationB.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath()).evaluate());
		assertEquals(BigDecimal.ONE, matrix.get(materializationB.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath()).evaluate());
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

		SimianMatrixValueFactory factory = new SimianMatrixValueFactory();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new SimianMatrixElementToKeyConverter();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);

		matrix.set(materializationA.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath(), intersectorAB);
		matrix.set(materializationB.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath(), intersectorBA);

		assertEquals(4, matrix.getPairs().size());
		assertEquals(BigDecimal.ONE, matrix.get(materializationA.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath()).evaluate());
		assertEquals(new BigDecimal("0.4"), matrix.get(materializationA.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath()).evaluate());
		assertEquals(new BigDecimal("0.6"), matrix.get(materializationB.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath()).evaluate());
		assertEquals(BigDecimal.ONE, matrix.get(materializationB.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath()).evaluate());
	}

}
