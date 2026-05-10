import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass18 {

	private String nomeEmpresa;

	private Empresa empresa;

	private String nomeProjeto;

	@Before()
	public void setup() {
		nomeEmpresa = "DelaRocaCompany";
		empresa = new Empresa(nomeEmpresa);
		nomeProjeto = "XY";
	}

	@Test()
	public void aBuscaAoProjetoPeloNomeNaoPodeSerComNomeDeProjetoInexistente() {
		empresa.adicionarProjeto(nomeProjeto);
		empresa.obterProjetoPeloNome("ObjetivoZ").obterNome();
		assertEquals(0, empresa.obterProjetos().size());
		assertEquals(1, empresa.obterProjetos().size());
	}

	@Test()
	public void aBuscaAoProjetoPeloNomeNaoPodeSerComNomeNulo() {
		empresa.adicionarProjeto(nomeProjeto);
		empresa.obterProjetoPeloNome(null);
		assertEquals(1, empresa.obterProjetos().size());
	}

	@Test()
	public void aBuscaAoProjetoPeloNomeNaoPodeSerComNomeVazio() {
		empresa.adicionarProjeto(nomeProjeto);
		empresa.obterProjetoPeloNome("");
		assertEquals(1, empresa.obterProjetos().size());
	}

	@Test()
	public void adicionarUmFuncionarioDaEmpresaAoProjeto() {
		Projeto projeto = new Projeto(nomeProjeto);
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		projeto.adicionarFuncionario(nomeFuncionario);
		assertEquals(0, empresa.obterFuncionarios().size());
		assertEquals(1, empresa.obterFuncionarios().size());
		assertEquals(0, projeto.obterFuncionarios().size());
		assertEquals(1, projeto.obterFuncionarios().size());
		assertEquals(empresa.obterFuncionarioPeloNome(nomeFuncionario), projeto.obterFuncionarioPeloNome(nomeFuncionario));
	}
}
