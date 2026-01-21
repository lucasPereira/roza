import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass19 {

	private Funcionario rafael;

	@Before()
	public void setup() {
		rafael = new Funcionario("Rafael");
	}

	@Test()
	public void adicionaProjeto2() {
		Projeto projeto2 = new Projeto("Projeto 2");
		rafael.adicionaProjeto(projeto2);
		assertTrue(rafael.temProjeto(projeto2));
	}

	@Test()
	public void criaRafael() {
		assertEquals("Rafael", rafael.getNome());
	}
}
