import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private Integer inteiro;

	private Integer fracionado;

	private Dinheiro dinheiro;

	@Before()
	public void setup() {
		inteiro = new Integer(500);
		fracionado = new Integer(0);
		dinheiro = new Dinheiro(Moeda.BRL, inteiro, fracionado);
	}

	@Test()
	public void criaDinheiro() {
		Integer inteiro = new Integer(500);
		Integer fracionado = new Integer(25);
		Moeda moeda = Moeda.BRL;
		Dinheiro dinheiro = new Dinheiro(moeda, inteiro, fracionado);
		assertEquals(TesteHelper.quantiaFormatadoComMoeda(inteiro, fracionado, moeda), dinheiro.formatado());
	}

	@Test()
	public void criaDinheiroZero2() {
	}

	@Test()
	public void obterTipoDeMoedaDoDinheiro_DeveSerBRL() {
		Moeda tipoDaMoeda = dinheiro.obterMoeda();
		assertEquals(Moeda.BRL, tipoDaMoeda);
	}

	@Test()
	public void pegaValorNegativoDoDinheiro() {
		ValorMonetario negativo = dinheiro.negativo();
		assertTrue(new Boolean(negativo.negativo()));
	}

	@Test()
	public void pegaValorPositivoDoDinheiro() {
		ValorMonetario positivo = dinheiro.positivo();
		assertTrue(new Boolean(!positivo.negativo()));
	}
}
