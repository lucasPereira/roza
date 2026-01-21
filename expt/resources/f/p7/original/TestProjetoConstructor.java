package tdd;

import static org.junit.Assert.*;

import org.junit.Test;

import tdd.Projeto;

public class TestProjetoConstructor {
	
	@Test
	public void newProjeto() throws Exception {
		Projeto meuProjeto = new Projeto();
		
		assertTrue(meuProjeto.getOcorrencias().isEmpty());
	}
}
