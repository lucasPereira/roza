import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	@Before()
	public void setup() {
		this.conta = TestHelper.criarNovaConta();
	}

	@Test()
	public void calcularSaldoZerado() {
		ValorMonetario valorMonetario = conta.calcularSaldo();
		assertEquals("0,00", valorMonetario.formatado());
	}

	@Test()
	public void obterAgencia() {
		Agencia agencia = conta.obterAgencia();
		assertEquals("Trindade", agencia.obterNome());
	}

	@Test()
	public void obterIdentificador() {
		String identificador = conta.obterIdentificador();
		assertEquals("0001-5", identificador);
	}

	@Test()
	public void obterTitular() {
		String titular = conta.obterTitular();
		assertEquals("Maria", titular);
	}
}
