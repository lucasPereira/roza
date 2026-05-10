import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void criarFuncionarioSemNome() {
		Funcionario funcionarioSemNome = new Funcionario("");
		assertEquals("", funcionarioSemNome.getNome());
	}
}
