import org.junit.Test;

public class RefactoredTestClass15 {

	@Test()
	public void verificaResponsavel2Test() {
		Ocorrencia ocorrencia = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Funcionario lucas = new Funcionario("Lucas");
		Projeto projeto = new Projeto("Projeto");
		lucas.adicionaOcorrencia(ocorrencia, projeto);
		assertEquals(ocorrencia.getResposavel().getNome(), lucas.getNome());
	}
}
