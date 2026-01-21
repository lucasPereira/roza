import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void adicionaOcorrenciaA() {
		Projeto projeto = new Projeto("Projeto");
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A");
		projeto.adicionaOcorrencia(ocorrenciaA);
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
	}
}
