import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void testeCriacao10reais() {
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		assertEquals("10,00 BRL", dezReais.formatado());
		assertEquals(1000, dezReais.obterQuantiaEmEscala().intValue());
		assertEquals(Moeda.BRL, dezReais.obterMoeda());
		assertEquals("-10,00 BRL", dezReais.negativo().formatado());
	}
}
