import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass18 {

	@Before()
	public void setup() {
		empresa.iniciaProjeto("ProjetoTeste");
	}

	@Test()
	public void iniciaMultiplosProjetos() {
		empresa.iniciaProjeto("ProjetoTeste");
		assertEquals(2, empresa.getProjetos().size());
	}

	@Test()
	public void iniciaUmProjeto() {
		assertEquals(1, empresa.getProjetos().size());
	}
}
