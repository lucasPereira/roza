import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass10 {

	private ValorMonetario vmBRL;

	private ValorMonetario vmCHF;

	@Before()
	public void setup() {
		vmBRL = new ValorMonetario(Moeda.BRL);
		vmCHF = new ValorMonetario(Moeda.CHF);
	}

	@Test()
	public void checarNegativoFalse() {
		Dinheiro dindin = new Dinheiro(Moeda.BRL, 100, 0);
		vmBRL = vmBRL.somar(dindin);
		Boolean neg = vmBRL.negativo();
		assertEquals(false, neg);
	}

	@Test()
	public void checarNegativoTrue() {
		Dinheiro dindin = new Dinheiro(Moeda.BRL, 100, 0);
		vmBRL = vmBRL.subtrair(dindin);
		Boolean neg = vmBRL.negativo();
		assertEquals(true, neg);
	}

	@Test()
	public void criarValorMonetário() {
		assertEquals(true, vmBRL.zero());
	}

	@Test()
	public void equalsDinheiroDiferentesObjetos() {
		Boolean igual = vmCHF.equals(Moeda.BRL);
		assertEquals(false, igual);
	}

	@Test()
	public void equalsDinheiroFalse() {
		Boolean igual = vmCHF.equals(vmBRL);
		assertEquals(true, igual);
	}

	@Test()
	public void equalsValorMonetarioTrue() {
		ValorMonetario outro = new ValorMonetario(Moeda.CHF);
		Boolean igual = vmCHF.equals(outro);
		assertEquals(true, igual);
	}

	@Test()
	public void obterQuantiaValorMonetário() {
		Dinheiro dindin = new Dinheiro(Moeda.BRL, 100, 0);
		vmBRL = vmBRL.somar(dindin);
		Dinheiro quantia = vmBRL.obterQuantia();
		assertEquals(dindin, quantia);
	}

	@Test()
	public void somarValorMonetário() {
		Dinheiro dindin = new Dinheiro(Moeda.BRL, 100, 50);
		vmBRL = vmBRL.somar(dindin);
		assertEquals("100,50 BRL", vmBRL.formatarSemSinal());
	}

	@Test()
	public void somarValorMonetárioDiferenteMoedas() {
		Dinheiro dindin = new Dinheiro(Moeda.CHF, 100, 0);
		vmBRL = vmBRL.somar(dindin);
	}

	@Test()
	public void subtrairValorMonetário() {
		Dinheiro dindin = new Dinheiro(Moeda.BRL, 100, 0);
		vmBRL = vmBRL.subtrair(dindin);
		assertEquals("-100,00 BRL", vmBRL.formatarComSinal());
	}

	@Test()
	public void subtrairValorMonetárioDiferenteMoedas() {
		Dinheiro dindin = new Dinheiro(Moeda.CHF, 100, 0);
		vmBRL = vmBRL.subtrair(dindin);
	}
}
