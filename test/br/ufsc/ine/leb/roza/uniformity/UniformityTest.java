
package br.ufsc.ine.leb.roza.uniformity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Field;
import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;
import br.ufsc.ine.leb.roza.materializer.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.measurer.SimilarityMeasurer;

public class UniformityTest {
    private SimilarityMeasurer<TestClass> measurer;
    private TestCaseMaterializer<TestClass> materializer;

    @BeforeEach
    void setup() {
        materializer = new ManyTestCasePerClassTestCaseMaterializer("execution/materializer");
        measurer = new LongoUniformityMeasurer();
    }

    @Test
    public void sameClassWithAField() throws Exception {
        Field field = new Field("Sut", "sut");
        Statement inicializationStatement = new Statement("sut = new Sut();");
        Statement assertStatement = new Statement("assertEquals(0, sut.get(0));");
        SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(inicializationStatement));
        TestMethod testMethod = new TestMethod("test", Arrays.asList(assertStatement));
        TestClass testClass = new TestClass("ExampleTest", Arrays.asList(field), Arrays.asList(setupMethod),
                Arrays.asList(testMethod));
        MaterializationReport<TestClass> materializations = materializer.materialize(Arrays.asList(testClass));
        SimilarityReport report = measurer.measure(materializations);

        assertEquals(1, report.getAssessments().size());
        assertEquals(BigDecimal.ZERO, report.getAssessments().get(0).getScore());
        assertEquals(testClass, report.getAssessments().get(0).getSource());
        assertEquals(testClass, report.getAssessments().get(0).getTarget());
    }
}

