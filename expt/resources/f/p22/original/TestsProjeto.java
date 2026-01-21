package testes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import src.Ocorrencia;
import src.Projeto;

class TestsProjeto {

Projeto projeto;
	
	@Before
	public void setup() {
		projeto = new Projeto("Projeto");
	}
	
	@Test
	public void criaProjeto() throws Exception {
		assertEquals("Projeto", projeto.Nome());
	}
	
	@Test
	public void adicionaOcorrenciaA() throws Exception {
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A");
		projeto.adicionaOcorrencia(ocorrenciaA);
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
	}
	
	@Test
	public void adicionaOcorrenciaB() throws Exception {
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia B");
		projeto.adicionaOcorrencia(ocorrenciaB);
		assertTrue(projeto.temOcorrencia(ocorrenciaB));
	}

}
