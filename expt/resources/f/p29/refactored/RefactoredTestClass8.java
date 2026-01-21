import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void naoExisteFuncionario() {
		assertEquals(null, newEmpresa.getFuncionario(1));
	}
}
