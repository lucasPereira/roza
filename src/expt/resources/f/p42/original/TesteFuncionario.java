package testes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelos.Empresa;
import modelos.Funcionario;

public class TesteFuncionario {
	
	private Empresa empresa;

	@BeforeEach
	public void configurar() {
		empresa = new Empresa("Empresa do Joao");
	}
	
	@Test
	public void testeCriaFuncionarioJoao() {
		empresa.criaFuncionario("Joao da Silva");
		assertEquals("Joao da Silva", empresa.obterFuncionario(0));
	}

	@Test
	public void testeCriaFuncionarioJorge() {
		empresa.criaFuncionario("Jorge da Silva");
		assertEquals("Jorge da Silva", empresa.obterFuncionario(0));
	}
	
	@Test
	public void testeCriaFuncionarioJoaoEJorge() {
		Funcionario joao = empresa.criaFuncionario("Joao da Silva");
		Funcionario jorge = empresa.criaFuncionario("Jorge da Silva");
		List<Funcionario> lista = new ArrayList<>();
		lista.add(joao);
		lista.add(jorge);
		
		assertEquals("Joao da Silva", empresa.obterFuncionario(0));
		assertEquals("Jorge da Silva", empresa.obterFuncionario(1));
		assertEquals(lista, empresa.obterFuncionarios());
		assertEquals(2, empresa.obterFuncionarios().size());
	}
	
}
