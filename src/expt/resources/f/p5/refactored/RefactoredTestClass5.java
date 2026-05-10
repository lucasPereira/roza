import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void retornaInfoFuncionario() {
		Funcionario umFuncionario = new Funcionario("Xisto");
		assertEquals("Xisto", umFuncionario.getNome());
	}
}
