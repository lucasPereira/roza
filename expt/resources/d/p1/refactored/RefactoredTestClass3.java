import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void testValorMonetarioPositivo() {
		Dinheiro dinheiro_banco = new Dinheiro(moeda_dinheiro, qtde_dinheiro, 0);
		ValorMonetario valormonetario_positivo = dinheiro_banco.positivo();
		ValorMonetario valor_monetario_setup = new ValorMonetario(moeda_dinheiro);
		assertEquals(valor_monetario_setup, valormonetario_positivo);
	}
}
