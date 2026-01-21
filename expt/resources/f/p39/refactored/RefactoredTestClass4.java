import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void criarFuncionarioSemNome() {
		Funcionario funcionarioVazio = new Funcionario("");
		assertEquals("", funcionarioVazio.pegarNome());
	}
}
