import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private SistemaBancario sistemaBancario;

	@Before()
	public void setup() {
		sistemaBancario = new SistemaBancario();
		sistemaBancario.criarBanco("bantest", Moeda.BRL);
	}

	@Test()
	public void calcularSaldo() {
		Banco bancoTeste = sistemaBancario.obterBancos().get(0);
		Agencia agenciaTeste = bancoTeste.criarAgencia("Agencia Teste");
		Conta contaTeste = agenciaTeste.criarConta("teste");
		assertEquals("0,00", contaTeste.calcularSaldo().toString());
	}

	@Test()
	public void calcularSaldoAposTransacao() {
		Banco bancoTeste = sistemaBancario.obterBancos().get(0);
		Agencia agenciaTeste = bancoTeste.criarAgencia("Agencia Teste");
		Conta contaTeste = agenciaTeste.criarConta("teste");
		Dinheiro reais10Centavos20 = new Dinheiro(Moeda.BRL, 10, 20);
		Entrada entradaDinheiro = new Entrada(contaTeste, reais10Centavos20);
		contaTeste.adicionarTransacao(entradaDinheiro);
		assertEquals("+10,20 BRL", contaTeste.calcularSaldo().toString());
	}

	@Test()
	public void obterAgencia() {
		Banco bancoTeste = sistemaBancario.obterBancos().get(0);
		Agencia agenciaTeste = bancoTeste.criarAgencia("Agencia Teste");
		Conta contaTeste = agenciaTeste.criarConta("teste");
		assertEquals("Agencia Teste", contaTeste.obterAgencia().obterNome());
	}

	@Test()
	public void obterTitular() {
		Banco bancoTeste = sistemaBancario.obterBancos().get(0);
		Agencia agenciaTeste = bancoTeste.criarAgencia("Agencia Teste");
		Conta contaTeste = agenciaTeste.criarConta("teste");
		assertEquals("teste", contaTeste.obterTitular());
	}

	@Test()
	public void testeCriaAgenciaTeste() {
		Banco bancoTeste = sistemaBancario.obterBancos().get(0);
		Agencia agenciaTeste = bancoTeste.criarAgencia("Agencia Teste");
		assertEquals("Agencia Teste", agenciaTeste.obterNome());
	}

	@Test()
	public void testeCriaBanco() {
		Banco banco = sistemaBancario.obterBancos().get(0);
		assertEquals(1, sistemaBancario.obterBancos().size());
	}

	@Test()
	public void testeMoedaBancoCorreta() {
		Banco banco = sistemaBancario.obterBancos().get(0);
		assertEquals(Moeda.BRL, banco.obterMoeda());
	}

	@Test()
	public void testeNomeBancoCorreto() {
		Banco banco = sistemaBancario.obterBancos().get(0);
		assertEquals("bantest", banco.obterNome());
	}

	@Test()
	public void testeObterBanco() {
		Banco bancoTeste = sistemaBancario.obterBancos().get(0);
		Agencia agenciaTeste = bancoTeste.criarAgencia("Agencia Teste");
		assertEquals("bantest", agenciaTeste.obterBanco().obterNome());
	}

	@Test()
	public void testeObterIdentificador() {
		Banco bancoTeste = sistemaBancario.obterBancos().get(0);
		Agencia agenciaTeste = bancoTeste.criarAgencia("Agencia Teste");
		assertEquals("001", agenciaTeste.obterIdentificador());
	}

	@Test()
	public void testeObterIdentificador() {
		Banco bancoTeste = sistemaBancario.obterBancos().get(0);
		Agencia agenciaTeste = bancoTeste.criarAgencia("Agencia Teste");
		Conta contaTeste = agenciaTeste.criarConta("teste");
		assertEquals("0001-5", contaTeste.obterIdentificador());
	}
}
