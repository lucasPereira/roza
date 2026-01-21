package test.br.ufsc.testes.exercicio_tdd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modelo.br.ufsc.testes.exercicio_tdd.Empresa;

public class TesteEmpresa {
	
	private Empresa empresa;

	@Before
	public void setUp() {
		empresa = new Empresa();
	}
	
	@Test
	public void testeAdicionarUmFuncionario() {
		empresa.adicionarFuncionario("João");
		assertEquals(1, empresa.obterNumeroDeFuncionarios());
	}
	
	@Test
	public void testeAdicionarDoisFuncionarios() {
		empresa.adicionarFuncionario("João");
		empresa.adicionarFuncionario("Maria");
		assertEquals(2, empresa.obterNumeroDeFuncionarios());
	}
	
	@Test
	public void testeAdicionarUmProjeto() {
		empresa.adicionarProjeto("Projeto Manhattan");
		assertEquals(1, empresa.obterNumeroDeProjetos());
	}
	
	@Test
	public void testeAdicionarDoisProjetos() {
		empresa.adicionarProjeto("Projeto Manhattan");
		empresa.adicionarProjeto("Projeto Verão");
		assertEquals(2, empresa.obterNumeroDeProjetos());
	}
	
}
