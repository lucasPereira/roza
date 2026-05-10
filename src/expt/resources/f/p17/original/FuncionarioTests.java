package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import src.Funcionario;
import src.Ocorrencia;
import src.Projeto;

public class FuncionarioTests {
	Funcionario rafael;
	
	@Before
	public void setup() {
		rafael = new Funcionario("Rafael");
	}
	
	@Test
	public void criaRafael() {
		assertEquals("Rafael", rafael.getNome());
	}
	
	@Test
	public void adicionaProjeto1() throws Exception {
		Projeto projeto1 = new Projeto("Projeto 1");
		rafael.adicionaProjeto(projeto1);
		assertTrue(rafael.temProjeto(projeto1));
	}
	
	@Test
	public void adicionaProjeto2() throws Exception {
		Projeto projeto2 = new Projeto("Projeto 2");
		rafael.adicionaProjeto(projeto2);
		assertTrue(rafael.temProjeto(projeto2));
	}
	
	@Test
	public void adicionasProjetos1e2() throws Exception {
		ArrayList<Projeto> projetos = new ArrayList<Projeto>();
		Projeto projeto1 = new Projeto("Projeto 1");
		rafael.adicionaProjeto(projeto1);
		projetos.add(projeto1);
		Projeto projeto2 = new Projeto("Projeto 2");
		rafael.adicionaProjeto(projeto2);
		projetos.add(projeto2);
		assertTrue(rafael.getListaProjetos().equals(projetos));
	}
	
	@Test
	public void adicionaOcorrenciaAaoProjeto() throws Exception {
		Projeto projeto = new Projeto("Projeto");
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaA, projeto);
		assertTrue(rafael.temOcorrencia(ocorrenciaA));
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
	}
	
	@Test
	public void adicionaOcorrenciaBaoProjeto() throws Exception {
		Projeto projeto = new Projeto("Projeto");
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia B", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaB, projeto);
		assertTrue(rafael.temOcorrencia(ocorrenciaB));
		assertTrue(projeto.temOcorrencia(ocorrenciaB));
	}
	
	@Test
	public void adicionaOcorrenciaAeBaoProjeto() throws Exception {
		Projeto projeto = new Projeto("Projeto");
		ArrayList<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaA, projeto);
		ocorrencias.add(ocorrenciaA);
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia B", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaB, projeto);
		ocorrencias.add(ocorrenciaB);
		assertTrue(rafael.getListaOcorrencias().equals(ocorrencias));
		assertTrue(projeto.getListaOcorrencias().equals(ocorrencias));
	}
	
	@Test
	public void adicionaOcorrenciaAaoProjeto1eBaoProjeto2() throws Exception {
		Projeto projeto1 = new Projeto("Projeto");
		ArrayList<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaA, projeto1);
		ocorrencias.add(ocorrenciaA);
		Projeto projeto2 = new Projeto("Projeto");
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia B", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaB, projeto2);
		ocorrencias.add(ocorrenciaB);
		assertTrue(rafael.getListaOcorrencias().equals(ocorrencias));
		assertFalse(projeto1.getListaOcorrencias().equals(ocorrencias));
		assertFalse(projeto2.getListaOcorrencias().equals(ocorrencias));
	}
	
	@Test
	public void concluiOcorrenciaTest() throws Exception {
		Projeto projeto = new Projeto("Projeto");
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaA, projeto);
		assertTrue(rafael.temOcorrencia(ocorrenciaA));
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
		rafael.concluiOcorrencia(ocorrenciaA);
		assertFalse(rafael.temOcorrencia(ocorrenciaA));
		assertFalse(projeto.temOcorrencia(ocorrenciaA));
	}
	
	@Test
	public void mudaFuncionarioOcorrenciaA() throws Exception {
		Projeto projeto = new Projeto("Projeto");
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaA, projeto);
		assertTrue(rafael.temOcorrencia(ocorrenciaA));
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
		
		Funcionario lucas = rafael = new Funcionario("Lucas");
		ocorrenciaA.setResponsavel(lucas);
		assertTrue(lucas.temOcorrencia(ocorrenciaA));
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
	}
	
	@Test
	public void maximo10tarefasTest() throws Exception {
		Projeto projeto = new Projeto("Projeto");
		//1
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaA, projeto));
		//2
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaB, projeto));
		//3
		Ocorrencia ocorrenciaC = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaC, projeto));
		//4
		Ocorrencia ocorrenciaD = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaD, projeto));
		//5
		Ocorrencia ocorrenciaE = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaE, projeto));
		//6
		Ocorrencia ocorrenciaF = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaF, projeto));
		//7
		Ocorrencia ocorrenciaG = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaG, projeto));
		//8
		Ocorrencia ocorrenciaH = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaH, projeto));
		//9
		Ocorrencia ocorrenciaI = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaI, projeto));
		//10
		Ocorrencia ocorrenciaJ = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaJ, projeto));
		//11
		Ocorrencia ocorrenciaK = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertFalse(rafael.adicionaOcorrencia(ocorrenciaK, projeto));
		//CONCLUI 
		rafael.concluiOcorrencia(ocorrenciaJ);
		//10
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaK, projeto));
		//11
		Ocorrencia ocorrenciaL = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertFalse(rafael.adicionaOcorrencia(ocorrenciaL, projeto));
		//TRASFERE
		Funcionario lucas = new Funcionario("Lucas");
		ocorrenciaI.setResponsavel(lucas);
		//10
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaL, projeto));
	}	
}
