import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void atribuidProjetoNull() {
		Funcionario func = new Funcionario("João");
		boolean result = func.atribuiProjeto(null);
		assertEquals(false, result);
	}
}
