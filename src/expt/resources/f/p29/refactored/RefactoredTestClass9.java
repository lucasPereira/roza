import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void naoExisteProjeto() {
		assertEquals(null, newEmpresa.getProjeto(1));
	}
}
