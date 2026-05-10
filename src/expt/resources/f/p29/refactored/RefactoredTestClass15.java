import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass15 {

	private Projeto newProjeto;

	@Before()
	public void setup() {
		newProjeto = new Projeto(newEmpresa, 1);
		newEmpresa.addProjeto(newProjeto);
	}

	@Test()
	public void adicionarProjeto() {
		assertEquals(newProjeto, newEmpresa.getProjeto(1));
	}

	@Test()
	public void numeroDeProjetos() {
		assertEquals(1, newEmpresa.getListaProjetos().size());
	}
}
