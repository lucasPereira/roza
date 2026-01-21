import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void adicionaOcorrenciaA() {
		Projeto projeto = new Projeto("Projeto");
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A", Ocorrencia.Tipos.Tarefa);
		projeto.adicionaOcorrencia(ocorrenciaA);
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
	}
}
