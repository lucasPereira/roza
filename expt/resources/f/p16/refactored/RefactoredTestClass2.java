import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa();
		funcionario = empresa.addFuncionario("Joao");
	}

	@Test()
	public void criarPrimeiroFuncionario() {
		assertEquals(1, funcionario.id().intValue());
		assertEquals("Joao", funcionario.nome());
	}

	@Test()
	public void criarSegundoFuncionario() {
		Funcionario funcionario2 = empresa.addFuncionario("Pedro");
		assertEquals(1, funcionario.id().intValue());
		assertEquals(2, funcionario2.id().intValue());
	}
}
