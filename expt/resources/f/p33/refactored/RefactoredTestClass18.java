import org.junit.Test;

public class RefactoredTestClass18 {

	@Test()
	public void nomeDoProjetoEDaEmpresa() {
		assertEquals("Projeto1", projeto1.getNome());
		assertEquals(empresa1, projeto1.getEmpresa());
	}
}
