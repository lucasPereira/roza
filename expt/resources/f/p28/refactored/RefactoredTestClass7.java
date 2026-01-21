import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void verificaOcorrenciaChaveUnica() {
		String nome = "Gmail";
		Projeto projeto = new Projeto(nome);
		String resumoOcorrencia = "Problema para pesquisar email";
		int maxOcorrencias = 10;
		Funcionario funcionario = new Funcionario("Patricia Vilain", maxOcorrencias);
		Ocorrencia ocorrencia1 = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
		Ocorrencia ocorrencia2 = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
		projeto.adicionarOcorrencia(ocorrencia1);
		projeto.adicionarOcorrencia(ocorrencia2);
		assertTrue(ocorrencia1.getId() != ocorrencia2.getId());
	}
}
