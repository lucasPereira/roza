import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private SistemaBancario sistemaBancario;

	private Agencia santanderTrindade;

	@Before()
	public void setup() {
		sistemaBancario = new SistemaBancario();
		sistemaBancario.criarBanco("Santander", Moeda.BRL);
		santanderTrindade = sistemaBancario.obterBancos().get(0).criarAgencia("SantaderTrindade");
	}

	@Test()
	public void depositar() {
		Conta conta = santanderTrindade.criarConta("Vinicius Alves");
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		Operacao deposito = sistemaBancario.depositar(conta, nove);
		assertEquals(EstadosDeOperacao.SUCESSO, deposito.obterEstado());
	}

	@Test()
	public void depositoMoedaInvalida() {
		Conta conta = santanderTrindade.criarConta("Vinicius Alves");
		Dinheiro nove = new Dinheiro(Moeda.USD, 9, 0);
		Operacao deposito = sistemaBancario.depositar(conta, nove);
		assertEquals(EstadosDeOperacao.MOEDA_INVALIDA, deposito.obterEstado());
	}

	@Test()
	public void sacar() {
		Conta conta = santanderTrindade.criarConta("Vinicius Alves");
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		Dinheiro quatro = new Dinheiro(Moeda.BRL, 4, 0);
		sistemaBancario.depositar(conta, nove);
		Operacao saque = sistemaBancario.sacar(conta, quatro);
		assertEquals(EstadosDeOperacao.SUCESSO, saque.obterEstado());
	}

	@Test()
	public void saqueMoedaInvalida() {
		Conta conta = santanderTrindade.criarConta("Vinicius Alves");
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		Dinheiro quatro = new Dinheiro(Moeda.USD, 4, 0);
		sistemaBancario.depositar(conta, nove);
		Operacao saque = sistemaBancario.sacar(conta, quatro);
		assertEquals(EstadosDeOperacao.MOEDA_INVALIDA, saque.obterEstado());
	}

	@Test()
	public void saqueSaldoInsuficiente() {
		Conta conta = santanderTrindade.criarConta("Vinicius Alves");
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		Dinheiro noveCinquenta = new Dinheiro(Moeda.BRL, 9, 50);
		sistemaBancario.depositar(conta, nove);
		Operacao saque = sistemaBancario.sacar(conta, noveCinquenta);
		assertEquals(EstadosDeOperacao.SALDO_INSUFICIENTE, saque.obterEstado());
	}

	@Test()
	public void transferenciaMoedaInvalida() {
		Conta contaOrigem = santanderTrindade.criarConta("Vinicius Alves");
		Conta contaDestino = santanderTrindade.criarConta("Georgia");
		Dinheiro nove = new Dinheiro(Moeda.USD, 9, 0);
		Operacao transferencia = sistemaBancario.transferir(contaOrigem, contaDestino, nove);
		assertEquals(EstadosDeOperacao.MOEDA_INVALIDA, transferencia.obterEstado());
	}

	@Test()
	public void transferenciaSaldoInsuficiente() {
		Conta contaOrigem = santanderTrindade.criarConta("Vinicius Alves");
		Conta contaDestino = santanderTrindade.criarConta("Georgia");
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		Operacao transferencia = sistemaBancario.transferir(contaOrigem, contaDestino, nove);
		assertEquals(EstadosDeOperacao.SALDO_INSUFICIENTE, transferencia.obterEstado());
	}

	@Test()
	public void transferir() {
		Conta contaOrigem = santanderTrindade.criarConta("Vinicius Alves");
		Conta contaDestino = santanderTrindade.criarConta("Georgia");
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		sistemaBancario.depositar(contaOrigem, nove);
		Operacao transferencia = sistemaBancario.transferir(contaOrigem, contaDestino, nove);
		assertEquals(EstadosDeOperacao.SUCESSO, transferencia.obterEstado());
	}
}
