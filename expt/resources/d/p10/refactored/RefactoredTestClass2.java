import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void testCriaDinheiroNegativo() {
		Dinheiro dinheiroCemReaisNegativo = new Dinheiro(Moeda.BRL, -100, 0);
		assertEquals(10000, dinheiroCemReaisNegativo.obterQuantiaEmEscala().intValue());
		assertEquals("-100,00 BRL", dinheiroCemReaisNegativo.negativo().formatado());
	}
}
