import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void ocorrênciaSòPodeSerCompletadaPeloResponsável() {
		Empresa empresa = new Empresa("Gerenciador de Ocorrências");
		Projeto projeto = empresa.criarProjeto("TDD");
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.TAREFA, Prioridade.ALTA);
		Funcionario responsavel = empresa.criarFuncionario("Fabiano Manschein");
		Funcionario funcionarioQualquer = empresa.criarFuncionario("Leonardo Molinari");
		empresa.atribuirFuncionarioParaProjeto(responsavel, projeto);
		empresa.atribuirFuncionarioParaProjeto(funcionarioQualquer, projeto);
		projeto.atribuirResponsavelParaOcorrencia(ocorrencia, responsavel);
		boolean sucesso = funcionarioQualquer.concluirOcorrencia(ocorrencia);
		assertThat(ocorrencia.getEstado(), equalTo(Estado.ABERTA));
		assertThat(sucesso, equalTo(false));
	}
}
