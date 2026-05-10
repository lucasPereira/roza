import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void testeCriacao50Centavos() {
		Dinheiro cinquentaCentavos = new Dinheiro(Moeda.BRL, 0, 50);
		assertEquals("0,50 BRL", cinquentaCentavos.formatado());
		assertEquals(50, cinquentaCentavos.obterQuantiaEmEscala().intValue());
		assertEquals(Moeda.BRL, cinquentaCentavos.obterMoeda());
	}
}
