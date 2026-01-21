import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void testDesigualdadeDeFuncionarios() {
		Funcionario funcionario = new Funcionario(1, "Mark");
		Funcionario funcionarioJoao = new Funcionario(2, "Joao");
		assertNotEquals(funcionario, funcionarioJoao);
	}
}
