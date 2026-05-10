import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa();
		empresa.adicionarFuncionario("João");
	}

	@Test()
	public void testeAdicionarDoisFuncionarios() {
		empresa.adicionarFuncionario("Maria");
		assertEquals(2, empresa.obterNumeroDeFuncionarios());
	}

	@Test()
	public void testeAdicionarUmFuncionario() {
		assertEquals(1, empresa.obterNumeroDeFuncionarios());
	}
}
