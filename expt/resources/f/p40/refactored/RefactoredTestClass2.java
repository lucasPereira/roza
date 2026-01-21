import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void concluirOcorrência() {
		Empresa empresa = new Empresa("Gerenciador de Ocorrências");
		Projeto projeto = empresa.criarProjeto("TDD");
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.TAREFA, Prioridade.ALTA);
		Funcionario funcionario = empresa.criarFuncionario("Fabiano Manschein");
		empresa.atribuirFuncionarioParaProjeto(funcionario, projeto);
		projeto.atribuirResponsavelParaOcorrencia(ocorrencia, funcionario);
		boolean sucesso = funcionario.concluirOcorrencia(ocorrencia);
		assertThat(ocorrencia.getEstado(), equalTo(Estado.CONCLUIDA));
		assertThat(sucesso, equalTo(true));
	}
}
