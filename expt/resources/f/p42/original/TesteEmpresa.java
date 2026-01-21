package testes;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import modelos.Empresa;

public class TesteEmpresa {
	

	@Test
	public void testeCriaEmpresaDoJoao() {
		Empresa empresa = new Empresa("Empresa do Joao");
		assertEquals("Empresa do Joao", empresa.obterEmpresa());
	}
	
	@Test
	public void testeCriaEmpresaDaMaria() {
		Empresa empresa = new Empresa("Empresa da Maria");
		assertEquals("Empresa da Maria", empresa.obterEmpresa());
	}
}
