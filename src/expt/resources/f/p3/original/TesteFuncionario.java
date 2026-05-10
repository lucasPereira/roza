package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.Empresa;
import src.Funcionario;
import src.Projeto;

public class TesteFuncionario {
	
	private Empresa empresa;
	private Projeto projeto;
	
	@Before
	public void config(){
		empresa = new Empresa();
		projeto = new Projeto("NovoProjeto");
	}
	
	@Test
	public void criarFuncionario() {
		Funcionario funcionario = new Funcionario("Joao");
		assertEquals(funcionario.getName(), "Joao");
	}
	
	@Test
	public void adicionarFuncionario() {
		Funcionario funcionario = new Funcionario("Joao");
		empresa.cadastrarFuncionario(funcionario.getName());
		
		assertEquals(funcionario.getName(), empresa.getFuncionarios().get(0).getName());
	}

}
