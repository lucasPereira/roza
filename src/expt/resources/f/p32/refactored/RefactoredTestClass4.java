import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void verificarNomeEmpresa() {
		Empresa empresa = DelegatedSetups.novaEmpresa();
		assertEquals("Rizota", empresa.getNome());
	}
}
