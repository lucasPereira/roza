import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("empresa");
	}

	@Test()
	public void adicionaFuncionario2() {
		Funcionario lucas = new Funcionario("Funcionario02");
		empresa.adicionaFuncionario(lucas);
		assertTrue(empresa.temFuncionario(lucas));
	}

	@Test()
	public void criaempresa() {
		assertEquals("empresa", empresa.nome());
	}
}
