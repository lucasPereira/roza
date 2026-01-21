import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private Empresa empresa;

	private Funcionario funcionario;

	private Projeto projetoTDD;

	private Projeto projetoSelenium;

	@Before()
	public void setup() {
		empresa = new Empresa("Gerenciador de Ocorrências");
		funcionario = empresa.criarFuncionario("Fabiano Manschein");
		projetoTDD = empresa.criarProjeto("TDD");
		projetoSelenium = empresa.criarProjeto("Selenium");
		empresa.atribuirFuncionarioParaProjeto(funcionario, projetoTDD);
		empresa.atribuirFuncionarioParaProjeto(funcionario, projetoSelenium);
	}

	@Test()
	public void funcionárioPodeTrabalharEmMúltiplosProjetos() {
		assertThat(projetoTDD.getListaDeFuncionarios(), hasItem(funcionario));
		assertThat(projetoTDD.getListaDeFuncionarios().size(), equalTo(1));
		assertThat(projetoSelenium.getListaDeFuncionarios(), hasItem(funcionario));
		assertThat(projetoSelenium.getListaDeFuncionarios().size(), equalTo(1));
	}

	@Test()
	public void funcionárioPodeTrabalharEmOcorrênciasDeProjetosDiferentes() {
		Ocorrencia ocorrenciaTDD = projetoTDD.criarOcorrencia("Ocorrência 01", "Resumo 01", Tipo.MELHORIA, Prioridade.ALTA);
		Ocorrencia ocorrenciaSelenium = projetoSelenium.criarOcorrencia("Ocorrência 02", "Resumo 02", Tipo.MELHORIA, Prioridade.ALTA);
		projetoTDD.atribuirResponsavelParaOcorrencia(ocorrenciaTDD, funcionario);
		projetoSelenium.atribuirResponsavelParaOcorrencia(ocorrenciaSelenium, funcionario);
		assertThat(funcionario.getListaDeOcorrencias(), hasItems(ocorrenciaTDD, ocorrenciaSelenium));
		assertThat(funcionario.getListaDeOcorrencias().size(), equalTo(2));
		assertThat(ocorrenciaTDD.getResponsavel(), equalTo(funcionario));
		assertThat(ocorrenciaSelenium.getResponsavel(), equalTo(funcionario));
	}
}
