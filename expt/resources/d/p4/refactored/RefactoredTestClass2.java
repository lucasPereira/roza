import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void criarDinheiroEVerificarQuantiaZero() {
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 0, 0);
		assertEquals(dinheiro.obterQuantiaEmEscala().intValue(), 0);
	}
}
