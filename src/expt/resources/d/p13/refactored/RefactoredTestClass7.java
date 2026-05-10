import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void testeSaldoZero() {
		Conta contaJohn = AjudanteDeTestes.retornaContaJohnBancoDoBrasilAgTrindade();
		ValorMonetario valor = new ValorMonetario(Moeda.BRL);
		ValorMonetario saldoJohn = contaJohn.calcularSaldo();
		assertEquals(valor, saldoJohn);
	}
}
