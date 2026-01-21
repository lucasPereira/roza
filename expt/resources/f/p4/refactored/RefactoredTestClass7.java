import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private Funcionario joao;

	private Funcionario carlos;

	@Before()
	public void setup() {
		joao = new Funcionario();
		carlos = new Funcionario();
	}

	@Test()
	public void chaveSequencialDoisFuncionarios() {
		assertTrue(joao.obtemChave() < carlos.obtemChave());
	}

	@Test()
	public void chaveUnicaDoisFuncionarios() {
		assertNotEquals(joao.obtemChave(), carlos.obtemChave());
	}
}
