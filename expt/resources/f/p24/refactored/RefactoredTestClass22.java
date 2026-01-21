import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass22 {

	private String nomeEmpresa;

	private Empresa empresa;

	private java.util.List<String> nomesFunc;

	@Before()
	public void setup() {
		nomeEmpresa = "DelaRocaCompany";
		empresa = new Empresa(nomeEmpresa);
		nomesFunc = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
	}

	@Test()
	public void aBuscaAoFuncionarioPeloNomeNaoPodeSerComNomeInexistente() {
		empresa.obterFuncionarioPeloNome("Jos�");
		assertEquals(0, empresa.obterFuncionarios().size());
	}

	@Test()
	public void aBuscaAoFuncionarioPeloNomeNaoPodeSerComNomeNulo() {
		empresa.obterFuncionarioPeloNome(null);
		assertEquals(0, empresa.obterFuncionarios().size());
	}

	@Test()
	public void aBuscaAoFuncionarioPeloNomeNaoPodeSerComNomeVazio() {
		empresa.obterFuncionarioPeloNome("");
		assertEquals(0, empresa.obterFuncionarios().size());
	}

	@Test()
	public void deveLocalizarEObterFuncionarioPeloNome() {
		assertEquals(nomesFunc.get(0), empresa.obterFuncionarioPeloNome(nomesFunc.get(0)).obterNome());
	}

	@Test()
	public void deveLocalizarEObterOutroFuncionarioPeloNome() {
		assertEquals(nomesFunc.get(1), empresa.obterFuncionarioPeloNome(nomesFunc.get(1)).obterNome());
	}
}
