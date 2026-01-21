import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void testeAtribuiFuncionarioNull() {
		Projeto proj = new Projeto("projeto x");
		Projeto proj = new Projeto("projeto x");
		boolean result = proj.atribuiFuncionario(null);
		assertEquals(false, result);
	}
}
