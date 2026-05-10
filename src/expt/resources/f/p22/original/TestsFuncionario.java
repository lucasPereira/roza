package testes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import src.Funcionario;
import src.Projeto;

class TestsFuncionario {

Funcionario funcionario;
	
	@Before
	public void setup() {
		funcionario = new Funcionario("Matheus");
	}
	
	@Test
	public void criafuncionario() {
		assertEquals("Matheus", funcionario.Nome());
	}

	
	@Test
	public void adicionaProjeto1() throws Exception {
		Projeto projeto01 = new Projeto("Projeto01");
		funcionario.adicionaProjeto(projeto01);
		assertTrue(funcionario.listaProjeto(projeto01));
	}
	
	@Test
	public void adicionaProjeto2() throws Exception {
		Projeto projeto02 = new Projeto("Projeto02");
		funcionario.adicionaProjeto(projeto02);
		assertTrue(funcionario.listaProjeto(projeto02));
	}
	

}
