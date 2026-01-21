import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa();
		projeto = empresa.addProjeto("Projeto 1");
	}

	@Test()
	public void criarPrimeiroProjeto() {
		assertEquals(1, projeto.id().intValue());
		assertEquals("Projeto 1", projeto.nome());
	}

	@Test()
	public void criarSegundoProjeto() {
		Projeto projeto2 = empresa.addProjeto("Projeto 2");
		assertEquals(1, projeto.id().intValue());
		assertEquals(2, projeto2.id().intValue());
	}
}
