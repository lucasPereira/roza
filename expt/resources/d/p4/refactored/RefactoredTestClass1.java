import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void criarDinheiroEVerificarMoeda() {
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 10, 0);
		assertEquals(dinheiro.obterMoeda(), Moeda.BRL);
	}
}
