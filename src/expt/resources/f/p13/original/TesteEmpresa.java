package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Main.Empresa;
import Main.Funcionario;
import Main.Projeto;

public class TesteEmpresa {

	Empresa empresa;
	
	@BeforeEach
	public void beforeEach() throws Exception {
		empresa = Empresa.Instance();
		empresa.Reset();
	}
	
	
	@Test
	void testFuncionarios() throws Exception {
		Funcionario Godofredo = new Funcionario("Godofredo","0000001");
		empresa.addFuncionario(Godofredo);
		assertNotNull(empresa.funcionarios());
		assertEquals(empresa.getFuncionarioByID("0000001").id(), Godofredo.id());
	}
	
	@Test
	void testProjeto() throws Exception {
		Funcionario juninho = new Funcionario("Juninho", "0000007");
		empresa.addFuncionario(juninho);
		Projeto p = new Projeto("Tibia 2", "001", juninho.id());
		empresa.addProjeto(p);
		assertNotNull(empresa.projetos());
		assertEquals(p.id(), empresa.getProjetoByID("001").id());
	}

}
