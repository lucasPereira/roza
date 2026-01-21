package teste;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import projeto.Empresa;
import projeto.Funcionario;
import projeto.Projeto;

public class TestaAEmpresa {
	private Empresa umaEmpresa;
	
	@Before
	public void setup() {
		umaEmpresa = new Empresa("Empresa de Teste");
	}
	
	@Test
	public void retornaNomeEmpresa() throws Exception {
		assertEquals("Empresa de Teste", umaEmpresa.getNomeEmpresa());
	}

	@Test
	public void adicionarFuncionarioALista() throws Exception {
		umaEmpresa.addFuncionario(new Funcionario("Xisto"));
		
		assertEquals("Xisto", umaEmpresa.getFuncionarios(0).getNome());
	}
	
	@Test
	public void quantidadeFuncionariosDaEmpresa() {
		umaEmpresa.addFuncionario(new Funcionario("Xisto"));
		umaEmpresa.addFuncionario(new Funcionario("Fabio"));
		umaEmpresa.addFuncionario(new Funcionario("B"));
		
		assertEquals(new Integer(3), umaEmpresa.getQtdFuncionarios());
	}
	
	@Test
	public void adicionarProjetoALista() throws Exception {
		umaEmpresa.addProjeto(new Projeto("Projeto Zeta"));
		
		assertEquals("Projeto Zeta", umaEmpresa.getProjeto(0).getNomeProjeto());
	}
	
	@Test
	public void quantidadeProjetosDaEmpresa() throws Exception {
		umaEmpresa.addProjeto(new Projeto("Projeto Zeta"));
		umaEmpresa.addProjeto(new Projeto("Projeto X"));
		umaEmpresa.addProjeto(new Projeto("Projeto Y"));
		
		assertEquals(new Integer(3), umaEmpresa.getQtdProjetos());		
	}
	
	@Test
	public void ligarProjetoAoFuncionario() {
		Integer funcionarioEscolhidoParaOProjeto = 0;
		Integer projeto = 0;
		Funcionario umFuncionario = new Funcionario("Xisto");
		Projeto umProjeto = new Projeto("Projeto X");
		
		umaEmpresa.addFuncionario(umFuncionario);
		umaEmpresa.addProjeto(umProjeto);	
		umaEmpresa.addFuncionarioAoProjeto(funcionarioEscolhidoParaOProjeto, projeto);
		
		assertEquals(umFuncionario, umProjeto.getFuncionario(0));
	}
	
}
