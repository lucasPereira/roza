package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import src.Funcionario;
import src.Ocorrencia;
import src.Projeto;

public class OcorrenciaTests {
	Ocorrencia ocorrencia;
	
	@Before
	public void setup() {
		ocorrencia = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
	}
	
	@Test
	public void criaOcorrencia() throws Exception {
		assertEquals("Ocorrencia", ocorrencia.getNome());
	}
	
	@Test
	public void unicIdTest() throws Exception {
		Ocorrencia ocorrencia1 = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrencia2 = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrencia3 = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertNotEquals(ocorrencia1.getID(), ocorrencia2.getID());
		assertNotEquals(ocorrencia1.getID(), ocorrencia3.getID());
		assertNotEquals(ocorrencia3.getID(), ocorrencia2.getID());
	}
	
	@Test
	public void adicionaResumo() throws Exception {
		ocorrencia.adicionaResumo("Resumo da ocorrencia");
		assertEquals("Resumo da ocorrencia", ocorrencia.getResumo());
	}
	
	@Test
	public void verificaResponsavelTest() throws Exception {
		Funcionario rafael = new Funcionario("Rafael");
		Projeto projeto = new Projeto("Projeto");
		rafael.adicionaOcorrencia(ocorrencia, projeto);
		assertEquals(ocorrencia.getResposavel().getNome(), rafael.getNome());
	}
	
	@Test
	public void verificaResponsavel2Test() throws Exception {
		Funcionario lucas = new Funcionario("Lucas");
		Projeto projeto = new Projeto("Projeto");
		lucas.adicionaOcorrencia(ocorrencia, projeto);
		assertEquals(ocorrencia.getResposavel().getNome(), lucas.getNome());
	}
	
	@Test
	public void VerificaPrioridadeMediaTest() throws Exception {
		assertEquals(Ocorrencia.Prioridades.Media, ocorrencia.getPrioridade());
	}
	
	@Test
	public void mudaPrioridadeAltaTest() throws Exception {
		ocorrencia.setPrioridade(Ocorrencia.Prioridades.Alta);
		assertEquals(Ocorrencia.Prioridades.Alta, ocorrencia.getPrioridade());
	}
	
	@Test
	public void mudaPrioridadeBaixaTest() throws Exception {
		ocorrencia.setPrioridade(Ocorrencia.Prioridades.Baixa);
		assertEquals(Ocorrencia.Prioridades.Baixa, ocorrencia.getPrioridade());		
	}
	
	@Test
	public void mudaPrioridadeBaixaDepoisMediaTest() throws Exception {
		ocorrencia.setPrioridade(Ocorrencia.Prioridades.Baixa);
		assertEquals(Ocorrencia.Prioridades.Baixa, ocorrencia.getPrioridade());
		ocorrencia.setPrioridade(Ocorrencia.Prioridades.Media);
		assertEquals(Ocorrencia.Prioridades.Media, ocorrencia.getPrioridade());		
	}
	
	@Test
	public void VerificaTipoTarefaTest() throws Exception {
		Ocorrencia ocorrencia1 = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertEquals(Ocorrencia.Tipos.Tarefa, ocorrencia1.getTipo());
	}
	
	@Test
	public void VerificaTipoBugTest() throws Exception {
		Ocorrencia ocorrencia1 = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Bug);
		assertEquals(Ocorrencia.Tipos.Bug, ocorrencia1.getTipo());
	}
	
	@Test
	public void VerificaTipoMelhoriaTest() throws Exception {
		Ocorrencia ocorrencia1 = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Melhoria);
		assertEquals(Ocorrencia.Tipos.Melhoria, ocorrencia1.getTipo());
	}
}
