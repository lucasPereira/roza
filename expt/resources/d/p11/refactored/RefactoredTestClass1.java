import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass1 {

	private Dinheiro reais100Centavos20;

	@Before()
	public void setup() {
		reais100Centavos20 = new Dinheiro(Moeda.BRL, 100, 20);
	}

	@Test()
	public void testeDinheiroEquals() {
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 100, 20);
		assertTrue(reais100Centavos20.equals(dinheiro));
	}

	@Test()
	public void testeDinheiroFormatado() {
		assertEquals("100,20 BRL", TestHelper.fazDinheiroBRL100Inteiro20Fracionado().formatado());
	}

	@Test()
	public void testeDinheiroNegativo() {
		assertEquals("-100,20 BRL", reais100Centavos20.negativo().toString());
	}

	@Test()
	public void testeDinheiroObterMoeda() {
		assertEquals(Moeda.BRL, reais100Centavos20.obterMoeda());
	}

	@Test()
	public void testeDinheiroObterQuantiaEmEscala() {
		Dinheiro reais100centavos20 = new Dinheiro(Moeda.BRL, 100, 20);
		assertEquals(10020, reais100centavos20.obterQuantiaEmEscala().intValue());
	}

	@Test()
	public void testeDinheiroPositivo() {
		assertEquals("+100,20 BRL", reais100Centavos20.positivo().toString());
	}

	@Test()
	public void testeDinheiroToString() {
		assertEquals("100,20 BRL", reais100Centavos20.toString());
	}
}
