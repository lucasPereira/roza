import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void criarValorMonetarioCHFETestarSaldoZero() {
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.CHF);
		assertTrue(valorMonetario.zero());
	}
}
