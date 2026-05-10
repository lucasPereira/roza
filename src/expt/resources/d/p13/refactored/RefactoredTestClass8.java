import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private Banco bancoDoBrasil;

	private Agencia trindade;

	@Before()
	public void setup() {
		bancoDoBrasil = AjudanteDeTestes.retornaBancoDoBrasil();
		trindade = bancoDoBrasil.criarAgencia("Trindade");
	}

	@Test()
	public void testeCriaConta() {
		Conta contaJohn = trindade.criarConta("John");
		assertEquals("John", contaJohn.obterTitular());
		assertEquals(trindade, contaJohn.obterAgencia());
	}

	@Test()
	public void testeCriacaoAgencia() {
		assertEquals("001", trindade.obterIdentificador());
		assertEquals("Trindade", trindade.obterNome());
		assertEquals(bancoDoBrasil, trindade.obterBanco());
	}
}
