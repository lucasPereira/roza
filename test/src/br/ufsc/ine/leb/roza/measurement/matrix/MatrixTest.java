package br.ufsc.ine.leb.roza.measurement.matrix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.measurement.intersector.Intersector;
import br.ufsc.ine.leb.roza.measurement.matrix.simian.SimianMatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.measurement.matrix.simian.SimianMatrixValueFactory;

class MatrixTest {

	@Test
	void zeroTestCaseMaterializations() {
		List<TestCaseMaterialization> materializations = List.of();

		MatrixValueFactory<TestCaseMaterialization, Intersector> factory = new SimianMatrixValueFactory();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new SimianMatrixElementToKeyConverter();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);

		assertEquals(0, matrix.getPairs().size());
	}

	@Test
	void oneTestCaseMaterializations() {
		TestCase testCase = new TestCase("test", List.of(), List.of());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("Materialization.java"), 10, testCase);
		List<TestCaseMaterialization> materializations = List.of(materialization);

		SimianMatrixValueFactory factory = new SimianMatrixValueFactory();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new SimianMatrixElementToKeyConverter();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);

		assertEquals(1, matrix.getPairs().size());
		assertEquals(BigDecimal.ONE, matrix.get(materialization.getAbsoluteFilePath(), materialization.getAbsoluteFilePath()).evaluate());
	}

	@Test
	void twoTestCaseMaterializationsWithDefaultValues() {
		TestCase testCaseA = new TestCase("testA", List.of(), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(), List.of());
		TestCaseMaterialization materializationA = new TestCaseMaterialization(new File("MaterializationA.java"), 10, testCaseA);
		TestCaseMaterialization materializationB = new TestCaseMaterialization(new File("MaterializationB.java"), 10, testCaseB);
		List<TestCaseMaterialization> materializations = List.of(materializationA, materializationB);

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
	void twoTestCaseMaterializationsSettingValues() {
		TestCase testCaseA = new TestCase("testA", List.of(), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(), List.of());
		TestCaseMaterialization materializationA = new TestCaseMaterialization(new File("MaterializationA.java"), 10, testCaseA);
		TestCaseMaterialization materializationB = new TestCaseMaterialization(new File("MaterializationB.java"), 10, testCaseB);
		Matrix<TestCaseMaterialization, String, Intersector> matrix = getTestCaseMaterializationStringIntersectorMatrix(materializationA, materializationB);

		assertEquals(4, matrix.getPairs().size());
		assertEquals(BigDecimal.ONE, matrix.get(materializationA.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath()).evaluate());
		assertEquals(new BigDecimal("0.4"), matrix.get(materializationA.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath()).evaluate());
		assertEquals(new BigDecimal("0.6"), matrix.get(materializationB.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath()).evaluate());
		assertEquals(BigDecimal.ONE, matrix.get(materializationB.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath()).evaluate());
	}

	private static Matrix<TestCaseMaterialization, String, Intersector> getTestCaseMaterializationStringIntersectorMatrix(TestCaseMaterialization materializationA, TestCaseMaterialization materializationB) {
		List<TestCaseMaterialization> materializations = List.of(materializationA, materializationB);

		Intersector intersectorAB = new Intersector(10);
		Intersector intersectorBA = new Intersector(10);
		intersectorAB.addSegment(1, 4);
		intersectorBA.addSegment(5, 10);

		SimianMatrixValueFactory factory = new SimianMatrixValueFactory();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new SimianMatrixElementToKeyConverter();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);

		matrix.set(materializationA.getAbsoluteFilePath(), materializationB.getAbsoluteFilePath(), intersectorAB);
		matrix.set(materializationB.getAbsoluteFilePath(), materializationA.getAbsoluteFilePath(), intersectorBA);
		return matrix;
	}

}
