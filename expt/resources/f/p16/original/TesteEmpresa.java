package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import aplicação.*;

public class TesteEmpresa {
	
	private Empresa empresa;
	
	@Before
	public void configurar() {
		empresa = new Empresa();
	}
	
	@Test
	public void adicionarFuncionario() throws Exception {
		empresa.addFuncionario("Joao");
		assertEquals(1, empresa.numFuncionarios().intValue());
	}
	
	@Test
	public void adicionarProjeto() throws Exception {
		empresa.addProjeto("Projeto 1");
		assertEquals(1, empresa.numProjetos().intValue());
	}
}
