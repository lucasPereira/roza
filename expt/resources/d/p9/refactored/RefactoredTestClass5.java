import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void valorMonetarioPositivo() {
		Dinheiro dinheiroCemReais = new Dinheiro(Moeda.BRL, -100, 00);
		ValorMonetario valorMonetario = dinheiroCemReais.positivo();
		assertEquals("+100,00 BRL", valorMonetario.toString());
	}
}
