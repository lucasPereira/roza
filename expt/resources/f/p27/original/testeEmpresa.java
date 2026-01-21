import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class testeEmpresa {
	
	Empresa emp;
	
	@Before
	public void init() {
		emp = new Empresa("Empresa x");
	}
	
	@Test
	public void testeCriaEmpresa() {
		String nome = "Empresa x";
		Empresa emp1 = new Empresa(nome);
		
		String result = emp1.getNome();
		
		assertEquals(nome, result);
		
	}
	
	@Test
	public void testeCriaEmpresaNomeDiferente() {
		String nome = "Empresa y";
		Empresa emp1 = new Empresa(nome);
		
		String result = emp1.getNome();
		
		assertEquals(nome, result);
		
	}
	
	@Test
	public void testeCriaFuncionario() {
		String nome = "Joao";
		Funcionario result = emp.criaFuncionario(nome);		
		
		assertEquals(nome, result.getNome());
		
	}
	
	@Test
	public void testeRetornaListaComUmFuncionario() {
		String nome1 = "Joao";
		
		Funcionario f1 = emp.criaFuncionario(nome1);
		
		List<Funcionario> result = emp.retornaFuncionarios();
		
		assertEquals(f1, result.get(0));
	}
	
	@Test
	public void testeCriaEmpresaSemFuncionario() {
		String nome = "Empresa x";
		Empresa emp = new Empresa(nome);
		
		List<Funcionario> result = emp.retornaFuncionarios();
		
		assertNotEquals(null, result);
		assertEquals(0, result.size());
	}
	
	@Test
	public void testeListaFuncionarios() {
		String nome1 = "Joao";
		String nome2 = "Maria";		
		Funcionario func = emp.criaFuncionario(nome1);
				
		Funcionario func2 = emp.criaFuncionario(nome2);
		
		List<Funcionario> result = emp.retornaFuncionarios();
		
		assertEquals(2, result.size());
		assertEquals(func, result.get(0));
		assertEquals(func2, result.get(1));
	}
	
	@Test
	public void testeCriarProjeto() {
		String nomeProj = "projeto x";
		Projeto proj = emp.criaProjeto(nomeProj);
		
		assertEquals(proj.getNome(), nomeProj);
	}
	
	
	@Test
	public void testeRetornaListaComUmProjeto() {
		String nome1 = "projeto x";
		
		Projeto proj = emp.criaProjeto(nome1);
		
		List<Projeto> result = emp.retornaProjetos();
		
		assertEquals(proj, result.get(0));
	}
	
	@Test
	public void testeCriaEmpresaSemProjeto() {
		String nome = "Empresa x";
		Empresa emp = new Empresa(nome);
		
		List<Projeto> result = emp.retornaProjetos();
		
		assertNotEquals(null, result);
		assertEquals(0, result.size());
	}
	
	@Test
	public void testeListaProjetos() {
		String nome1 = "projeto x";
		String nome2 = "projeto y";
		
		Projeto proj = emp.criaProjeto(nome1);
		Projeto proj2 = emp.criaProjeto(nome2);
		
		List<Projeto> result = emp.retornaProjetos();
		
		assertEquals(2, result.size());
		assertEquals(proj, result.get(0));
		assertEquals(proj2, result.get(1));		
	}
	
	@Test
	public void testeAtribuiProjetoFuncionario() {
		String nomeFunc = "João";
		String nomeProj = "projeto x";
		Funcionario func = emp.criaFuncionario(nomeFunc);
		Projeto proj = emp.criaProjeto(nomeProj);
		
		boolean result = emp.atribuiProjetoFuncionario(func, proj);
		
		assertEquals(true, result);
	}
	
	@Test
	public void testeAtribuiProjetoNullFuncionario() {
		String nomeFunc = "João";
		Funcionario func = emp.criaFuncionario(nomeFunc);
		
		boolean result = emp.atribuiProjetoFuncionario(func, null);
		
		assertEquals(false, result);
	}
	
	@Test
	public void testeAtribuiProjetoFuncionarioNull() {
		String nomeProj = "projeto x";
		Projeto proj = emp.criaProjeto(nomeProj);
		
		boolean result = emp.atribuiProjetoFuncionario(null, proj);
		
		assertEquals(false, result);
	}

}
