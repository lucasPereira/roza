package testes;

import static org.junit.Assert.*;


import org.junit.Test;
import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Conta;
import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.EstadosDeOperacao;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.Operacao;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;
import br.ufsc.ine.leb.sistemaBancario.Entrada;
import br.ufsc.ine.leb.sistemaBancario.Saida;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteDelegated {
	private static final String BANCO_DO_BRASIL = "Banco do Brasil";
	private static final String TRINDADE = "Trindade";
	private static final String LEANDRO = "Leandro";

	private SistemaBancario getSistemaBancario() {
		SistemaBancario sistemaBancario = new SistemaBancario();
		return sistemaBancario;
	}

	private Banco novoBanco() {
		SistemaBancario sistemaBancario = this.getSistemaBancario();
		Banco bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
		return bb;
	}

	private Agencia novaAgencia() {
		Agencia bbTrindade = this.novoBanco().criarAgencia(TRINDADE);
		return bbTrindade;
	}

	private Conta novaConta() {
		Conta leandroConta = this.novaAgencia().criarConta(LEANDRO);
		return leandroConta;
	}

	private Conta novaContaComDezReaisDeSaldo() {
		Conta leandroConta = this.novaConta();
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		this.getSistemaBancario().depositar(leandroConta, dezReais);
		return leandroConta;
	}

	private Conta novaContaComQuatroReaisDeSaldo() {
		Conta leandroConta = this.novaConta();
		Dinheiro quatroReais = new Dinheiro(Moeda.BRL, 4, 0);
		this.getSistemaBancario().depositar(leandroConta, quatroReais);
		return leandroConta;
	}
	
	
	
	@Test
	public void criacaoDeBanco() throws Exception {
		Banco bb = this.novoBanco();

		assertEquals(BANCO_DO_BRASIL, bb.obterNome());
		assertEquals(Moeda.BRL, bb.obterMoeda());
	}
	
	
	@Test
	public void criacaoAgencia() throws Exception {
		Agencia bbTrindade = this.novaAgencia();

		assertEquals("001", bbTrindade.obterIdentificador());
		assertEquals(TRINDADE, bbTrindade.obterNome());
		assertEquals(BANCO_DO_BRASIL, bbTrindade.obterBanco().obterNome());
	}
	
	@Test
	public void criacaoDeConta() throws Exception {
		Conta leandroConta = this.novaConta();

		assertEquals("0001-7", leandroConta.obterIdentificador());
		assertEquals(LEANDRO, leandroConta.obterTitular());
		assertEquals("0,00", leandroConta.calcularSaldo().formatado());
		assertEquals(TRINDADE, leandroConta.obterAgencia().obterNome());
	}
	
	@Test
	public void operacaoDeposito() throws Exception {
		Conta leandroConta = this.novaConta();
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		Operacao deposito = this.getSistemaBancario().depositar(leandroConta, dezReais);

		assertEquals(EstadosDeOperacao.SUCESSO, deposito.obterEstado());
		assertEquals(dezReais.toString(), leandroConta.calcularSaldo().formatarSemSinal());
	}
	
	@Test
	public void operacaoSaque() throws Exception {
		Conta leandroConta = this.novaContaComDezReaisDeSaldo();
		Operacao operacao = this.getSistemaBancario().sacar(leandroConta, new Dinheiro(Moeda.BRL, 5, 0));

		assertEquals(EstadosDeOperacao.SUCESSO, operacao.obterEstado());
		assertEquals(new Dinheiro(Moeda.BRL, 5, 0).toString(), leandroConta.calcularSaldo().formatarSemSinal());
	}
	
	@Test
	public void operacaoSaqueInsuficiente() throws Exception {
		Conta leandroConta = this.novaContaComQuatroReaisDeSaldo();
		Dinheiro cincoReais = new Dinheiro(Moeda.BRL, 5, 0);
		Operacao operacao = this.getSistemaBancario().sacar(leandroConta, cincoReais);

		assertEquals(EstadosDeOperacao.SALDO_INSUFICIENTE, operacao.obterEstado());
		assertEquals(new Dinheiro(Moeda.BRL, 4, 0).toString(), leandroConta.calcularSaldo().formatarSemSinal());
	}
	
	@Test
	public void bancoDoBrasilErro() throws Exception {
		Banco bb = this.novoBanco();

		assertNotEquals("Banco Brasil", bb.obterNome());
		assertNotEquals(Moeda.USD, bb.obterMoeda());
	}
	
	@Test
	public void agenciaTrindadeErro() throws Exception {
		Agencia bbTrindade = this.novaAgencia();

		assertNotEquals("002", bbTrindade.obterIdentificador());
		assertNotEquals("Campeche", bbTrindade.obterNome());
		assertNotEquals("Banco Brasil", bbTrindade.obterBanco().obterNome());
	}
	
	@Test
	public void contaLeandroErro() throws Exception {
		Banco bb = this.novoBanco();
		Conta leandroConta = this.novaConta();

		assertNotEquals("0001-5", leandroConta.obterIdentificador());
		assertNotEquals("Mario", leandroConta.obterTitular());
		assertNotEquals(bb.criarAgencia("Campeche"), leandroConta.obterAgencia());
	}
	
	@Test
	public void saldoAposDepositoSucessoErro() throws Exception {
		Conta leandroConta = this.novaConta();
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		Operacao deposito = this.getSistemaBancario().depositar(leandroConta, dezReais);
		ValorMonetario zero = new ValorMonetario(Moeda.BRL);
		assertNotEquals(zero.obterQuantia().formatado(), leandroConta.calcularSaldo().obterQuantia().formatado());
	}
	
	@Test
	public void saqueSucessoErro() throws Exception {
		Conta leandroConta = this.novaContaComDezReaisDeSaldo();
		Operacao operacao = this.getSistemaBancario().sacar(leandroConta, new Dinheiro(Moeda.BRL, 5, 0));
		ValorMonetario zero = new ValorMonetario(Moeda.BRL);
		assertNotEquals(zero.obterQuantia().formatado(), leandroConta.calcularSaldo().obterQuantia().formatado());
	}

}
