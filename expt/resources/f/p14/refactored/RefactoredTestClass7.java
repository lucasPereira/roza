import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void criarFuncionario() {
		Funcionario func = new Funcionario("João da Silva");
		assertEquals("João da Silva", func.getNome());
	}
}
