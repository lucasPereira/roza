import org.junit.Test;

public class RefactoredTestClass16 {

	@Test()
	public void verificaResponsavelTest() {
		Ocorrencia ocorrencia = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Funcionario rafael = new Funcionario("Rafael");
		Projeto projeto = new Projeto("Projeto");
		rafael.adicionaOcorrencia(ocorrencia, projeto);
		assertEquals(ocorrencia.getResposavel().getNome(), rafael.getNome());
	}
}
