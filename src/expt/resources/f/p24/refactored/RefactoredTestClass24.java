import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass24 {

	private String nomeEmpresa;

	private Empresa empresa;

	@Before()
	public void setup() {
		Empresa.apagarEmpresa();
		nomeEmpresa = "DelaRocaCompany";
		empresa = new Empresa(nomeEmpresa);
	}

	@Test()
	public void deveSerPossivelDeObterAEmpresaCriada() {
		Empresa empresaRetorno = Empresa.obterEmpresa();
		assertEquals(empresa, empresaRetorno);
	}

	@Test()
	public void devoPoderCriarPrimeiroFuncionarioDaEmpresa() {
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		assertEquals(0, empresa.obterFuncionarios().size());
		assertEquals(1, empresa.obterFuncionarios().size());
	}

	@Test()
	public void devoPoderCriarPrimeiroProjetoDaEmpresa() {
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		assertEquals(0, empresa.obterProjetos().size());
		assertEquals(1, empresa.obterProjetos().size());
	}

	@Test()
	public void osFuncionariosNaoDevemterNomesDuplicados() {
		java.util.List<String> nomesFunc = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
		empresa.adicionarFuncionario(nomesFunc.get(0));
		assertEquals(0, empresa.obterFuncionarios().size());
	}

	@Test()
	public void osMultiplosFuncionariosDevemSerCriadosCorretamente() {
		java.util.List<String> nomesFunc = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
		Iterator<Funcionario> itera = empresa.obterFuncionarios().iterator();
		assertEquals(0, empresa.obterFuncionarios().size());
		assertEquals(2, empresa.obterFuncionarios().size());
		assertEquals(nomesFunc.get(0), itera.next().obterNome());
		assertEquals(nomesFunc.get(1), itera.next().obterNome());
	}

	@Test()
	public void osMultiplosProjetosDevemSerCriadosCorretamente() {
		java.util.List<String> nomesProj = java.util.List.of("XY", "YZ");
		empresa.adicionarProjeto(nomesProj.get(0));
		empresa.adicionarProjeto(nomesProj.get(1));
		Iterator<Projeto> itera = empresa.obterProjetos().iterator();
		assertEquals(0, empresa.obterProjetos().size());
		assertEquals(2, empresa.obterProjetos().size());
		assertEquals(nomesProj.get(0), itera.next().obterNome());
		assertEquals(nomesProj.get(1), itera.next().obterNome());
	}

	@Test()
	public void osProjetosNaoDevemterNomesDuplicados() {
		java.util.List<String> nomesProj = java.util.List.of("XY", "YZ");
		empresa.adicionarProjeto(nomesProj.get(0));
		empresa.adicionarProjeto(nomesProj.get(1));
		empresa.adicionarProjeto(nomesProj.get(0));
		assertEquals(0, empresa.obterProjetos().size());
	}

	@Test()
	public void possoAdicionarMultiplosFuncionarios() {
		java.util.List<String> nomesFunc = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
		assertEquals(0, empresa.obterFuncionarios().size());
		assertEquals(2, empresa.obterFuncionarios().size());
	}

	@Test()
	public void possoAdicionarMultiplosProjetos() {
		java.util.List<String> nomesProj = java.util.List.of("XY", "YZ");
		empresa.adicionarProjeto(nomesProj.get(0));
		empresa.adicionarProjeto(nomesProj.get(1));
		assertEquals(0, empresa.obterProjetos().size());
		assertEquals(2, empresa.obterProjetos().size());
	}

	@Test()
	public void umFuncionarioDeveSerCriadoCorretamente() {
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		Iterator<Funcionario> itera = empresa.obterFuncionarios().iterator();
		Funcionario primeiroFuncionario = itera.next();
		assertEquals(0, empresa.obterFuncionarios().size());
		assertEquals(1, empresa.obterFuncionarios().size());
		assertEquals(nomeFuncionario, primeiroFuncionario.obterNome());
	}

	@Test()
	public void umProjetoDeveSerCriadoCorretamente() {
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		Iterator<Projeto> itera = empresa.obterProjetos().iterator();
		Projeto primeiroProjeto = itera.next();
		assertEquals(0, empresa.obterProjetos().size());
		assertEquals(1, empresa.obterProjetos().size());
		assertEquals(nomeProjeto, primeiroProjeto.obterNome());
	}

	@Test()
	public void umaEmpresaCriadaDeveTerUmNome() {
		assertEquals(nomeEmpresa, empresa.obterNome());
	}

	@Test()
	public void umaEmpresaCriadaDeveTerUmaListaDeFuncionariosVazia() {
		assertEquals(0, empresa.obterFuncionarios().size());
	}

	@Test()
	public void umaEmpresaCriadaDeveTerUmaListaDeProjetosVazia() {
		assertEquals(0, empresa.obterProjetos().size());
	}
}
