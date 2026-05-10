import org.junit.Test;

public class RefactoredTestClass19 {

	@Test()
	public void testeCriaFuncionarioNomeDiferente() {
		String nome = "Maria";
		Funcionario func = new Funcionario(nome);
		String result = func.getNome();
		assertEquals(nome, result);
	}
}
