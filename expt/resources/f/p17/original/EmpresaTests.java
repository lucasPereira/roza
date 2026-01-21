package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import src.Empresa;
import src.Funcionario;
import src.Projeto;

public class EmpresaTests {	
	Empresa hexagon;
	
	@Before
	public void setup() {
		hexagon = new Empresa("Hexagon");
	}
	
	@Test
	public void criaHexagon() throws Exception {
		assertEquals("Hexagon", hexagon.getNome());
	}

	@Test
	public void adicionaRafael() throws Exception {
		Funcionario rafael = new Funcionario("Rafael");
		hexagon.adicionaFuncionario(rafael);
		assertTrue(hexagon.temFuncionario(rafael));
	}
	
	@Test
	public void adicionaLucas() throws Exception {
		Funcionario lucas = new Funcionario("Lucas");
		hexagon.adicionaFuncionario(lucas);
		assertTrue(hexagon.temFuncionario(lucas));
	}
	
	@Test
	public void adicionaLucasERafael() throws Exception {
		ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
		Funcionario rafael = new Funcionario("Rafael");
		hexagon.adicionaFuncionario(rafael);
		funcionarios.add(rafael);
		Funcionario lucas = new Funcionario("Lucas");
		hexagon.adicionaFuncionario(lucas);
		funcionarios.add(lucas);
		assertTrue(hexagon.getListaFuncionarios().equals(funcionarios));
	}
	
	@Test
	public void adicionaProjeto1() throws Exception {
		Projeto projeto1 = new Projeto("Projeto 1");
		hexagon.adicionaProjeto(projeto1);
		assertTrue(hexagon.temProjeto(projeto1));
	}
	
	@Test
	public void adicionaProjeto2() throws Exception {
		Projeto projeto2 = new Projeto("Projeto 2");
		hexagon.adicionaProjeto(projeto2);
		assertTrue(hexagon.temProjeto(projeto2));
	}
	
	@Test
	public void adicionasProjetos1e2() throws Exception {
		ArrayList<Projeto> projetos = new ArrayList<Projeto>();
		Projeto projeto1 = new Projeto("Projeto 1");
		hexagon.adicionaProjeto(projeto1);
		projetos.add(projeto1);
		Projeto projeto2 = new Projeto("Projeto 2");
		hexagon.adicionaProjeto(projeto2);
		projetos.add(projeto2);
		assertTrue(hexagon.getListaProjetos().equals(projetos));
	}
}
