import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void atribuirResponsavelParaOcorrencia() {
		Empresa empresa = new Empresa("Gerenciador de Ocorrências");
		Projeto projeto = empresa.criarProjeto("TDD");
		Funcionario funcionario = empresa.criarFuncionario("Fabiano Manschein");
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.MELHORIA, Prioridade.ALTA);
		Boolean sucesso = projeto.atribuirResponsavelParaOcorrencia(ocorrencia, funcionario);
		assertThat(ocorrencia.getResponsavel(), equalTo(funcionario));
		assertThat(funcionario.getListaDeOcorrencias(), hasItem(ocorrencia));
		assertThat(funcionario.getListaDeOcorrencias().size(), equalTo(1));
		assertThat(sucesso, equalTo(true));
	}
}
