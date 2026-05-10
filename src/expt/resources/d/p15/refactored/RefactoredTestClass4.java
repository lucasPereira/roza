import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Dinheiro dezDolates;

	@Before()
	public void setup() {
		dezDolates = Delegates.criaDezDolares();
	}

	@Test()
	public void criaDinheiroUSD() {
		assertEquals(1000, dezDolates.obterQuantiaEmEscala().intValue());
		assertEquals(Moeda.USD, dezDolates.obterMoeda());
	}

	@Test()
	public void stringDinheiroDezDolares() {
		String formatado = dezDolates.toString();
		assertEquals("10,00 USD", formatado);
	}
}
