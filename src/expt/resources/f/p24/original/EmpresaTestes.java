package testes;

import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import domain.Empresa;
import domain.Funcionario;
import domain.Projeto;


public class EmpresaTestes {

	@Before
	public void cleanEmpresa() {
		Empresa.apagarEmpresa();
	}
	
	// EMPRESA
	@Test
	public void umaEmpresaCriadaDeveTerUmNome() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		assertEquals(nomeEmpresa, empresa.obterNome());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void umaEmpresaNaoPodeTerUmNomeNulo() {
		String nomeEmpresa = null;
		new Empresa(nomeEmpresa);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void umaEmpresaNaoPodeTerUmNomeVazio() {
		String nomeEmpresa = "";
		new Empresa(nomeEmpresa);
	}
	
	@Test
	public void deveSerPossivelDeObterAEmpresaCriada() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		Empresa empresaRetorno = Empresa.obterEmpresa();
		assertEquals(empresa, empresaRetorno);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoPodeObterUmaEmpresaSemEstarCriadaPrimeiro() {
		Empresa.obterEmpresa();
	}
	
	// FUNCIONARIOS DA EMPRESA
	@Test
	public void umaEmpresaCriadaDeveTerUmaListaDeFuncionariosVazia() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		assertEquals(0, empresa.obterFuncionarios().size());
	}
	
	@Test
	public void devoPoderCriarPrimeiroFuncionarioDaEmpresa() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		assertEquals(0, empresa.obterFuncionarios().size());

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		assertEquals(1, empresa.obterFuncionarios().size());
	}

	@Test
	public void umFuncionarioDeveSerCriadoCorretamente() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		assertEquals(0, empresa.obterFuncionarios().size());

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		assertEquals(1, empresa.obterFuncionarios().size());
		
		Iterator<Funcionario> itera = empresa.obterFuncionarios().iterator();
		Funcionario primeiroFuncionario = itera.next();
		
		assertEquals(nomeFuncionario, primeiroFuncionario.obterNome());
	}
	
	@Test
	public void possoAdicionarMultiplosFuncionarios() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		assertEquals(0, empresa.obterFuncionarios().size());

		java.util.List<String> nomesFunc = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
		
		assertEquals(2, empresa.obterFuncionarios().size());
	}
	
	@Test
	public void osMultiplosFuncionariosDevemSerCriadosCorretamente() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		assertEquals(0, empresa.obterFuncionarios().size());

		java.util.List<String> nomesFunc = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
		
		assertEquals(2, empresa.obterFuncionarios().size());

		Iterator<Funcionario> itera = empresa.obterFuncionarios().iterator();

		assertEquals(nomesFunc.get(0), itera.next().obterNome());
		assertEquals(nomesFunc.get(1), itera.next().obterNome());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void osFuncionariosNaoDevemterNomesDuplicados() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		assertEquals(0, empresa.obterFuncionarios().size());

		java.util.List<String> nomesFunc = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));

		empresa.adicionarFuncionario(nomesFunc.get(0));
	}

	// PROJETOS DA EMPRESA
	@Test
	public void umaEmpresaCriadaDeveTerUmaListaDeProjetosVazia() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		assertEquals(0, empresa.obterProjetos().size());
	}
	
	@Test
	public void devoPoderCriarPrimeiroProjetoDaEmpresa() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		assertEquals(0, empresa.obterProjetos().size());

		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		
		assertEquals(1, empresa.obterProjetos().size());
	}

	@Test
	public void umProjetoDeveSerCriadoCorretamente() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		assertEquals(0, empresa.obterProjetos().size());

		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		
		assertEquals(1, empresa.obterProjetos().size());
		
		Iterator<Projeto> itera = empresa.obterProjetos().iterator();
		Projeto primeiroProjeto = itera.next();
		
		assertEquals(nomeProjeto, primeiroProjeto.obterNome());
	}
	
	@Test
	public void possoAdicionarMultiplosProjetos() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		assertEquals(0, empresa.obterProjetos().size());

		java.util.List<String> nomesProj = java.util.List.of("XY", "YZ");
		empresa.adicionarProjeto(nomesProj.get(0));
		empresa.adicionarProjeto(nomesProj.get(1));
		
		assertEquals(2, empresa.obterProjetos().size());
	}
	
	@Test
	public void osMultiplosProjetosDevemSerCriadosCorretamente() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		assertEquals(0, empresa.obterProjetos().size());

		java.util.List<String> nomesProj = java.util.List.of("XY", "YZ");
		empresa.adicionarProjeto(nomesProj.get(0));
		empresa.adicionarProjeto(nomesProj.get(1));
		
		assertEquals(2, empresa.obterProjetos().size());

		Iterator<Projeto> itera = empresa.obterProjetos().iterator();
		
		assertEquals(nomesProj.get(0), itera.next().obterNome());
		assertEquals(nomesProj.get(1), itera.next().obterNome());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void osProjetosNaoDevemterNomesDuplicados() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		assertEquals(0, empresa.obterProjetos().size());

		java.util.List<String> nomesProj = java.util.List.of("XY", "YZ");
		empresa.adicionarProjeto(nomesProj.get(0));
		empresa.adicionarProjeto(nomesProj.get(1));

		empresa.adicionarProjeto(nomesProj.get(0));
	}
}
