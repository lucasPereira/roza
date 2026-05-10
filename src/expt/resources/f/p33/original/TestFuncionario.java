package tests;

import org.junit.jupiter.api.Test;

import exceptions.NomeVazio;

import org.junit.jupiter.api.BeforeEach;

import tdd_ocorrencias.Empresa;
import tdd_ocorrencias.Funcionario;
import tdd_ocorrencias.Ocorrencia;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

public class TestFuncionario {
	Empresa empresa1;
	Funcionario funcionario1;
	
	@BeforeEach
	public void setUp() throws NomeVazio {
		empresa1 = new Empresa("Empresa1");
		funcionario1 = new Funcionario("Funcionario1", empresa1);
	}
	
	@Test
	void nomeDoFuncionario() throws Exception {
		assertEquals("Funcionario1", funcionario1.getNome());
	}
	
	@Test
	void nomeVazioFuncionario() throws NomeVazio {
		assertThrows(NomeVazio.class, () -> new Funcionario("", empresa1));
	}
	
	@Test
	void idDoFuncionario() throws NomeVazio {
		Funcionario funcionario2 = new Funcionario("Funcionario2", empresa1);
		assertEquals(0, funcionario1.getId());
		assertEquals(1, funcionario2.getId());
	}
	
	@Test
	void empresaDoFuncionario() throws Exception {
		assertEquals(empresa1, funcionario1.getEmpresa());
	}
	
	@Test
	void inserirFuncionarioNaEmpresa() throws Exception {
		assertTrue(empresa1.getFuncionarios().contains(funcionario1));
	}
	
	@Test
	void listaDeOcorrenciasVazia() throws Exception {
		assertEquals(new ArrayList<Ocorrencia>(), funcionario1.getOcorrencias());
	}
}
