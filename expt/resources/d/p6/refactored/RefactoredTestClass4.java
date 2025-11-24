import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void criaValorMonetario() {
		Moeda moeda = Moeda.BRL;
		ValorMonetario valorMonetario = new ValorMonetario(moeda);
		assertEquals(TesteHelper.quantiaFormatadoComMoeda(0, 0, moeda), valorMonetario.formatarSemSinal());
	}
}
