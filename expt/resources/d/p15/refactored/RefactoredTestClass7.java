import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private ValorMonetario baseBRL;

	@Before()
	public void setup() {
		baseBRL = new ValorMonetario(Moeda.BRL);
	}

	@Test()
	public void criaValorMonetarioNegativo() {
		Dinheiro dezReais = Delegates.criaDezReais();
		ValorMonetario menosDez = baseBRL.subtrair(dezReais);
		assertTrue(menosDez.negativo());
	}

	@Test()
	public void criaValorMonetarioPositivo() {
		Dinheiro dezReais = Delegates.criaDezReais();
		ValorMonetario dez = baseBRL.somar(dezReais);
		assertFalse(dez.negativo());
	}

	@Test()
	public void criaValorMonetarioZero() {
		assertTrue(baseBRL.zero());
	}

	@Test()
	public void stringValorMonetarioNegativo() {
		Dinheiro dezReais = Delegates.criaDezReais();
		ValorMonetario dez = baseBRL.subtrair(dezReais);
		assertEquals("-10,00 BRL", dez.toString());
	}

	@Test()
	public void stringValorMonetarioPositivo() {
		Dinheiro dezReais = Delegates.criaDezReais();
		ValorMonetario dez = baseBRL.somar(dezReais);
		assertEquals("+10,00 BRL", dez.toString());
	}

	@Test()
	public void stringValorMonetarioZero() {
		assertEquals("0,00", baseBRL.toString());
	}
}
