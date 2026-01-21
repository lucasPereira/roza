package tests;

import org.junit.jupiter.api.Test;

import exceptions.NomeVazio;

import org.junit.jupiter.api.BeforeEach;

import tdd_ocorrencias.Empresa;
import tdd_ocorrencias.Funcionario;
import tdd_ocorrencias.Projeto;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class TestEmpresa {
	Empresa empresa1;
	
	@BeforeEach
	public void setUp() throws NomeVazio {
		empresa1 = new Empresa("Empresa1");
	}
	
	@Test
	void nomeDaEmpresa() throws Exception {
		assertEquals("Empresa1", empresa1.getNome());
	}
	
	@Test
	void nomeVazioEmpresa() throws NomeVazio {
		assertThrows(NomeVazio.class, () -> new Empresa(""));
	}
	
	@Test
	void listaDeFuncionariosVazia() throws Exception {
		assertEquals(new ArrayList<Funcionario>(), empresa1.getFuncionarios());
	}
	
	@Test
	void listaDeProjetosVazia() throws Exception {
		assertEquals(new ArrayList<Projeto>(), empresa1.getProjetos());
	}
}
