import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	private Banco bb;

	@Before()
	public void setup() {
		bb = this.novoBanco();
	}

	@Test()
	public void bancoDoBrasilErro() {
		assertNotEquals("Banco Brasil", bb.obterNome());
		assertNotEquals(Moeda.USD, bb.obterMoeda());
	}

	@Test()
	public void criacaoDeBanco() {
		assertEquals(BANCO_DO_BRASIL, bb.obterNome());
		assertEquals(Moeda.BRL, bb.obterMoeda());
	}
}
