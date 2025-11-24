import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void testeConstrutor() {
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		assertTrue(valorMonetario.zero());
		assertEquals("0,00", valorMonetario.formatado());
	}
}
