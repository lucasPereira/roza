import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass10 {

	@Before()
	public void setup() {
		this.valorMonetarioBRL = new ValorMonetario(Moeda.BRL);
		this.dinheiroCemReais = TestHelper.criarDinheiroCemReais();
		this.dinheiroCinquentaReais = new Dinheiro(Moeda.BRL, 50, 00);
	}

	@Test()
	public void comparaValoresMonetariosComMoedasDiferentes() {
		ValorMonetario valorMonetarioComparacao = new ValorMonetario(Moeda.USD);
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		valorMonetarioComparacao = valorMonetarioComparacao.somar(dinheiroCemReais);
		boolean ehIgual = valorMonetarioBRL.equals(valorMonetarioComparacao);
		assertEquals(false, ehIgual);
	}

	@Test()
	public void comparaValoresMonetariosComQuantiasDiferentes() {
		ValorMonetario valorMonetarioComparacao = new ValorMonetario(Moeda.BRL);
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		valorMonetarioComparacao = valorMonetarioComparacao.somar(dinheiroCinquentaReais);
		boolean ehIgual = valorMonetarioBRL.equals(valorMonetarioComparacao);
		assertEquals(false, ehIgual);
	}

	@Test()
	public void comparaValoresMonetariosIguais() {
		ValorMonetario valorMonetarioComparacao = new ValorMonetario(Moeda.BRL);
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		valorMonetarioComparacao = valorMonetarioComparacao.somar(dinheiroCemReais);
		boolean ehIgual = valorMonetarioBRL.equals(valorMonetarioComparacao);
		assertEquals(true, ehIgual);
	}

	@Test()
	public void criarValorMonetarioZerado() {
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		assertEquals("0,00", valorMonetario.toString());
	}

	@Test()
	public void metodoNegativoComValorMonetarioNegativo() {
		valorMonetarioBRL = valorMonetarioBRL.subtrair(dinheiroCemReais);
		boolean ehNegativo = valorMonetarioBRL.negativo();
		assertEquals(true, ehNegativo);
	}

	@Test()
	public void metodoNegativoComValorMonetarioPositivo() {
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		boolean ehNegativo = valorMonetarioBRL.negativo();
		assertEquals(false, ehNegativo);
	}

	@Test()
	public void metodoZeroComValorMonetarioCem() {
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		boolean ehZero = valorMonetarioBRL.zero();
		assertEquals(false, ehZero);
	}

	@Test()
	public void metodoZeroComValorMonetarioZero() {
		boolean ehZero = valorMonetarioBRL.zero();
		assertEquals(true, ehZero);
	}

	@Test()
	public void obterQuantia() {
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		Dinheiro dinheiroObtido = valorMonetarioBRL.obterQuantia();
		assertEquals("Dinheiro", dinheiroCemReais, dinheiroObtido);
	}

	@Test()
	public void somaValorMonetario() {
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCinquentaReais);
		assertEquals("+150,00 BRL", valorMonetarioBRL.toString());
	}

	@Test()
	public void somarBRLComUSD() {
		Dinheiro dinheiroCemDolares = new Dinheiro(Moeda.USD, 100, 00);
		valorMonetarioBRL = valorMonetarioBRL.subtrair(dinheiroCemReais);
		valorMonetarioBRL = valorMonetarioBRL.subtrair(dinheiroCemDolares);
		assertEquals("-150,00 BRL", valorMonetarioBRL.toString());
	}

	@Test()
	public void subtraiValorMonetario() {
		valorMonetarioBRL = valorMonetarioBRL.subtrair(dinheiroCemReais);
		valorMonetarioBRL = valorMonetarioBRL.subtrair(dinheiroCinquentaReais);
		assertEquals("-150,00 BRL", valorMonetarioBRL.toString());
	}
}
