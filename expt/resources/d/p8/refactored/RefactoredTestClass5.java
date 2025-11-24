import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	private Dinheiro VinteReais;

	private ValorMonetario valorMonetario;

	@Before()
	public void setup() {
		VinteReais = new Dinheiro(Moeda.BRL, 20, 00);
		valorMonetario = new ValorMonetario(Moeda.BRL);
	}

	@Test()
	public void obterQuantiaValorMonetario() {
		ValorMonetario valor = valorMonetario.somar(VinteReais);
		assertEquals("20,00 BRL", valor.obterQuantia().formatado());
	}

	@Test()
	public void somarValorMonetario() {
		ValorMonetario valor = valorMonetario.somar(VinteReais);
		assertEquals("+20,00 BRL", valor.formatado());
	}

	@Test()
	public void subtrairValorMonetario() {
		ValorMonetario valor = valorMonetario.subtrair(VinteReais);
		assertEquals("-20,00 BRL", valor.formatado());
	}
}
