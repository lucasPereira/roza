import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TesteEmpresa {
	
	private Empresa newEmpresa;
	
	@BeforeEach
	void configuracao() {
		newEmpresa = new Empresa("Coca-Cola");
	}
	
	@Test
	void nomeDaEmpresa() throws Exception {
		assertEquals("Coca-Cola", newEmpresa.getNome());
	}
	@Test
	void nomeDaEmpresaErrado() throws Exception {
		assertNotEquals("E", newEmpresa.getNome());
	}
	
	@Test
	void adicionarFuncionario() throws Exception {
		Funcionario stefanoFuncionario = new Funcionario(newEmpresa, "nome", 1);
		newEmpresa.addFuncionario(stefanoFuncionario);
		assertEquals(stefanoFuncionario, newEmpresa.getFuncionario(1));
	}
	
	@Test
	void adicionarDoisFuncionarios() throws Exception {
		Funcionario stefanoFuncionario = new Funcionario(newEmpresa, "nome", 1);
		Funcionario stefanoFuncionario2 = new Funcionario(newEmpresa, "nome2", 2);
		newEmpresa.addFuncionario(stefanoFuncionario);
		newEmpresa.addFuncionario(stefanoFuncionario2);
		assertEquals(stefanoFuncionario, newEmpresa.getFuncionario(1));
		assertEquals(stefanoFuncionario2, newEmpresa.getFuncionario(2));
	}
	
	@Test
	void checarNomeDeFuncionario() throws Exception {
		Funcionario stefanoFuncionario = new Funcionario(newEmpresa, "nome", 1);
		Funcionario stefanoFuncionario2 = new Funcionario(newEmpresa, "nome2", 2);
		newEmpresa.addFuncionario(stefanoFuncionario);
		newEmpresa.addFuncionario(stefanoFuncionario2);
		assertEquals("nome", newEmpresa.getFuncionario(1).getNome());
		assertEquals("nome2", newEmpresa.getFuncionario(2).getNome());
	}
	
	@Test
	void checarIDDeFuncionario() throws Exception {
		Funcionario stefanoFuncionario = new Funcionario(newEmpresa, "Stefano Bergamini Poletto", 1);
		Funcionario joaoFuncionario = new Funcionario(newEmpresa, "Joao Santana", 2);
		newEmpresa.addFuncionario(stefanoFuncionario);
		newEmpresa.addFuncionario(joaoFuncionario);
		assertEquals(1, newEmpresa.getFuncionario(1).getID());
		assertEquals(2, newEmpresa.getFuncionario(2).getID());
	}
	
	// da errado pois nao é adicionado um funcionario a empresa
	@Test
	void naoExisteFuncionario() throws Exception {
		assertEquals(null, newEmpresa.getFuncionario(1));
	}
	
	@Test
	void adicionarProjeto() throws Exception {
		Projeto newProjeto = new Projeto(newEmpresa, 1);
		newEmpresa.addProjeto(newProjeto);
		assertEquals(newProjeto, newEmpresa.getProjeto(1));
	}
	
	@Test
	void adicionarDoisProjetos() throws Exception {
		Projeto newProjeto = new Projeto(newEmpresa, 1);
		Projeto newProjeto2 = new Projeto(newEmpresa, 2);
		newEmpresa.addProjeto(newProjeto);
		newEmpresa.addProjeto(newProjeto2);
		assertEquals(newProjeto, newEmpresa.getProjeto(1));
		assertEquals(newProjeto2, newEmpresa.getProjeto(2));
	}
	
	//testar tamanho da lista de projetos
	@Test
	void numeroDeProjetos() throws Exception {
		Projeto newProjeto = new Projeto(newEmpresa, 1);
		newEmpresa.addProjeto(newProjeto);
		assertEquals(1, newEmpresa.getListaProjetos().size());
	}
	
	//testar sem projetos
	@Test
	void naoExisteProjeto() throws Exception {
		assertEquals(null, newEmpresa.getProjeto(1));
	}
	
	
	
}
