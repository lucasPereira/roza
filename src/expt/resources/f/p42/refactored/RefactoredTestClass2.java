import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void testeCriaEmpresaDaMaria() {
		Empresa empresa = new Empresa("Empresa da Maria");
		assertEquals("Empresa da Maria", empresa.obterEmpresa());
	}
}
