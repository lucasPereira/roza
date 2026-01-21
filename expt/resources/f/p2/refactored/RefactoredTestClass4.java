import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa();
		empresa.adicionarProjeto("Projeto Manhattan");
	}

	@Test()
	public void testeAdicionarDoisProjetos() {
		empresa.adicionarProjeto("Projeto Verão");
		assertEquals(2, empresa.obterNumeroDeProjetos());
	}

	@Test()
	public void testeAdicionarUmProjeto() {
		assertEquals(1, empresa.obterNumeroDeProjetos());
	}
}
