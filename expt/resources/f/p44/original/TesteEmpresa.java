package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TesteEmpresa {
	private Empresa empresa;
	
	@Before
	public void criacaoEmpresa() {
		empresa = new Empresa("Google");
	}
	
	@Test
	public void empresaZeroFuncionarios() {
		List<Funcionario> funcionarios = empresa.getFuncionarios();
		assertEquals(0, funcionarios.size()); 
	}
	
	@Test
	public void empresaUmFuncionario() throws Exception {
		Funcionario func1 = new Funcionario("Bryan Lima", 0);
		empresa.addFunc(func1);
		List<Funcionario> funcionarios = empresa.getFuncionarios();
		
		int idFunc1 = empresa.getFuncionarios().get(0).getId();
		
		assertEquals(1, funcionarios.size());
		assertEquals(0, idFunc1);
	}
	
	@Test
	public void empresaDoisFuncionario() throws Exception {
		Funcionario func1 = new Funcionario("Bryan Lima", 0);
		empresa.addFunc(func1);
		Funcionario func2 = new Funcionario("Patricia Vilain", 1);
		empresa.addFunc(func2);
		List<Funcionario> funcionarios = empresa.getFuncionarios();
		
		int idFunc1 = empresa.getFuncionarios().get(0).getId();
		int idFunc2 = empresa.getFuncionarios().get(1).getId();
		
		assertEquals(2, funcionarios.size()); 
		assertEquals(0, idFunc1);
		assertEquals(1, idFunc2);
	}
	
	@Test (expected = Exception.class)
	public void adicionarFuncionariosIdIguais() throws Exception {
		Funcionario func1 = new Funcionario("Bryan Lima", 0);
		empresa.addFunc(func1);
		Funcionario func2 = new Funcionario("Patricia Vilain", 0);
		empresa.addFunc(func2);
	}
	
	@Test
	public void empresaZeroProjetos() {
		List<Projeto> projetos = empresa.getProjetos();
		assertEquals(0, projetos.size());
	}
	
	@Test
	public void empresaUmProjeto() {
		Projeto proj1 = new Projeto("Pixel 5a");
		empresa.addProj(proj1);
		List<Projeto> projetos = empresa.getProjetos();
		
		String nomeProjeto = empresa.getProjetos().get(0).getName();
		
		assertEquals(1, projetos.size());
		assertEquals("Pixel 5a", nomeProjeto);
	}
	
	@Test
	public void empresaDoisProjeto() {
		Projeto proj1 = new Projeto("Pixel 5a");
		empresa.addProj(proj1);
		Projeto proj2 = new Projeto("Pixel 4a");
		empresa.addProj(proj2);
		List<Projeto> projetos = empresa.getProjetos();
		
		String nomeProjeto1 = empresa.getProjetos().get(0).getName();
		String nomeProjeto2 = empresa.getProjetos().get(1).getName();
		
		assertEquals(2, projetos.size());
		assertEquals("Pixel 5a", nomeProjeto1);
		assertEquals("Pixel 4a", nomeProjeto2);
	}
}
