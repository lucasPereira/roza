import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private ValorMonetario valor;

	private Dinheiro dinheiro;

	@Before()
	public void setup() {
		valor = new ValorMonetario(Moeda.BRL);
		dinheiro = new Dinheiro(Moeda.BRL, 10, 0);
	}

	@Test()
	public void somarDinheiro() {
		ValorMonetario valorResultante = valor.somar(dinheiro);
		assertFalse(valorResultante.negativo());
	}

	@Test()
	public void subtrairDinheiro() {
		ValorMonetario valorResultante = valor.subtrair(dinheiro);
		assertTrue(valorResultante.negativo());
	}
}
