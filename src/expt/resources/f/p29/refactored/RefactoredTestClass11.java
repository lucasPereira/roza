import org.junit.Test;

public class RefactoredTestClass11 {

	@Test()
	public void nomeDaEmpresaErrado() {
		assertNotEquals("E", newEmpresa.getNome());
	}
}
