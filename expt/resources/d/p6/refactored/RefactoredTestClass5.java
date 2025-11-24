import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void somaQuantiaAoValorMonetario() {
		Moeda moeda = Moeda.BRL;
		Integer quinhentosDinheiros = new Integer(500);
		Integer zeroCentavosDeDinheiros = new Integer(10);
		ValorMonetario valor = new ValorMonetario(moeda);
		Dinheiro dinheiro = new Dinheiro(moeda, quinhentosDinheiros, zeroCentavosDeDinheiros);
		ValorMonetario valorSomado = valor.somar(dinheiro);
		String valorFormatado = new String(TesteHelper.quantiaFormatadoComMoeda(quinhentosDinheiros, zeroCentavosDeDinheiros, moeda));
		assertEquals(valorFormatado, valorSomado.formatarSemSinal());
	}
}
