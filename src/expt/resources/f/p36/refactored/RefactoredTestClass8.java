import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private Funcionario joao;

	@Before()
	public void setup() {
		joao = empresa.criarFuncionario("Joao");
	}

	@Test()
	public void empresaCriarDoisFuncionarios() {
		Funcionario pedro = empresa.criarFuncionario("Pedro");
		assertEquals(0, joao.getId());
		assertEquals("Joao", joao.getNome());
		assertEquals(1, pedro.getId());
		assertEquals("Pedro", pedro.getNome());
	}

	@Test()
	public void empresaCriarFuncionario() {
		assertEquals(0, joao.getId());
		assertEquals("Joao", joao.getNome());
	}
}
