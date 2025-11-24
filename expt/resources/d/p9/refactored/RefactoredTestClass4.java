import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void formatadoSemMoeda() {
		Dinheiro dinheiroZerado = new Dinheiro(Moeda.BRL, 0, 00);
		String formatado = dinheiroZerado.formatado();
		assertEquals("0,00", formatado);
	}
}
