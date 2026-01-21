package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Funcionario;

public class TestFuncionario {
	
	private Funcionario funcionario;
	
	@Before
	public void setUp() {
		funcionario = new Funcionario(1, "Mark");
	}
	
	@Test
	public void testCriarFuncionario(){
		assertEquals("Mark", funcionario.getNome());
		assertEquals(1, funcionario.getId());
		assertEquals(0, funcionario.getQuantidadeDeOcorrencias());
		assertFalse(funcionario.possuiMaximoDeOcorrencias());
	}
		
	@Test
	public void testIgualdadeDeFuncionarios() {
		assertEquals(funcionario, funcionario);
	}
	
	@Test
	public void testIgualdadeDeFuncionariosDiferentesInstancias() {
		Funcionario funcionarioMark = new Funcionario(1, "Mark");
		assertEquals(funcionarioMark, funcionario);
	}
	
	@Test
	public void testDesigualdadeDeFuncionarios() {
		Funcionario funcionarioJoao = new Funcionario(2, "Joao");
		assertNotEquals(funcionario, funcionarioJoao);
	}
	
}
