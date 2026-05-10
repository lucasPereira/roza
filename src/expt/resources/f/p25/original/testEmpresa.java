package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GerenciadorDeOcorrencia.Empresa;

class testEmpresa {
	
	Empresa empresa;
	
	@BeforeEach
	void configuracao() {
		empresa = new Empresa("Empresa Teste");
	}

	@Test
	void empresaSemFuncionarios() {
		assertEquals(0, empresa.getFuncionarios().size());
	}
	
	@Test
	void contratarUmFuncionario() {
		empresa.contrataFuncionario();
		assertEquals(1, empresa.getFuncionarios().size());
	}
	
	@Test
	void contratarMultiplosFuncionario() {
		empresa.contrataFuncionario();
		empresa.contrataFuncionario();
		assertEquals(2, empresa.getFuncionarios().size());
	}
	
	@Test
	void empresaSemProjetos() {
		assertEquals(0, empresa.getProjetos().size());
	}
	
	@Test
	void iniciaUmProjeto() {
		empresa.iniciaProjeto("ProjetoTeste");
		assertEquals(1, empresa.getProjetos().size());
	}
	
	@Test
	void iniciaMultiplosProjetos() {
		empresa.iniciaProjeto("ProjetoTeste");
		empresa.iniciaProjeto("ProjetoTeste");
		assertEquals(2, empresa.getProjetos().size());
	}
}
