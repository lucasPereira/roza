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

public class Testes {
	private Empresa empresa;
	private Funcionario joao;
	private Projeto desenvolvimentoSite;
	
	@Before
	public void configuracao() {
		empresa = new Empresa("Empresa", 12345);
		criaFuncionarioJoao();
		criaProjetoDesenvolvimentoSite();
	}

	private void criaFuncionarioJoao() {
		joao = new Funcionario("João", 1234);
	}
	
	private void criaProjetoDesenvolvimentoSite() {
		desenvolvimentoSite = new Projeto("Desenvolvimento de Site", joao);
	}
	
	@Test
	public void criarEmpresa() {
		assertEquals("Empresa", empresa.getNome());
		assertEquals(12345, empresa.getCNPJ());
	}
	
	@Test
	public void criarFuncionarioJoao() {
		assertEquals("João", joao.getNome());
		assertEquals(1234, joao.getCPF());
	}
	
	@Test
	public void criarProjetoDesenvolvimentoSite() {
		assertEquals("Desenvolvimento de Site", desenvolvimentoSite.getNome());
		assertEquals(joao, desenvolvimentoSite.getLider());
	}
	
	@Test
	public void inserirFuncionarioNaEmpresa() {
		empresa.inserirFuncionario(joao);
		assertEquals(joao, empresa.getFuncionarios().get(0));
		assertEquals(1, empresa.getFuncionarios().size());
	}
	
	@Test
	public void inserirNovoProjetoNaEmpresa() {
		empresa.inserirProjeto(desenvolvimentoSite);
		assertEquals(desenvolvimentoSite, empresa.getProjetos().get(0));
		assertEquals(1, empresa.getProjetos().size());
	}
	
	@Test
	public void criarOcorrencia() {
		Ocorrencia ocorrencia = new Ocorrencia(001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		assertEquals(001, ocorrencia.getChave());
		assertEquals(joao, ocorrencia.getResponsavel());
		assertEquals(Prioridade.ALTA, ocorrencia.getPrioridade());
		assertEquals(TipoOcorrencia.TAREFA, ocorrencia.getTipo());
		assertEquals(true, ocorrencia.getEstadoOcorrencia());
	}
	
	@Test
	public void fecharOcorrencia() throws Exception {
		Ocorrencia ocorrencia = new Ocorrencia(002, joao, Prioridade.ALTA, TipoOcorrencia.BUG, true);
		ocorrencia.fecharOcorrencia(joao);
		assertEquals(false, ocorrencia.getEstadoOcorrencia());
	}

	@Test(expected=Exception.class)
	public void fecharOcorrenciaComFuncionarioNaoAutorizado() throws Exception {
		Ocorrencia ocorrencia = new Ocorrencia(002, joao, Prioridade.ALTA, TipoOcorrencia.BUG, true);
		Funcionario pedro = new Funcionario("Pedro", 5678);
		ocorrencia.fecharOcorrencia(pedro);
	}
	
	@Test
	public void inserirOcorrenciaNoProjeto() throws Exception {
		Ocorrencia ocorrencia = new Ocorrencia(003, joao, Prioridade.ALTA, TipoOcorrencia.MELHORIA, true);
		desenvolvimentoSite.inserirOcorrencia(ocorrencia);
		assertEquals(ocorrencia, desenvolvimentoSite.getOcorrencias().get(0));
	}
	
	@Test
	public void cadastrarResumoOcorrencia() {
		Ocorrencia ocorrencia = new Ocorrencia(004, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		ocorrencia.setResumo("Organizar equipes do projeto.");
		assertEquals("Organizar equipes do projeto.", ocorrencia.getResumo());
	}
	
	@Test(expected=Exception.class)
	public void ocorrenciaComChaveRepetida() throws Exception {
		Ocorrencia ocorrencia1 = new Ocorrencia(1, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		Ocorrencia ocorrencia2 = new Ocorrencia(2, joao, Prioridade.MEDIA, TipoOcorrencia.BUG, true);
		Ocorrencia ocorrencia3 = new Ocorrencia(1, joao, Prioridade.BAIXA, TipoOcorrencia.MELHORIA, true);
		desenvolvimentoSite.inserirOcorrencia(ocorrencia1);
		desenvolvimentoSite.inserirOcorrencia(ocorrencia2);
		desenvolvimentoSite.inserirOcorrencia(ocorrencia3);
	}
	
	@Test
	public void modificarPrioridadeOcorrenciaAberta() throws Exception {
		Ocorrencia ocorrencia = new Ocorrencia(005, joao, Prioridade.ALTA, TipoOcorrencia.BUG, true);
		ocorrencia.setPrioridade(Prioridade.BAIXA);
		assertEquals(Prioridade.BAIXA, ocorrencia.getPrioridade());
	}
	
	@Test(expected=Exception.class)
	public void modificarPrioridadeOcorrenciaFechada() throws Exception {
		Ocorrencia ocorrencia = new Ocorrencia(005, joao, Prioridade.ALTA, TipoOcorrencia.BUG, true);
		ocorrencia.fecharOcorrencia(joao);
		ocorrencia.setPrioridade(Prioridade.BAIXA);
	}
	
	@Test
	public void modificarResponsavelOcorrenciaAberta() throws Exception {
		Ocorrencia ocorrencia = new Ocorrencia(006, joao, Prioridade.ALTA, TipoOcorrencia.MELHORIA, true);
		Funcionario pedro = new Funcionario("Pedro", 5678);
		ocorrencia.setResponsavel(pedro);
		assertEquals(pedro, ocorrencia.getResponsavel());
	}
	
	@Test(expected=Exception.class)
	public void modificarResponsavelOcorrenciaFechada() throws Exception {
		Ocorrencia ocorrencia = new Ocorrencia(007, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		Funcionario pedro = new Funcionario("Pedro", 5678);
		ocorrencia.fecharOcorrencia(joao);
		ocorrencia.setResponsavel(pedro);
	}
}
