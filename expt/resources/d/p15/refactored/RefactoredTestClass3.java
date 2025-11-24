import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Dinheiro dezCHF;

	@Before()
	public void setup() {
		dezCHF = Delegates.criaDezCHF();
	}

	@Test()
	public void criaDinheiroCHF() {
		assertEquals(1000, dezCHF.obterQuantiaEmEscala().intValue());
		assertEquals(Moeda.CHF, dezCHF.obterMoeda());
	}

	@Test()
	public void stringDinheiroDezCHF() {
		String formatado = dezCHF.toString();
		assertEquals("10,00 CHF", formatado);
	}
}
