import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void criaDinheiroComMoedaUSD() {
		Dinheiro dinheiroCemDolares = TestHelper.criarDinheiroCemReais();
		assertEquals("100,00 USD", dinheiroCemDolares.formatado());
	}
}
