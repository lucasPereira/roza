import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("empresa");
	}

	@Test()
	public void adicionaProjeto2() {
		Projeto projeto2 = new Projeto("Projeto02");
		empresa.adicionaProjeto(projeto2);
		assertTrue(empresa.temProjeto(projeto2));
	}

	@Test()
	public void criaempresa() {
		assertEquals("empresa", empresa.nome());
	}
}
