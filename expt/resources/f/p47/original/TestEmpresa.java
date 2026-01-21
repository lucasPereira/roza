package tdd.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import tdd.model.Empresa;
import tdd.model.Projeto;

public class TestEmpresa {

	@Test
	public void testConstrutor() {
		Empresa empresa = new Empresa("Google");
		
		assertEquals(empresa.getNome(), "Google");
		assertTrue(empresa.getProjetos() != null);
		assertTrue(empresa.getFuncionarios() != null);
		assertEquals(empresa.getProjetos().size(), 0);
		assertEquals(empresa.getFuncionarios().size(), 0);
	}
	
	@Test
	public void testCreateProjeto() {
		Empresa empresa = new Empresa("Google");
		
		Projeto projeto = empresa.createProjeto("projeto1");
		
		assertEquals(projeto.getNome(), "projeto1");
		assertEquals(projeto.getEmpresa(), empresa);
		assertTrue(projeto.getFuncionarios() != null);
		assertTrue(projeto.getOcorrencias() != null);
		assertEquals(projeto.getFuncionarios().size(), 0);
		assertEquals(projeto.getOcorrencias().size(), 0);
	}
}
