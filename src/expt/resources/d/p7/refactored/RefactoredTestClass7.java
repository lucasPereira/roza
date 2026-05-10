import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	@Before()
	public void setup() {
		this.SB = new SistemaBancario();
		this.BB = this.SB.criarBanco("BB", Moeda.BRL);
		this.Agronomica = this.BB.criarAgencia("Agronomica");
		this.Trindade = this.BB.criarAgencia("Trindade");
		this.contaIvan = this.Agronomica.criarConta("Ivan");
		this.reais_00 = new Dinheiro(Moeda.BRL, 00, 00);
		this.reais_50 = new Dinheiro(Moeda.BRL, 50, 00);
		this.reais_100 = new Dinheiro(Moeda.BRL, 100, 00);
	}

	@Test()
	public void adicionaBanco() {
		Banco Bradesco = this.SB.criarBanco("Bradesco", Moeda.BRL);
		assertEquals("Bradesco", Bradesco.obterNome());
		assertEquals(Moeda.BRL, Bradesco.obterMoeda());
	}

	@Test()
	public void comparaBancos_mesmoNome() {
		Banco Bradesco = this.SB.criarBanco("Bradesco", Moeda.BRL);
		Banco BradescoInternational = this.SB.criarBanco("Bradesco", Moeda.USD);
		assertNotEquals(Bradesco, BradescoInternational);
	}

	@Test()
	public void criaSistemaBancario() {
		SistemaBancario SistBancario = new SistemaBancario();
		assertEquals(SistBancario.obterBancos(), new LinkedList<>());
	}

	@Test()
	public void testeDeposito() {
		this.SB.depositar(contaIvan, reais_100);
		assertEquals(this.reais_100.formatado(), this.contaIvan.calcularSaldo().formatarSemSinal());
	}

	@Test()
	public void testeSaldoInicial() {
		assertEquals(this.reais_00.formatado(), this.contaIvan.calcularSaldo().formatarSemSinal());
	}

	@Test()
	public void testeSaque() {
		this.SB.depositar(contaIvan, reais_100);
		this.SB.sacar(contaIvan, reais_50);
		assertEquals(this.reais_50.formatado(), this.contaIvan.calcularSaldo().formatarSemSinal());
	}

	@Test()
	public void testeSaque_saldoInsuficiente() {
		this.SB.depositar(contaIvan, reais_50);
		this.SB.sacar(contaIvan, reais_100);
		assertEquals(this.reais_50.formatado(), this.contaIvan.calcularSaldo().formatarSemSinal());
	}

	@Test()
	public void testeTransferencia_delegated() {
		Conta contaThiago = TesteSistemaBancarioHelper.criaConta(SB, "Santander", "StMonica", "Thiago", 50);
		this.SB.depositar(contaIvan, reais_50);
		this.SB.transferir(contaIvan, contaThiago, reais_50);
		assertEquals(this.reais_100.formatado(), contaThiago.calcularSaldo().formatarSemSinal());
	}

	@Test()
	public void testeTransferencia_saldoNegativo() {
		Conta contaGuilherme = this.Trindade.criarConta("Guilherme");
		this.SB.depositar(contaGuilherme, reais_50);
		this.SB.transferir(contaGuilherme, contaIvan, reais_100);
		assertEquals(this.reais_100.formatado(), this.contaIvan.calcularSaldo().formatarSemSinal());
		assertEquals(this.reais_50.negativo().formatarComSinal(), contaGuilherme.calcularSaldo().formatarComSinal());
	}

	@Test()
	public void teste_transferencia() {
		Conta contaGuilherme = this.Trindade.criarConta("Guilherme");
		this.SB.depositar(contaIvan, reais_100);
		this.SB.transferir(contaIvan, contaGuilherme, reais_50);
		assertEquals(this.reais_50.formatado(), this.contaIvan.calcularSaldo().formatarSemSinal());
		assertEquals(this.reais_50.formatado(), contaGuilherme.calcularSaldo().formatarSemSinal());
	}
}
