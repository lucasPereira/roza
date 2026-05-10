import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	private Dinheiro DezReais;

	private ValorMonetario valorMonetario;

	@Before()
	public void setup() {
		DezReais = new Dinheiro(Moeda.BRL, 10, 00);
		valorMonetario = new ValorMonetario(Moeda.BRL);
	}

	@Test()
	public void somarValorMonetario() {
		ValorMonetario monetario = valorMonetario.somar(DezReais);
		assertEquals("+10,00 BRL", monetario.formatado());
	}

	@Test()
	public void subtrairValorMonetario() {
		ValorMonetario monetario = valorMonetario.subtrair(DezReais);
		assertEquals("-10,00 BRL", monetario.formatado());
	}
}
