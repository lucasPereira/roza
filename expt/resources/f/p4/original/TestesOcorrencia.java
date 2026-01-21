package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import projeto.Estados;
import projeto.Funcionario;
import projeto.Ocorrencia;
import projeto.Prioridades;
import projeto.Tipos;

public class TestesOcorrencia {
	
	Ocorrencia ocorrencia;
	Funcionario joao;
	
	@Before
	public void inicializacao() {
		joao = new Funcionario();
		ocorrencia = new Ocorrencia(joao);
	}
	
	@Test
	public void chaveUnicaDuasOcorrencias() throws Exception {
		Ocorrencia outraOcorrencia = new Ocorrencia(joao);
		assertTrue(ocorrencia.obtemChave() < outraOcorrencia.obtemChave());
	}
	
	@Test
	public void chaveSequencialDuasOcorrencias() throws Exception {
		Ocorrencia outraOcorrencia = new Ocorrencia(joao);
		assertNotEquals(ocorrencia.obtemChave(), outraOcorrencia.obtemChave());
	}
	
	@Test
	public void estadoInicialABERTA() throws Exception {
		assertEquals(Estados.ABERTA, ocorrencia.obtemEstado());
	}
	
	@Test
	public void estadoAlteradoCOMLPETADA() throws Exception {
		ocorrencia.alterarEstado(Estados.COMPLETADA);
		assertEquals(Estados.COMPLETADA, ocorrencia.obtemEstado());
	}
	
	@Test
	public void estadoAlteradoCOMLPETADADepoisABERTA() throws Exception {
		ocorrencia.alterarEstado(Estados.COMPLETADA);
		ocorrencia.alterarEstado(Estados.ABERTA);
		assertEquals(Estados.ABERTA, ocorrencia.obtemEstado());
	}
	
	@Test
	public void tipoTarefa() throws Exception {
		ocorrencia.alterarTipo(Tipos.TAREFA);
		assertEquals(Tipos.TAREFA, ocorrencia.obterTipo());
	}
	
	@Test
	public void tipoBug() throws Exception {
		ocorrencia.alterarTipo(Tipos.BUG);
		assertEquals(Tipos.BUG, ocorrencia.obterTipo());
	}
	
	@Test
	public void tipoMelhoria() throws Exception {
		ocorrencia.alterarTipo(Tipos.MELHORIA);
		assertEquals(Tipos.MELHORIA, ocorrencia.obterTipo());
	}
	
	@Test
	public void prioridadeBaixa() throws Exception {
		ocorrencia.alterarPrioridade(Prioridades.BAIXA);
		assertEquals(Prioridades.BAIXA, ocorrencia.obterPrioridade());
	}
	
	@Test
	public void prioridadeMedia() throws Exception {
		ocorrencia.alterarPrioridade(Prioridades.MEDIA);
		assertEquals(Prioridades.MEDIA, ocorrencia.obterPrioridade());
	}
	
	@Test
	public void prioridadeAlta() throws Exception {
		ocorrencia.alterarPrioridade(Prioridades.ALTA);
		assertEquals(Prioridades.ALTA, ocorrencia.obterPrioridade());
	}
	
	@Test(expected=Exception.class)
	public void alteraPrioridadeComOcorrenciaCompletada() throws Exception {
		ocorrencia.alterarPrioridade(Prioridades.BAIXA);
		ocorrencia.alterarEstado(Estados.COMPLETADA);
		
		ocorrencia.alterarPrioridade(Prioridades.ALTA);
	}
	
	@Test
	public void defineResumo() throws Exception {
		ocorrencia.defineResumo("Este é o resumo da primeira ocorrência.");
		assertEquals("Este é o resumo da primeira ocorrência.", ocorrencia.obterResumo());
	}
	
	@Test
	public void joaoResponsavel() throws Exception {
		assertEquals(joao.obtemChave(), ocorrencia.obtemResponsavel().obtemChave());
	}
	
	@Test
	public void alteraResponsavel() throws Exception {
		Funcionario carlos = new Funcionario();
		ocorrencia.alterarResponsavel(carlos);
		
		assertEquals(carlos, ocorrencia.obtemResponsavel());
	}
	
	@Test(expected=Exception.class)
	public void alteraResponsavelComOcorrenciaCompletada() throws Exception {
		Funcionario carlos = new Funcionario();
		ocorrencia.alterarResponsavel(carlos);
		ocorrencia.alterarEstado(Estados.COMPLETADA);
		
		ocorrencia.alterarResponsavel(joao);
	}
	
}
