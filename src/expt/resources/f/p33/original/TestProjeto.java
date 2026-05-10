package tests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.NomeVazio;
import tdd_ocorrencias.Empresa;
import tdd_ocorrencias.Projeto;

public class TestProjeto {
	Empresa empresa1;
	Projeto projeto1;
	
	@BeforeEach
	public void setUp() throws NomeVazio {
		empresa1 = new Empresa("Empresa1");
		projeto1 = new Projeto("Projeto1", empresa1);
	}
	
	@Test
	void nomeDoProjetoEDaEmpresa() throws Exception {
		assertEquals("Projeto1", projeto1.getNome());
		assertEquals(empresa1, projeto1.getEmpresa());
	}
	
	@Test
	void nomeVazioProjeto() throws NomeVazio {
		assertThrows(NomeVazio.class, () -> new Projeto("", empresa1));
	}
	
	@Test
	void inserirProjetoNaEmpresa() throws Exception {
		assertTrue(empresa1.getProjetos().contains(projeto1));
	}
	
	@Test
	void inserirSegundoProjetoNaEmpresa() throws Exception {
		Projeto projeto2 = new Projeto("Projeto2", empresa1);
		assertTrue(empresa1.getProjetos().contains(projeto2));
	}
}
