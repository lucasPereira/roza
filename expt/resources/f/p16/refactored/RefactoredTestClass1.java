import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass1 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa();
	}

	@Test()
	public void adicionarFuncionario() {
		empresa.addFuncionario("Joao");
		assertEquals(1, empresa.numFuncionarios().intValue());
	}

	@Test()
	public void adicionarProjeto() {
		empresa.addProjeto("Projeto 1");
		assertEquals(1, empresa.numProjetos().intValue());
	}
}
