import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void verificarBaseFracionaria() {
		Dinheiro reais = new Dinheiro(Moeda.BRL, 20, 20);
		assertEquals(Moeda.BRL, reais.obterMoeda());
	}
}
