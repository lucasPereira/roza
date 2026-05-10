package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import aplicação.*;

public class TesteFuncionario {
	
	private Empresa empresa;
	private Funcionario funcionario;
	
	@Before
	public void configurar() {
		empresa = new Empresa();
		
	}
	
	@Test
	public void criarPrimeiroFuncionario() throws Exception {
		funcionario = empresa.addFuncionario("Joao");
		assertEquals(1, funcionario.id().intValue());
		assertEquals("Joao", funcionario.nome());
	}
	
	@Test
	public void criarSegundoFuncionario() throws Exception {
		funcionario = empresa.addFuncionario("Joao");
		Funcionario funcionario2 = empresa.addFuncionario("Pedro");
		assertEquals(1, funcionario.id().intValue());
		assertEquals(2, funcionario2.id().intValue());
	}
}
