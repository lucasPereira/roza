import org.junit.Test;

public class RefactoredTestClass18 {

	@Test()
	public void testeCriaFuncionario() {
		String nome = "João";
		Funcionario func = new Funcionario(nome);
		String result = func.getNome();
		assertEquals(nome, result);
	}
}
