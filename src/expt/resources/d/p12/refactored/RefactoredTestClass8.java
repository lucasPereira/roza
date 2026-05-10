import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private Dinheiro dindin;

	@Before()
	public void setup() {
		dindin = new Dinheiro(Moeda.BRL, 50, 2);
	}

	@Test()
	public void equalsDinheiroDiferentesObjetos() {
		Boolean igual = dindin.equals(Moeda.BRL);
		assertEquals(false, igual);
	}

	@Test()
	public void negativo() {
		ValorMonetario vm = dindin.negativo();
		assertEquals("-50,02 BRL", vm.formatado());
	}

	@Test()
	public void positivo() {
		ValorMonetario vm = dindin.positivo();
		assertEquals("+50,02 BRL", vm.formatado());
	}
}
