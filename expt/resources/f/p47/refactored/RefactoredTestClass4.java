import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("Google");
	}

	@Test()
	public void testConstrutor() {
		assertEquals(empresa.getNome(), "Google");
		assertTrue(empresa.getProjetos() != null);
		assertTrue(empresa.getFuncionarios() != null);
		assertEquals(empresa.getProjetos().size(), 0);
		assertEquals(empresa.getFuncionarios().size(), 0);
	}

	@Test()
	public void testCreateProjeto() {
		Projeto projeto = empresa.createProjeto("projeto1");
		assertEquals(projeto.getNome(), "projeto1");
		assertEquals(projeto.getEmpresa(), empresa);
		assertTrue(projeto.getFuncionarios() != null);
		assertTrue(projeto.getOcorrencias() != null);
		assertEquals(projeto.getFuncionarios().size(), 0);
		assertEquals(projeto.getOcorrencias().size(), 0);
	}
}
