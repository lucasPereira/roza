import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void criarDinheiro() {
		Dinheiro dindin = new Dinheiro(Moeda.BRL, 0, 0);
		Moeda moeda = dindin.obterMoeda();
		assertEquals(Moeda.BRL, moeda);
	}
}
