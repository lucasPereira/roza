import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void adicionaOcorrenciaB() {
		Projeto projeto = new Projeto("Projeto");
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia B");
		projeto.adicionaOcorrencia(ocorrenciaB);
		assertTrue(projeto.temOcorrencia(ocorrenciaB));
	}
}
