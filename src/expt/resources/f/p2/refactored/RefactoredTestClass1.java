import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void testaFuncionariosDiferentesComNomesIguais() {
		Funcionario joao = new Funcionario("João");
		Funcionario joao2 = new Funcionario("João");
		assertFalse(joao.equals(joao2));
	}
}
