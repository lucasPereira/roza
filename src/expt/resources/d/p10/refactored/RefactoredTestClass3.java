import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void testCriaDinheiroPositivo() {
		Dinheiro dinheiroCemReais = new Dinheiro(Moeda.BRL, 100, 0);
		assertEquals(10000, dinheiroCemReais.obterQuantiaEmEscala().intValue());
		assertEquals(Moeda.BRL, dinheiroCemReais.obterMoeda());
		assertEquals("+100,00 BRL", dinheiroCemReais.positivo().formatado());
	}
}
