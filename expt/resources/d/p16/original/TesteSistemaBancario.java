package teste.br.ufsc.ine.leb.sistemaBancario;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Conta;
import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.EstadosDeOperacao;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.Operacao;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteSistemaBancario {
	
	private SistemaBancario sistemaBancario;
	private Agencia santanderTrindade;
	
	@Before
	public void setUp() {
		sistemaBancario = new SistemaBancario();
		sistemaBancario.criarBanco("Santander", Moeda.BRL);
		santanderTrindade = sistemaBancario.obterBancos().get(0).criarAgencia("SantaderTrindade");
	}
	
	@Test
	public void transferir() {
		//Fixture Setup
		Conta contaOrigem = santanderTrindade.criarConta("Vinicius Alves");
		Conta contaDestino = santanderTrindade.criarConta("Georgia");
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		sistemaBancario.depositar(contaOrigem, nove);
		//Exercise SUT
		Operacao transferencia = sistemaBancario.transferir(contaOrigem, contaDestino, nove);
		//Result Verification
		assertEquals(EstadosDeOperacao.SUCESSO, transferencia.obterEstado());
		//Fixture Teardown
	}
	
	@Test
	public void transferenciaMoedaInvalida() {
		//Fixture Setup
		Conta contaOrigem = santanderTrindade.criarConta("Vinicius Alves");
		Conta contaDestino = santanderTrindade.criarConta("Georgia");
		Dinheiro nove = new Dinheiro(Moeda.USD, 9, 0);
		//Exercise SUT
		Operacao transferencia = sistemaBancario.transferir(contaOrigem, contaDestino, nove);
		//Result Verification
		assertEquals(EstadosDeOperacao.MOEDA_INVALIDA, transferencia.obterEstado());
		//Fixture Teardown
	}
	
	@Test
	public void transferenciaSaldoInsuficiente() {
		//Fixture Setup
		Conta contaOrigem = santanderTrindade.criarConta("Vinicius Alves");
		Conta contaDestino = santanderTrindade.criarConta("Georgia");
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		//Exercise SUT
		Operacao transferencia = sistemaBancario.transferir(contaOrigem, contaDestino, nove);
		//Result Verification
		assertEquals(EstadosDeOperacao.SALDO_INSUFICIENTE, transferencia.obterEstado());
		//Fixture Teardown
	}
	
	@Test
	public void sacar() {
		//Fixture Setup
		Conta conta = santanderTrindade.criarConta("Vinicius Alves");
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		Dinheiro quatro = new Dinheiro(Moeda.BRL, 4, 0);
		sistemaBancario.depositar(conta, nove);
		//Exercise SUT
		Operacao saque = sistemaBancario.sacar(conta, quatro);
		//Result Verification
		assertEquals(EstadosDeOperacao.SUCESSO, saque.obterEstado());
		//Fixture Teardown
	}
	
	@Test
	public void saqueSaldoInsuficiente() {
		//Fixture Setup
		Conta conta = santanderTrindade.criarConta("Vinicius Alves");
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		Dinheiro noveCinquenta = new Dinheiro(Moeda.BRL, 9, 50);
		sistemaBancario.depositar(conta, nove);
		//Exercise SUT
		Operacao saque = sistemaBancario.sacar(conta, noveCinquenta);
		//Result Verification
		assertEquals(EstadosDeOperacao.SALDO_INSUFICIENTE, saque.obterEstado());
		//Fixture Teardown
	}
	
	@Test
	public void saqueMoedaInvalida() {
		//Fixture Setup
		Conta conta = santanderTrindade.criarConta("Vinicius Alves");
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		Dinheiro quatro = new Dinheiro(Moeda.USD, 4, 0);
		sistemaBancario.depositar(conta, nove);
		//Exercise SUT
		Operacao saque = sistemaBancario.sacar(conta, quatro);
		//Result Verification
		assertEquals(EstadosDeOperacao.MOEDA_INVALIDA, saque.obterEstado());
		//Fixture Teardown
	}
	
	@Test
	public void depositar() {
		//Fixture Setup
		Conta conta = santanderTrindade.criarConta("Vinicius Alves");
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		//Exercise SUT
		Operacao deposito = sistemaBancario.depositar(conta, nove);
		//Result Verification
		assertEquals(EstadosDeOperacao.SUCESSO, deposito.obterEstado());
		//Fixture Teardown
	}
	
	@Test
	public void depositoMoedaInvalida() {
		//Fixture Setup
		Conta conta = santanderTrindade.criarConta("Vinicius Alves");
		Dinheiro nove = new Dinheiro(Moeda.USD, 9, 0);
		//Exercise SUT
		Operacao deposito = sistemaBancario.depositar(conta, nove);
		//Result Verification
		assertEquals(EstadosDeOperacao.MOEDA_INVALIDA, deposito.obterEstado());
		//Fixture Teardown
	}

}
