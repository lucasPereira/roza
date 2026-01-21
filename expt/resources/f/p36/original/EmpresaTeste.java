import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmpresaTeste {

	private Empresa empresa;
	
	@BeforeEach
	void init() {
		empresa = new Empresa();
	}
	
	@Test
	void empresaCriarFuncionario() throws Exception {
		Funcionario joao = empresa.criarFuncionario("Joao");
		assertEquals(0, joao.getId());
		assertEquals("Joao", joao.getNome());
	}

	@Test
	void empresaCriarDoisFuncionarios() throws Exception {
		Funcionario joao = empresa.criarFuncionario("Joao");
		Funcionario pedro = empresa.criarFuncionario("Pedro");

		assertEquals(0, joao.getId());
		assertEquals("Joao", joao.getNome());
		assertEquals(1, pedro.getId());
		assertEquals("Pedro", pedro.getNome());		
	}

	@Test
	void empresaCriarProjeto() throws Exception {
		Projeto projetoA = empresa.criarProjeto("Projeto A");
		assertEquals(0, projetoA.getId());
		assertEquals("Projeto A", projetoA.getNome());
	}

	@Test
	void empresaCriarDoisProjeto() throws Exception {
		Projeto projetoA = empresa.criarProjeto("Projeto A");
		Projeto projetoB = empresa.criarProjeto("Projeto B");

		assertEquals(0, projetoA.getId());
		assertEquals("Projeto A", projetoA.getNome());
		assertEquals(1, projetoB.getId());
		assertEquals("Projeto B", projetoB.getNome());		
	}	
}
