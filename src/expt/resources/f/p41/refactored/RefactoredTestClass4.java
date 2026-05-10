import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Empresa empresaPaulo;

	@Before()
	public void setup() {
		empresaPaulo = new Empresa("Restaurante Massashin");
		empresaPaulo.contrataFunc("Igor");
	}

	@Test()
	public void contrataFuncionarioAnonimo() {
		empresaPaulo.contrataFunc("");
		assertEquals(1, empresaPaulo.numFuncionarios());
	}

	@Test()
	public void contrataFuncionarioReal() {
		empresaPaulo.contrataFunc("Lucas");
		assertEquals(2, empresaPaulo.numFuncionarios());
	}

	@Test()
	public void criaEmpresaAnonima() {
		String nomeEmpresa = "";
		empresaHiroki = new Empresa(nomeEmpresa);
		assertEquals("Anonimo", empresaHiroki.geraNomeEmpresa());
		assertEquals(0, empresaHiroki.numFuncionarios());
	}

	@Test()
	public void geraEmpresaComNome() {
		String nomeEmpresa = "Restaurante Massashin";
		empresaHiroki = new Empresa(nomeEmpresa);
		assertEquals("Restaurante Massashin", empresaPaulo.geraNomeEmpresa());
		assertEquals(0, empresaHiroki.numFuncionarios());
	}
}
