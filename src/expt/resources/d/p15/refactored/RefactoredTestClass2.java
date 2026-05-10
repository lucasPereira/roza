import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private Dinheiro dezReais;

	@Before()
	public void setup() {
		dezReais = Delegates.criaDezReais();
	}

	@Test()
	public void criaDinheiroBRL() {
		assertEquals(1000, dezReais.obterQuantiaEmEscala().intValue());
		assertEquals(Moeda.BRL, dezReais.obterMoeda());
	}

	@Test()
	public void stringDinheiroDezReais() {
		String formatado = dezReais.toString();
		assertEquals("10,00 BRL", formatado);
	}
}
