package testes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import src.Empresa;
import src.Funcionario;
import src.Projeto;


class TestsEmpresa {

Empresa empresa;
	
	@Before
	public void setup() {
		empresa = new Empresa("empresa");
	}
	
	@Test
	public void criaempresa() throws Exception {
		assertEquals("empresa", empresa.nome());
	}


	@Test
	public void adicionaFuncionario1() throws Exception {
		Funcionario rafael = new Funcionario("Funcionario01");
		empresa.adicionaFuncionario(rafael);
		assertTrue(empresa.temFuncionario(rafael));
	}
	
	@Test
	public void adicionaFuncionario2() throws Exception {
		Funcionario lucas = new Funcionario("Funcionario02");
		empresa.adicionaFuncionario(lucas);
		assertTrue(empresa.temFuncionario(lucas));
	}
	
	
	@Test
	public void adicionaProjeto1() throws Exception {
		Projeto projeto1 = new Projeto("Projeto01");
		empresa.adicionaProjeto(projeto1);
		assertTrue(empresa.temProjeto(projeto1));
	}
	
	@Test
	public void adicionaProjeto2() throws Exception {
		Projeto projeto2 = new Projeto("Projeto02");
		empresa.adicionaProjeto(projeto2);
		assertTrue(empresa.temProjeto(projeto2));
	}
	
	
}
