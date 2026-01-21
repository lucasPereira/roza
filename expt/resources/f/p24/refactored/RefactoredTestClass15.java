import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass15 {

	private String nomeEmpresa;

	private Empresa empresa;

	private java.util.List<String> nomesFunc;

	@Before()
	public void setup() {
		nomeEmpresa = "DelaRocaCompany";
		empresa = new Empresa(nomeEmpresa);
		nomesFunc = java.util.List.of("XY", "YZ");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
	}

	@Test()
	public void deveLocalizarEObterOutroProjetoPeloNome() {
		assertEquals(nomesFunc.get(1), empresa.obterFuncionarioPeloNome(nomesFunc.get(1)).obterNome());
	}

	@Test()
	public void deveLocalizarEObterProjetoPeloNome() {
		assertEquals(nomesFunc.get(0), empresa.obterFuncionarioPeloNome(nomesFunc.get(0)).obterNome());
	}
}
