import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void criarValorMonetarioBRLETestarSaldoZero() {
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		assertTrue(valorMonetario.zero());
	}
}
