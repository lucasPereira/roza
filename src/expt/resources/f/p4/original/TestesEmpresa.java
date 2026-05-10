package testes;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import projeto.Empresa;
import projeto.Funcionario;
import projeto.Projeto;

public class TestesEmpresa {

	Empresa empresa;
	
	@Before
	public void inicializacao() {
		empresa = new Empresa();
	}
	
	@Test
	public void adicionarNovoFuncionario() {
		Funcionario joao = new Funcionario();
		empresa.adicionarFuncionario(joao);
		
		List<Funcionario> funcionarios = empresa.obterFuncionarios();
		
		assertEquals(1, funcionarios.size());
	}
	
	@Test
	public void adicionaNovoProjeto() {
		Projeto novoProjeto = new Projeto();
		empresa.adicionaNovoProjeto(novoProjeto);
		
		List<Projeto> projetos = empresa.obterProjetos();
		
		assertEquals(1, projetos.size());
	}

}
