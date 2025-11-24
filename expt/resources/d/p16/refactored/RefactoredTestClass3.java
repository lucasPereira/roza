import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Integer inteiro;

	private Integer fracionado;

	private Dinheiro quantia;

	@Before()
	public void setup() {
		inteiro = 100;
		fracionado = 50;
		quantia = new Dinheiro(Moeda.BRL, inteiro, fracionado);
	}

	@Test()
	public void dinheiroFormatado() {
		String dinheiroFormatado = quantia.formatado();
		assertEquals("100,50 BRL", dinheiroFormatado);
	}

	@Test()
	public void valorMonetarioNegativo() {
		ValorMonetario valorNegativo = quantia.negativo();
		assertEquals("-100,50 BRL", valorNegativo.formatado());
	}

	@Test()
	public void valorMonetarioPositivo() {
		ValorMonetario valorPositivo = quantia.positivo();
		assertEquals("+100,50 BRL", valorPositivo.formatado());
	}
}
