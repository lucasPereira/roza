package tdd;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tdd.Empresa;
import tdd.Funcionario;
import tdd.Projeto;

public class TestEmpresa {

	private Empresa minhaEmpresa;
	@Before
	public void fixtureSetup() throws Exception {
		minhaEmpresa = new Empresa();
	}
	
	@Test
	public void cadastrarFuncionario() throws Exception {
		Funcionario meuFuncionario = new Funcionario();
		// Exercise SUT
		minhaEmpresa.cadastrarFuncionario(meuFuncionario);
		// Result Verification
		assertEquals(1, minhaEmpresa.getFuncionarios().size());
		assertTrue(minhaEmpresa.getFuncionarios().contains(meuFuncionario));
	}
	@Test
	public void cadastrarFuncionario_2() throws Exception {
		// Fixture Setup
		Funcionario primeiroFuncionario = new Funcionario();
		minhaEmpresa.cadastrarFuncionario(primeiroFuncionario);
		Funcionario segundoFuncionario = new Funcionario();
		// Exercise SUT
		minhaEmpresa.cadastrarFuncionario(segundoFuncionario);
		// Result Verification
		assertEquals(2, minhaEmpresa.getFuncionarios().size());
		assertTrue(minhaEmpresa.getFuncionarios().contains(primeiroFuncionario));
		assertTrue(minhaEmpresa.getFuncionarios().contains(segundoFuncionario));
	}
	@Test(expected=FuncionarioJaCadastrado.class)
	public void cadastrarFuncionario_jaCadastrado() throws Exception {
		Funcionario meuFuncionario = new Funcionario();
		minhaEmpresa.cadastrarFuncionario(meuFuncionario);
		List<Funcionario> listaDeFuncionarios = new LinkedList<>(minhaEmpresa.getFuncionarios());
		try{
			// Exercise SUT
			minhaEmpresa.cadastrarFuncionario(meuFuncionario);
		}catch (FuncionarioJaCadastrado e) {
			// Result Verification
			assertEquals(listaDeFuncionarios, minhaEmpresa.getFuncionarios());
			throw e;
		}
	}
	
	@Test
	public void cadastrarProjeto() throws Exception {
		// Fixture Setup
		Projeto meuProjeto = new Projeto();
		// Exercise SUT
		minhaEmpresa.cadastrarProjeto(meuProjeto);
		// Result Verification
		assertEquals(1, minhaEmpresa.getProjetos().size());
		assertTrue(minhaEmpresa.getProjetos().contains(meuProjeto));
	}
	@Test
	public void cadastrarProjeto_2() throws Exception {
		// Fixture Setup
		Projeto primeiroProjeto = new Projeto();
		minhaEmpresa.cadastrarProjeto(primeiroProjeto);
		Projeto segundoProjeto = new Projeto();
		// Exercise SUT
		minhaEmpresa.cadastrarProjeto(segundoProjeto);
		// Result Verification
		assertEquals(2, minhaEmpresa.getProjetos().size());
		assertTrue(minhaEmpresa.getProjetos().contains(primeiroProjeto));
		assertTrue(minhaEmpresa.getProjetos().contains(segundoProjeto));
	}
	@Test(expected=ProjetoJaCadastrado.class)
	public void cadastrarProjeto_jaCadastrado() throws Exception {
		// Fixture Setup
		Projeto meuProjeto = new Projeto();
		minhaEmpresa.cadastrarProjeto(meuProjeto);
		List<Projeto> listaDeProjetos = new LinkedList<>(minhaEmpresa.getProjetos());
		try{
			// Exercise SUT
			minhaEmpresa.cadastrarProjeto(meuProjeto);
		} catch (ProjetoJaCadastrado e) {
			// Result Verification
			assertEquals(listaDeProjetos, minhaEmpresa.getProjetos());
			throw e;
		}
	}
}
