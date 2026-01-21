import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void responsavelEPrioridadeNaoModificaveisEmOcorrenciasConcluidas() {
		Empresa empresa = new Empresa("Gerenciador de Ocorrências");
		Projeto projeto = empresa.criarProjeto("TDD");
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.MELHORIA, Prioridade.ALTA);
		Funcionario responsavel = empresa.criarFuncionario("Fabiano Manschein");
		Funcionario novoResponsavel = empresa.criarFuncionario("Leonardo Molinari");
		empresa.atribuirFuncionarioParaProjeto(responsavel, projeto);
		empresa.atribuirFuncionarioParaProjeto(novoResponsavel, projeto);
		projeto.atribuirResponsavelParaOcorrencia(ocorrencia, responsavel);
		responsavel.concluirOcorrencia(ocorrencia);
		boolean sucessoAlterarResponsavel = projeto.atribuirResponsavelParaOcorrencia(ocorrencia, novoResponsavel);
		boolean sucessoAlterarPrioridade = ocorrencia.setPrioridade(Prioridade.BAIXA);
		assertThat("Foi possível alterar o responsável de uma ocorrência concluída!", sucessoAlterarResponsavel, equalTo(false));
		assertThat("Foi possível alterar a prioridade de uma ocorrência concluída!", sucessoAlterarPrioridade, equalTo(false));
	}
}
