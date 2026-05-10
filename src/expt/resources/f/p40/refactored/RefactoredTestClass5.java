import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void responsavelDaOcorrenciaPodeSerModificado() {
		Empresa empresa = new Empresa("Gerenciador de Ocorrências");
		Projeto projeto = empresa.criarProjeto("TDD");
		Funcionario exResponsavel = empresa.criarFuncionario("Fabiano Manschein");
		Funcionario novoResponsavel = empresa.criarFuncionario("Leonardo Molinari");
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Resumo 01", Tipo.TAREFA, Prioridade.ALTA);
		empresa.atribuirFuncionarioParaProjeto(exResponsavel, projeto);
		empresa.atribuirFuncionarioParaProjeto(novoResponsavel, projeto);
		projeto.atribuirResponsavelParaOcorrencia(ocorrencia, exResponsavel);
		projeto.atribuirResponsavelParaOcorrencia(ocorrencia, novoResponsavel);
		assertThat(ocorrencia.getResponsavel(), equalTo(novoResponsavel));
		assertThat(exResponsavel.getListaDeOcorrencias(), not(hasItem(ocorrencia)));
		assertThat(exResponsavel.getListaDeOcorrencias().size(), equalTo(0));
		assertThat(novoResponsavel.getListaDeOcorrencias(), hasItem(ocorrencia));
		assertThat(novoResponsavel.getListaDeOcorrencias().size(), equalTo(1));
	}
}
