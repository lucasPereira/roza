import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void ocorrenciaRemovidaDaListaDeOcorrenciasAoSerConcluida() {
		Empresa empresa = new Empresa("Gerenciador de Ocorrências");
		Funcionario funcionario = empresa.criarFuncionario("Fabiano Manschein");
		Projeto projeto = empresa.criarProjeto("TDD");
		empresa.atribuirFuncionarioParaProjeto(funcionario, projeto);
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Resumo 01", Tipo.TAREFA, Prioridade.ALTA);
		projeto.atribuirResponsavelParaOcorrencia(ocorrencia, funcionario);
		funcionario.concluirOcorrencia(ocorrencia);
		assertThat(funcionario.getListaDeOcorrencias(), not(hasItem(ocorrencia)));
		assertThat(funcionario.getListaDeOcorrencias().size(), equalTo(0));
		assertThat(projeto.getListaDeOcorrencias(), hasItem(ocorrencia));
		assertThat(projeto.getListaDeOcorrencias().size(), equalTo(1));
	}
}
