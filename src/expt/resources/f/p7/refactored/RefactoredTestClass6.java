import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void newEmpresa() {
		Empresa minhaEmpresa = new Empresa();
		assertTrue(minhaEmpresa.getFuncionarios().isEmpty());
		assertTrue(minhaEmpresa.getProjetos().isEmpty());
	}
}
