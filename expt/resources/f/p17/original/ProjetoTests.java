package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import src.Ocorrencia;
import src.Projeto;

public class ProjetoTests {
	Projeto projeto;
	
	@Before
	public void setup() {
		projeto = new Projeto("Projeto");
	}
	
	@Test
	public void criaProjeto() throws Exception {
		assertEquals("Projeto", projeto.getNome());
	}
	
	@Test
	public void adicionaOcorrenciaA() throws Exception {
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A", Ocorrencia.Tipos.Tarefa);
		projeto.adicionaOcorrencia(ocorrenciaA);
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
	}
	
	@Test
	public void adicionaOcorrenciaB() throws Exception {
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia B", Ocorrencia.Tipos.Tarefa);
		projeto.adicionaOcorrencia(ocorrenciaB);
		assertTrue(projeto.temOcorrencia(ocorrenciaB));
	}
	
	@Test
	public void adicionaOcorrenciaAeB() throws Exception {
		ArrayList<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A", Ocorrencia.Tipos.Tarefa);
		projeto.adicionaOcorrencia(ocorrenciaA);
		ocorrencias.add(ocorrenciaA);
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia B", Ocorrencia.Tipos.Tarefa);
		projeto.adicionaOcorrencia(ocorrenciaB);
		ocorrencias.add(ocorrenciaB);
		assertTrue(projeto.getListaOcorrencias().equals(ocorrencias));
	}
}
