import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void testarQuinzeReaisFormatado() {
		Dinheiro quinzeReais = new Dinheiro(Moeda.BRL, 15, 0);
		assertEquals("15,00 BRL", quinzeReais.formatado());
	}
}
