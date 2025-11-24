import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void testarValorDezReais() {
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		assertEquals(Moeda.BRL, dezReais.obterMoeda());
	}
}
