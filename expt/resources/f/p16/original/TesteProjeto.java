package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import aplicação.*;

public class TesteProjeto {
	
	private Empresa empresa;
	private Projeto projeto;
	
	@Before
	public void configurar() {
		empresa = new Empresa();
		
	}
	
	@Test
	public void criarPrimeiroProjeto() throws Exception {
		projeto = empresa.addProjeto("Projeto 1");
		assertEquals(1, projeto.id().intValue());
		assertEquals("Projeto 1", projeto.nome());
	}
	
	@Test
	public void criarSegundoProjeto() throws Exception {
		projeto = empresa.addProjeto("Projeto 1");
		Projeto projeto2 = empresa.addProjeto("Projeto 2");
		assertEquals(1, projeto.id().intValue());
		assertEquals(2, projeto2.id().intValue());
	}
}
