import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass12 {

	private Conta contaJohn;

	@Before()
	public void setup() {
		contaJohn = AjudanteDeTestes.retornaContaJohnBancoDoBrasilAgTrindade();
	}

	@Test()
	public void testeObterAgencia() {
		String ag = contaJohn.obterAgencia().obterNome();
		assertEquals("Trindade", ag);
	}

	@Test()
	public void testeObterIdentificador() {
		String id = contaJohn.obterIdentificador();
		assertEquals("0001-4", id);
	}

	@Test()
	public void testeObterTitular() {
		String titular = contaJohn.obterTitular();
		assertEquals("John", titular);
	}
}
