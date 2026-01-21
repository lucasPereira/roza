package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import src.Empresa;
import src.Funcionario;
import src.Projeto;

public class TesteEmpresa {
	private Empresa empresa;

	@Before
	public void setup() {
		empresa = new Empresa();
	}

	@Test
	public void adicionarFuncionario() {
		Funcionario alice = new Funcionario();
		Funcionario bob = new Funcionario();
		
		empresa.addFuncionario(alice);
		empresa.addFuncionario(bob);
		
		assertEquals(2, empresa.numFuncionarios());
	}

	@Test
	public void adicionarProjeto() {
		Projeto calculadora = new Projeto();
		Projeto gerenciadorOcorrencias = new Projeto();
		
		empresa.addProjeto(calculadora);
		empresa.addProjeto(gerenciadorOcorrencias);
		
		assertEquals(2, empresa.numProjetos());
	}
}
