package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.Empresa;

public class TesteEmpresa {
	
	private Empresa empresa;
	
	@Before
	public void config(){
		this.empresa = new Empresa();
	}

	@Test
	public void cadastrarFuncionario(){
		this.empresa.cadastrarFuncionario("Joao");
		assertTrue(this.empresa.getFuncionarios().size() > 0);
	}
	
	@Test
	public void cadastrarProjeto(){
		this.empresa.cadastrarProjeto("Projeto");
		assertTrue(this.empresa.getProjetos().size() > 0);
	}
}
