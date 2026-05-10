package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Main.Empresa;
import Main.Funcionario;

public class TesteFuncionario {

Empresa empresa;
	
	@BeforeEach
	public void beforeEach() {
		empresa = Empresa.Instance();
		empresa.Reset();
	}
	
	@Test
	void testFuncionarios() throws Exception {
		Funcionario Fagundes = new Funcionario("Fagundes","0000002");
		empresa.addFuncionario(Fagundes);
		assertEquals(Fagundes.nome(), "Fagundes");
		assertEquals(Fagundes.id(), "0000002");
		assertEquals(empresa.getFuncionarioByID("0000002"), Fagundes);
	}
	
	
}
