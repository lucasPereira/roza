import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void projetoSemOcorrencias() {
		Integer numeroDeOcorrencias = projetoCalculadora.obterNumeroDeOcorrencias();
		assertEquals(zero, numeroDeOcorrencias);
	}
}
