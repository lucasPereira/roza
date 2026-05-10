import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private ValorMonetario valorMonetario;

	private Dinheiro reais10Centavos20;

	@Before()
	public void setup() {
		valorMonetario = new ValorMonetario(Moeda.BRL);
		reais10Centavos20 = new Dinheiro(Moeda.BRL, 10, 20);
	}

	@Test()
	public void testeEquals() {
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		assertTrue(this.valorMonetario.equals(valorMonetario));
	}

	@Test()
	public void testeFormatado() {
		assertEquals("0,00", valorMonetario.formatado());
	}

	@Test()
	public void testeFormatarComSinal() {
		assertEquals("+0,00", valorMonetario.formatarComSinal());
	}

	@Test()
	public void testeFormatarSemSinal() {
		assertEquals("0,00", valorMonetario.formatarSemSinal());
	}

	@Test()
	public void testeNegativo() {
		assertFalse(valorMonetario.negativo());
	}

	@Test()
	public void testeObterQuantia() {
		assertEquals("0,00", valorMonetario.obterQuantia().formatado());
	}

	@Test()
	public void testeSomar() {
		valorMonetario = valorMonetario.somar(reais10Centavos20);
		assertEquals("10,20 BRL", valorMonetario.obterQuantia().toString());
	}

	@Test()
	public void testeSubtrair() {
		valorMonetario = valorMonetario.subtrair(reais10Centavos20);
		assertEquals("-10,20 BRL", valorMonetario.obterQuantia().negativo().toString());
	}

	@Test()
	public void testeValorMonetarioToString() {
		assertEquals("0,00", valorMonetario.toString());
	}

	@Test()
	public void testeZero() {
		assertTrue(valorMonetario.zero());
	}
}
