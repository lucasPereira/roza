import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	private SistemaBancarioTest sisBankTest;

	private Banco bancoNormal;

	@Before()
	public void setup() {
		sisBankTest = new SistemaBancarioTest();
		bancoNormal = new Banco("Banco do Brasil", Moeda.BRL, new Dinheiro(Moeda.BRL, 0, 0));
	}

	@Test()
	public void bankConstructor() {
		Banco banco = new Banco("Banco do Brasil", Moeda.BRL, new Dinheiro(Moeda.BRL, 0, 0));
		assertNotNull(banco);
	}

	@Test()
	public void longAgencyName() {
		String nome = "";
		for (int i = 0; i < 10000; i++) nome = nome.concat("aa");
		Agencia agencia = bancoNormal.criarAgencia(nome);
		assertEquals(agencia.obterBanco(), bancoNormal);
		assertEquals(agencia.obterNome(), nome);
	}

	@Test()
	public void moedaBancoBrasil() {
		moedaTest(Moeda.BRL);
	}

	@Test()
	public void moedaBancoFr() {
		moedaTest(Moeda.CHF);
	}

	@Test()
	public void moedaBancoUSA() {
		moedaTest(Moeda.USD);
	}

	@Test()
	public void weirdAgencyName() {
		String nome = "kl2jkj\0\n\n\t\231";
		Agencia agencia = bancoNormal.criarAgencia(nome);
		assertEquals(agencia.obterBanco(), bancoNormal);
		assertEquals(agencia.obterNome(), nome);
	}
}
