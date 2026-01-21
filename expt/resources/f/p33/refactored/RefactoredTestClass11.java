import org.junit.Test;

public class RefactoredTestClass11 {

	@Test()
	public void listaDeFuncionariosVazia() {
		assertEquals(new ArrayList<Funcionario>(), empresa1.getFuncionarios());
	}
}
