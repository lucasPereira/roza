package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Main.Empresa;
import Main.Funcionario;
import Main.Projeto;

public class TesteProjeto {

	Empresa empresa;
	Funcionario juninho;
	
	@BeforeEach
	public void beforeEach() throws Exception {
		empresa = Empresa.Instance();
		empresa.Reset();
		juninho = new Funcionario("Juninho", "0000007");
		empresa.addFuncionario(juninho);
	}
	
	
	@Test
	void testProjeto() throws Exception {
		Projeto p = new Projeto("RuneEscape", "002", juninho.id());
		empresa.addProjeto(p);
		assertEquals(p.nome(), "RuneEscape");
		assertEquals(p.id(), "002");
		assertEquals(p, empresa.getProjetoByID("002"));
		assertTrue(p.idResponsaveis().contains(juninho.id()));
	}
	
	@Test
	void testAddProjetoComResponsavelInexistente() throws Exception {
		Projeto p = new Projeto("RuneEscape", "002", "0000001");
		assertThrows(Exception.class, ()->{empresa.addProjeto(p);});
	}
}
