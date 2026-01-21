import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void funcionarioSemOcorrencias() {
		assertEquals(0, funcionario.getOcorrencias().size());
	}
}
