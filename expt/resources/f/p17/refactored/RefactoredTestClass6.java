import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void adicionaOcorrenciaBaoProjeto() {
		Funcionario rafael = new Funcionario("Rafael");
		Projeto projeto = new Projeto("Projeto");
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia B", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaB, projeto);
		assertTrue(rafael.temOcorrencia(ocorrenciaB));
		assertTrue(projeto.temOcorrencia(ocorrenciaB));
	}
}
