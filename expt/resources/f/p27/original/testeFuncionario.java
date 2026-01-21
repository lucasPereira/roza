import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class testeFuncionario {
	
	@Test
	public void testeCriaFuncionario() {
		String nome = "João";
		Funcionario func = new Funcionario(nome);
		
		String result = func.getNome();
		
		assertEquals(nome, result);
	}
	
	@Test
	public void testeCriaFuncionarioNomeDiferente() {
		String nome = "Maria";
		Funcionario func = new Funcionario(nome);
		
		String result = func.getNome();
		
		assertEquals(nome, result);
	}
	
	@Test
	public void atribuiProjeto() {
		Projeto proj = new Projeto("projeto x");
		Funcionario func = new Funcionario("João");
		
		boolean result = func.atribuiProjeto(proj);
		
		assertEquals(true, result);
	}
	
	@Test
	public void atribuiDoisProjetos() {
		Projeto proj = new Projeto("projeto x");
		Projeto proj2 = new Projeto("projeto y");
		Funcionario func = new Funcionario("João");		
		func.atribuiProjeto(proj);
		
		boolean result = func.atribuiProjeto(proj2);
		
		assertEquals(true, result);
	}
	
	@Test
	public void atribuiProjetoDuasVezes() {
		Projeto proj = new Projeto("projeto x");
		Funcionario func = new Funcionario("João");
		func.atribuiProjeto(proj);
		
		boolean result = func.atribuiProjeto(proj);
		
		assertEquals(false, result);
	}
	
	@Test
	public void atribuidProjetoNull() {
		Funcionario func = new Funcionario("João");
		
		boolean result = func.atribuiProjeto(null);
		
		assertEquals(false, result);
	}
	
	@Test
	public void listaProjetos() {
		Funcionario func = new Funcionario("João");
		Projeto proj = new Projeto("Projeto x");
		func.atribuiProjeto(proj);
		
		List<Projeto> result = func.retornaProjetos();
		
		assertEquals(1, result.size());
		assertEquals(proj, result.get(0));
		
	}
	
	@Test
	public void listaDoisProjetos() {
		Funcionario func = new Funcionario("João");
		Projeto proj = new Projeto("Projeto x");
		Projeto proj2 = new Projeto("Projeto y");
		func.atribuiProjeto(proj);
		func.atribuiProjeto(proj2);
		
		List<Projeto> result = func.retornaProjetos();
		
		assertEquals(2, result.size());
		assertEquals(proj, result.get(0)); 
		assertEquals(proj2, result.get(1));
		
	}
}
