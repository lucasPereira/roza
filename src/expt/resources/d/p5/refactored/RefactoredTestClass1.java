import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass1 {

	private Moeda moeda;

	private Dinheiro dindin;

	@Before()
	public void setup() {
		moeda = Moeda.BRL;
		dindin = new Dinheiro(moeda, new Integer(5), new Integer(50));
	}

	@Test()
	public void creationTest() {
		assertEquals(moeda, dindin.obterMoeda());
		assertEquals(dindin.obterQuantiaEmEscala(), new Integer(550));
	}

	@Test()
	public void formatTest() {
		String str = dindin.formatado();
		assertEquals("5,50 BRL", str);
	}
}
