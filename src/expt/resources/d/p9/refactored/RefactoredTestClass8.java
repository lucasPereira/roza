import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private Dinheiro dinheiroCemReais;

	@Before()
	public void setup() {
		dinheiroCemReais = TestHelper.criarDinheiroCemReais();
	}

	@Test()
	public void criaDinheiroComMoedaBRL() {
		assertEquals("100,00 BRL", dinheiroCemReais.formatado());
	}

	@Test()
	public void formatadoComMoeda() {
		String formatado = dinheiroCemReais.formatado();
		assertEquals("100,00 BRL", formatado);
	}

	@Test()
	public void obterQuantiaEmEscala() {
		Integer quantia = dinheiroCemReais.obterQuantiaEmEscala();
		assertEquals(10000, quantia.intValue());
	}

	@Test()
	public void valorMonetarioNegativo() {
		ValorMonetario valorMonetario = dinheiroCemReais.negativo();
		assertEquals("-100,00 BRL", valorMonetario.toString());
	}
}
