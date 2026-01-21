import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void testeCriaEmpresaDoJoao() {
		Empresa empresa = new Empresa("Empresa do Joao");
		assertEquals("Empresa do Joao", empresa.obterEmpresa());
	}
}
