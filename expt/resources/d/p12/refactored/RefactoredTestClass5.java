import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void equalsDinheiroFalse() {
		Dinheiro dindin = new Dinheiro(Moeda.BRL, 50, 2);
		Dinheiro outro = new Dinheiro(Moeda.BRL, 1000, 0);
		Boolean igual = dindin.equals(outro);
		assertEquals(false, igual);
	}
}
