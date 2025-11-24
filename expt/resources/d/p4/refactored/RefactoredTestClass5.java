import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void criarValorMonetarioUSDETestarSaldoZero() {
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.USD);
		assertTrue(valorMonetario.zero());
	}
}
