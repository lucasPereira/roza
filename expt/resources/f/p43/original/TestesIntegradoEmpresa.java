package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import projeto.Empresa;
import projeto.Funcionario;
import projeto.Ocorrencia;
import projeto.Prioridade;
import projeto.Projeto;
import projeto.TipoOcorrencia;

// Classe de testes para testar as outras classes integradas diretamente na classe Empresa

public class TestesIntegradoEmpresa {
	private Empresa empresa;
	
	@Before
	public void configuracao() {
		empresa = new Empresa("Empresa", 12345);
		cadastrarFuncionarioJoao();
		cadastrarProjetoDesenvolvimentoAplicativo();
	}
	
	private void cadastrarFuncionarioJoao() {
		empresa.cadastrarFuncionario("João", 1234);
	}
	
	public void cadastrarProjetoDesenvolvimentoAplicativo() {
		Funcionario joao = empresa.getFuncionarios().get(0);
		empresa.cadastrarProjeto("Desenvolvimento Aplicativo", joao);
	}

	@Test
	public void cadastrarFuncionario() {
		Funcionario joao = empresa.getFuncionarios().get(0);
		assertEquals("João", joao.getNome());
		assertEquals(1234, joao.getCPF());
		assertEquals(1, empresa.getFuncionarios().size());
	}
	
	@Test
	public void cadastrarProjeto() {
		Funcionario joao = empresa.getFuncionarios().get(0);
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		assertEquals("Desenvolvimento Aplicativo", desenvolvimentoAplicativo.getNome());
		assertEquals(joao, desenvolvimentoAplicativo.getLider());
		assertEquals(1, empresa.getProjetos().size());
	}
	
	@Test
	public void cadastrarOcorrencia() throws Exception {
		Funcionario joao = empresa.getFuncionarios().get(0);
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		Ocorrencia ocorrencia = desenvolvimentoAplicativo.getOcorrencias().get(0);
		assertEquals(001, ocorrencia.getChave());
		assertEquals(joao, ocorrencia.getResponsavel());
		assertEquals(Prioridade.ALTA, ocorrencia.getPrioridade());
		assertEquals(TipoOcorrencia.TAREFA, ocorrencia.getTipo());
		assertEquals(true, ocorrencia.getEstadoOcorrencia());
		assertEquals(1, desenvolvimentoAplicativo.getOcorrencias().size());
	}
	
	@Test(expected=Exception.class)
	public void cadastrarOcorrenciaSemProjetoNaEmpresa() throws Exception {
		Funcionario joao = empresa.getFuncionarios().get(0);
		Projeto modelagemConceitual = new Projeto("Modelagem Conceitual", joao);
		empresa.cadastrarOcorrencia(modelagemConceitual, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
	}
	
	@Test
	public void limiteCorretoDeOcorrenciasPorFuncionario() throws Exception {
		Funcionario joao = empresa.getFuncionarios().get(0);
		empresa.cadastrarProjeto("Banco de Dados", joao);
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		Projeto bancoDeDados = empresa.getProjetos().get(1);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 1, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 2, joao, Prioridade.MEDIA, TipoOcorrencia.BUG, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 3, joao, Prioridade.BAIXA, TipoOcorrencia.MELHORIA, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 4, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 5, joao, Prioridade.MEDIA, TipoOcorrencia.MELHORIA, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 6, joao, Prioridade.BAIXA, TipoOcorrencia.BUG, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 7, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 8, joao, Prioridade.MEDIA, TipoOcorrencia.BUG, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 9, joao, Prioridade.BAIXA, TipoOcorrencia.MELHORIA, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 10, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		assertEquals(10, desenvolvimentoAplicativo.getOcorrencias().size()+bancoDeDados.getOcorrencias().size());
	}
	
	@Test(expected=Exception.class)
	public void limiteIncorretoDeOcorrenciasPorFuncionario() throws Exception {
		Funcionario joao = empresa.getFuncionarios().get(0);
		empresa.cadastrarProjeto("Banco de Dados", joao);
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		Projeto bancoDeDados = empresa.getProjetos().get(1);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 1, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 2, joao, Prioridade.MEDIA, TipoOcorrencia.BUG, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 3, joao, Prioridade.BAIXA, TipoOcorrencia.MELHORIA, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 4, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 5, joao, Prioridade.MEDIA, TipoOcorrencia.MELHORIA, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 6, joao, Prioridade.BAIXA, TipoOcorrencia.BUG, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 7, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 8, joao, Prioridade.MEDIA, TipoOcorrencia.BUG, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 9, joao, Prioridade.BAIXA, TipoOcorrencia.MELHORIA, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 10, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 11, joao, Prioridade.MEDIA, TipoOcorrencia.BUG, true);
	}
	
