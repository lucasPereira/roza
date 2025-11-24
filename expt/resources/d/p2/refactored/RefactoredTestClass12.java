import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass12 {

	@Before()
	public void setup() {
		this.sistemaBancario = new SistemaBancario();
		this.bb = this.sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
		this.bbTrindade = this.bb.criarAgencia(TRINDADE);
		this.leandroConta = this.bbTrindade.criarConta(LEANDRO);
		this.dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		this.cincoReais = new Dinheiro(Moeda.BRL, 5, 0);
		this.doisReais = new Dinheiro(Moeda.BRL, 2, 0);
		this.zero = new ValorMonetario(Moeda.BRL);
		this.deposito = new Entrada(leandroConta, dezReais);
		this.saque = new Saida(leandroConta, cincoReais);
		this.leandroContaDepositadoDezReais = this.bbTrindade.criarConta(LEANDRO);
		this.depositoDezReais = this.sistemaBancario.depositar(this.leandroContaDepositadoDezReais, this.dezReais);
		this.leandroContaSaldoDezReais = this.bbTrindade.criarConta(LEANDRO);
		this.sistemaBancario.depositar(this.leandroContaSaldoDezReais, this.dezReais);
		this.saqueCincoReaisSaldoSuperior = this.sistemaBancario.sacar(this.leandroContaSaldoDezReais, this.cincoReais);
		this.leandroContaSaldoDoisReais = this.bbTrindade.criarConta(LEANDRO);
		this.sistemaBancario.depositar(this.leandroContaSaldoDoisReais, this.doisReais);
		this.saqueCincoReaisSaldoInferior = this.sistemaBancario.sacar(this.leandroContaSaldoDoisReais, this.cincoReais);
	}

	@Test()
	public void agenciaTrindadeErro() {
		assertNotEquals("002", this.bbTrindade.obterIdentificador());
		assertNotEquals("Campeche", this.bbTrindade.obterNome());
		assertNotEquals("Banco Brasil", this.bbTrindade.obterBanco().obterNome());
	}

	@Test()
	public void bancoDoBrasilErro() {
		assertNotEquals("Banco Brasil", this.bb.obterNome());
		assertNotEquals(Moeda.USD, this.bb.obterMoeda());
	}

	@Test()
	public void contaLeandroErro() {
		assertNotEquals("0001-5", this.leandroConta.obterIdentificador());
		assertNotEquals("Mario", this.leandroConta.obterTitular());
		assertNotEquals(zero.somar(new Dinheiro(Moeda.BRL, 5, 0)), this.leandroConta.calcularSaldo());
		assertNotEquals(bb.criarAgencia("Campeche"), this.leandroConta.obterAgencia());
	}

	@Test()
	public void criacaoAgencia() {
		assertEquals("001", this.bbTrindade.obterIdentificador());
		assertEquals(TRINDADE, this.bbTrindade.obterNome());
		assertEquals(BANCO_DO_BRASIL, this.bbTrindade.obterBanco().obterNome());
	}

	@Test()
	public void criacaoDeBanco() {
		assertEquals(BANCO_DO_BRASIL, this.bb.obterNome());
		assertEquals(Moeda.BRL, this.bb.obterMoeda());
	}

	@Test()
	public void criacaoDeConta() {
		assertEquals("0001-7", this.leandroConta.obterIdentificador());
		assertEquals(LEANDRO, this.leandroConta.obterTitular());
		assertEquals("0,00", this.leandroConta.calcularSaldo().formatado());
		assertEquals(TRINDADE, this.leandroConta.obterAgencia().obterNome());
	}

	@Test()
	public void operacaoDeposito() {
		assertEquals(EstadosDeOperacao.SUCESSO, this.depositoDezReais.obterEstado());
		assertEquals(this.dezReais.toString(), this.leandroContaDepositadoDezReais.calcularSaldo().formatarSemSinal());
	}

	@Test()
	public void operacaoSaque() {
		assertEquals(EstadosDeOperacao.SUCESSO, this.saqueCincoReaisSaldoSuperior.obterEstado());
		assertEquals(this.cincoReais.toString(), this.leandroContaSaldoDezReais.calcularSaldo().formatarSemSinal());
	}

	@Test()
	public void operacaoSaqueInsuficiente() {
		assertEquals(EstadosDeOperacao.SALDO_INSUFICIENTE, this.saqueCincoReaisSaldoInferior.obterEstado());
		assertEquals(this.doisReais.toString(), this.leandroContaSaldoDoisReais.calcularSaldo().formatarSemSinal());
	}

	@Test()
	public void saldoAposDepositoSucessoErro() {
		this.leandroConta.adicionarTransacao(deposito);
		assertNotEquals(this.zero.obterQuantia().formatado(), this.leandroConta.calcularSaldo().obterQuantia().formatado());
	}

	@Test()
	public void saqueSucessoErro() {
		leandroConta.adicionarTransacao(deposito);
		leandroContaSaldoDezReais.adicionarTransacao(saque);
		assertNotEquals(this.zero.obterQuantia().formatado(), this.leandroContaDepositadoDezReais.calcularSaldo().obterQuantia().formatado());
	}
}
