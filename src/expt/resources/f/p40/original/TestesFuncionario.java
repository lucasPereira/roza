package gerenciador;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

public class TestesFuncionario {
	
	private Empresa empresa;
	private Funcionario funcionario;

	@Before
	public void configurar() {
		empresa = new Empresa("Gerenciador de Ocorrências");
		funcionario = empresa.criarFuncionario("Fabiano Manschein");
	}
	
	public boolean atribuirOcorrenciasParaFuncionario(int quantia, Funcionario funcionario, Projeto projeto) {
		// Atribui ´quantia´ ocorrencias para o funcionario
		boolean sucesso = false;
		for (int i=0; i<quantia; i++) {
			sucesso = projeto.atribuirResponsavelParaOcorrencia(new Ocorrencia("Ocorrência", "Resumo", Tipo.MELHORIA, Prioridade.ALTA), funcionario);
		}
		return sucesso;
	}
	
	@Test
	public void verificarQuantiaCorretaDoMetodoAtribuirOcorrenciasParaFuncionario() throws Exception {
		// Verificar se o método atribui a quantia correta de ocorrencia para o funcioanrio, também garantindo que o funcionário possa ter 10 ocorrencias das quais é responsável
		Projeto projeto = empresa.criarProjeto("TDD");
		Boolean sucesso = atribuirOcorrenciasParaFuncionario(10, funcionario, projeto);
		assertThat(funcionario.getListaDeOcorrencias().size(), equalTo(10));
		assertThat(sucesso, equalTo(true));
	}
	
	@Test
	public void criarFuncionarioComConstrutor() throws Exception {
		// Verifica se o funcionario foi criado corretamente utilizando seu construtor. Listas de ocorrencia e projetos devem estar vazias
		funcionario = new Funcionario("Fabiano Manschein");
		assertThat(funcionario.getNome(), equalTo("Fabiano Manschein"));
		assertThat(funcionario.getListaDeOcorrencias().size(), equalTo(0));
		assertThat(funcionario.getListaDeProjetos().size(), equalTo(0));
	}
	
	@Test
	public void funcionarioPodeSerResponsavelPorAteDezOcorrenciasAbertas() throws Exception {
		// Um funcionario pode ser responsável por até 10 ocorrencia de uma vez
		Boolean sucesso = atribuirOcorrenciasParaFuncionario(11, funcionario, new Projeto("TDD"));
		assertThat(funcionario.getListaDeOcorrencias().size(), lessThanOrEqualTo(10));
		assertThat(sucesso, equalTo(false));
	}	
	
	@Test
	public void funcionárioPodeTrabalharEmMúltiplosProjetos() throws Exception {
		// Um funcionario pode trabalhar em múltiplos projetos
		Projeto projetoTDD = empresa.criarProjeto("TDD");
		Projeto projetoSelenium = empresa.criarProjeto("Selenium");

		empresa.atribuirFuncionarioParaProjeto(funcionario, projetoTDD);
		empresa.atribuirFuncionarioParaProjeto(funcionario, projetoSelenium);
		
		assertThat(projetoTDD.getListaDeFuncionarios(), hasItem(funcionario));
		assertThat(projetoTDD.getListaDeFuncionarios().size(), equalTo(1));
		assertThat(projetoSelenium.getListaDeFuncionarios(), hasItem(funcionario));
		assertThat(projetoSelenium.getListaDeFuncionarios().size(), equalTo(1));
	}
	
	@Test
	public void funcionárioPodeTrabalharEmOcorrênciasDeProjetosDiferentes() throws Exception {
		// Verifica que o funcionario pode ser responsavel por ocorrencias de diferentes projetos
		Projeto projetoTDD = empresa.criarProjeto("TDD");
		Projeto projetoSelenium = empresa.criarProjeto("Selenium");

		empresa.atribuirFuncionarioParaProjeto(funcionario, projetoTDD);
		empresa.atribuirFuncionarioParaProjeto(funcionario, projetoSelenium);
		
		Ocorrencia ocorrenciaTDD = projetoTDD.criarOcorrencia("Ocorrência 01", "Resumo 01", Tipo.MELHORIA, Prioridade.ALTA);
		Ocorrencia ocorrenciaSelenium = projetoSelenium.criarOcorrencia("Ocorrência 02", "Resumo 02", Tipo.MELHORIA, Prioridade.ALTA);

		projetoTDD.atribuirResponsavelParaOcorrencia(ocorrenciaTDD, funcionario);
		projetoSelenium.atribuirResponsavelParaOcorrencia(ocorrenciaSelenium, funcionario);
		
		assertThat(funcionario.getListaDeOcorrencias(), hasItems(ocorrenciaTDD, ocorrenciaSelenium));
		assertThat(funcionario.getListaDeOcorrencias().size(), equalTo(2));
		assertThat(ocorrenciaTDD.getResponsavel(), equalTo(funcionario));
		assertThat(ocorrenciaSelenium.getResponsavel(), equalTo(funcionario));
	}
	
	@Test
	public void ocorrenciaRemovidaDaListaDeOcorrenciasAoSerConcluida() throws Exception {
		// Verifica se a ocorrencia concluida é removida da lista de ocorrencias do funcionario.
		// A ocorrência deve ser mantida na lista de ocorrências do projeto para fins de histórico.
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