	@Test(expected=Exception.class)
	public void cadastrarOcorrenciaComChaveRepetida() throws Exception {
		Funcionario joao = empresa.getFuncionarios().get(0);
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 1, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 2, joao, Prioridade.MEDIA, TipoOcorrencia.BUG, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 1, joao, Prioridade.BAIXA, TipoOcorrencia.MELHORIA, true);
	}
	
	@Test
	public void modificarPrioridadeDeOcorrenciaEmProjetoCadastrado() throws Exception {
		Funcionario joao = empresa.getFuncionarios().get(0);
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.modificarPrioridadeOcorrencia(desenvolvimentoAplicativo, desenvolvimentoAplicativo.getOcorrencias().get(0), Prioridade.BAIXA);
		assertEquals(Prioridade.BAIXA, desenvolvimentoAplicativo.getOcorrencias().get(0).getPrioridade());
	}
	
	@Test
	public void fecharOcorrencia() throws Exception {
		Funcionario joao = empresa.getFuncionarios().get(0);
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.fecharOcorrencia(desenvolvimentoAplicativo.getOcorrencias().get(0), joao);
		assertEquals(false, desenvolvimentoAplicativo.getOcorrencias().get(0).getEstadoOcorrencia());
	}
	
	@Test(expected=Exception.class)
	public void modificarPrioridadeDeOcorrenciaEmProjetoNaoCadastrado() throws Exception {
		Funcionario joao = empresa.getFuncionarios().get(0);
		Projeto layoutSite = new Projeto("Layout do Site", joao);
		empresa.cadastrarOcorrencia(layoutSite, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.modificarPrioridadeOcorrencia(layoutSite, layoutSite.getOcorrencias().get(0), Prioridade.BAIXA);
	}
	
	@Test(expected=Exception.class)
	public void modificarPrioridadeDeOcorrenciaNaoCadastrada() throws Exception {
		Funcionario joao = empresa.getFuncionarios().get(0);
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		Ocorrencia ocorrencia = new Ocorrencia(005, joao, Prioridade.ALTA, TipoOcorrencia.BUG, true);
		empresa.modificarPrioridadeOcorrencia(desenvolvimentoAplicativo, ocorrencia, Prioridade.BAIXA);
	}
	
	@Test(expected=Exception.class)
	public void modificarPrioridadeDeOcorrenciaFechada() throws Exception {
		Funcionario joao = empresa.getFuncionarios().get(0);
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.fecharOcorrencia(desenvolvimentoAplicativo.getOcorrencias().get(0), joao);
		empresa.modificarPrioridadeOcorrencia(desenvolvimentoAplicativo, desenvolvimentoAplicativo.getOcorrencias().get(0), Prioridade.BAIXA);
	}
	
	@Test
	public void modificarResponsavelOcorrencia() throws Exception {
		Funcionario joao = empresa.getFuncionarios().get(0);
		empresa.cadastrarFuncionario("Pedro", 4567);
		Funcionario pedro = empresa.getFuncionarios().get(1);
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.modificarResponsavelOcorrencia(desenvolvimentoAplicativo.getOcorrencias().get(0), pedro);
		assertEquals(pedro, desenvolvimentoAplicativo.getOcorrencias().get(0).getResponsavel());
	}
	
	@Test(expected=Exception.class)
	public void modificarResponsavelOcorrenciaFechada() throws Exception {
		Funcionario joao = empresa.getFuncionarios().get(0);
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.fecharOcorrencia(desenvolvimentoAplicativo.getOcorrencias().get(0), joao);
		empresa.cadastrarFuncionario("Funcionario2", 4567);
		Funcionario pedro = empresa.getFuncionarios().get(1);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.modificarResponsavelOcorrencia(desenvolvimentoAplicativo.getOcorrencias().get(0), pedro);
	}
}
