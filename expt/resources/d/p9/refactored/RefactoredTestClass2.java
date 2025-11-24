import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void criaDinheiroComMoedaCHF() {
		Dinheiro dinheiroCemFrancos = new Dinheiro(Moeda.CHF, 100, 00);
		assertEquals("100,00 CHF", dinheiroCemFrancos.formatado());
	}
}
