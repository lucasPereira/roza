import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void equalsDinheiroTrue() {
		Dinheiro dindin = new Dinheiro(Moeda.BRL, 50, 2);
		Dinheiro outro = new Dinheiro(Moeda.BRL, 50, 2);
		Boolean igual = dindin.equals(outro);
		assertEquals(true, igual);
	}
}
