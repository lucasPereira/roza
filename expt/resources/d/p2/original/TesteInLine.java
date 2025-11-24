package testes;

import static org.junit.Assert.*;

import org.junit.Ignore;
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


public class TesteInLine {

	private static final String BANCO_DO_BRASIL = "Banco do Brasil";
	private static final String TRINDADE = "Trindade";
	private static final String LEANDRO = "Leandro";

	
	
	@Test
	public void criacaoDeBanco() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Banco bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);

		assertEquals(BANCO_DO_BRASIL, bb.obterNome());
		assertEquals(Moeda.BRL, bb.obterMoeda());
	}
	
	@Test
	public void criacaoAgencia() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Banco bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
		Agencia bbTrindade = bb.criarAgencia(TRINDADE);

		assertEquals("001", bbTrindade.obterIdentificador());
		assertEquals(TRINDADE, bbTrindade.obterNome());
		assertEquals(bb, bbTrindade.obterBanco());
	}
	
	@Test
	public void criacaoDeConta() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Banco bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
		Agencia bbTrindade = bb.criarAgencia(TRINDADE);
		Conta leandroConta = bbTrindade.criarConta(LEANDRO);

		assertEquals("0001-7", leandroConta.obterIdentificador());
		assertEquals(LEANDRO, leandroConta.obterTitular());
		assertEquals("0,00", leandroConta.calcularSaldo().formatado());
		assertEquals(TRINDADE, leandroConta.obterAgencia().obterNome());
	}
	
	@Test
	public void operacaoDeposito() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Banco bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
		Agencia bbTrindade = bb.criarAgencia(TRINDADE);
		Conta leandroConta = bbTrindade.criarConta(LEANDRO);
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		Operacao deposito = sistemaBancario.depositar(leandroConta, dezReais);

		assertEquals(EstadosDeOperacao.SUCESSO, deposito.obterEstado());
		assertEquals(dezReais.toString(), leandroConta.calcularSaldo().formatarSemSinal());
	}
	
	@Test
	public void operacaoSaque() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Banco bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
		Agencia bbCentro = bb.criarAgencia(TRINDADE);
		Conta leandroConta = bbCentro.criarConta(LEANDRO);
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		sistemaBancario.depositar(leandroConta, dezReais);
		Operacao operacao = sistemaBancario.sacar(leandroConta, new Dinheiro(Moeda.BRL, 5, 0));

		assertEquals(EstadosDeOperacao.SUCESSO, operacao.obterEstado());
		assertEquals(new Dinheiro(Moeda.BRL, 5, 0).toString(), leandroConta.calcularSaldo().formatarSemSinal());
	}
	
	@Test
	public void operacaoSaqueInsuficiente() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Banco bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
		Agencia bbCentro = bb.criarAgencia(TRINDADE);
		Conta leandroConta = bbCentro.criarConta(LEANDRO);
		Dinheiro cincoReais = new Dinheiro(Moeda.BRL, 5, 0);
		sistemaBancario.depositar(leandroConta, cincoReais);
		Operacao operacao = sistemaBancario.sacar(leandroConta, new Dinheiro(Moeda.BRL, 10, 0));

		assertEquals(EstadosDeOperacao.SALDO_INSUFICIENTE, operacao.obterEstado());
		assertEquals(cincoReais.toString(), leandroConta.calcularSaldo().formatarSemSinal());
	}
	
	@Test
	public void bancoDoBrasilErro() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();

		Banco bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);

		assertNotEquals("Banco Brasil", bb.obterNome());
		assertNotEquals(Moeda.USD, bb.obterMoeda());
	}
	
	@Test
	public void agenciaTrindadeErro() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Banco bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);

		Agencia bbTrindade = bb.criarAgencia(TRINDADE);

		assertNotEquals("002", bbTrindade.obterIdentificador());
		assertNotEquals("Campeche", bbTrindade.obterNome());
		assertNotEquals("Banco Brasil", bbTrindade.obterBanco().obterNome());
	}
	@Test
	public void contaLeandroErro() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Banco bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
		Agencia bbTrindade = bb.criarAgencia(TRINDADE);

		Conta contaLeandro = bbTrindade.criarConta(LEANDRO);
		ValorMonetario zero = new ValorMonetario(Moeda.BRL);

		assertNotEquals("0001-5", contaLeandro.obterIdentificador());
		assertNotEquals("Mario", contaLeandro.obterTitular());
		assertNotEquals(zero.somar(new Dinheiro(Moeda.BRL, 5, 0)), contaLeandro.calcularSaldo());
		assertNotEquals(bb.criarAgencia("Campeche"), contaLeandro.obterAgencia());
	}
	
	@Test
	public void saldoAposDepositoSucessoErro() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Banco bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
		Agencia bbTrindade = bb.criarAgencia(TRINDADE);
		Conta contaLeandro = bbTrindade.criarConta(LEANDRO);

		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		Entrada deposito = new Entrada(contaLeandro, dezReais);
		contaLeandro.adicionarTransacao(deposito);
		ValorMonetario zero = new ValorMonetario(Moeda.BRL);

		assertNotEquals(zero.obterQuantia().formatado(), contaLeandro.calcularSaldo().obterQuantia().formatado());
	}
	
	@Test
	public void saqueSucessoErro() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Banco bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
		Agencia bbTrindade = bb.criarAgencia(TRINDADE);
		Conta contaLeandro = bbTrindade.criarConta(LEANDRO);
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		Entrada deposito = new Entrada(contaLeandro, dezReais);
		contaLeandro.adicionarTransacao(deposito);
		ValorMonetario zero = new ValorMonetario(Moeda.BRL);

		Dinheiro cincoReais = new Dinheiro(Moeda.BRL, 5, 0);
		Saida saque = new Saida(contaLeandro, cincoReais);
		contaLeandro.adicionarTransacao(saque);
		Dinheiro doisReais = new Dinheiro(Moeda.BRL, 2, 0);
		ValorMonetario dois = zero.somar(doisReais);

		assertNotEquals(zero.obterQuantia().formatado(), contaLeandro.calcularSaldo().obterQuantia().formatado());
	}
	
	@Test
	public void comparacaoValorMonetarioDeMesmaQuantia() throws Exception{

		ValorMonetario zero = new ValorMonetario(Moeda.BRL);
		ValorMonetario cinco = zero.somar(new Dinheiro(Moeda.BRL, 5, 0));
		ValorMonetario menosCinco = zero.subtrair(new Dinheiro(Moeda.BRL, 5, 0));
		assertEquals(cinco.obterQuantia().formatado(), menosCinco.obterQuantia().formatado());
		assertNotEquals(cinco, menosCinco);
	}
	
	@Test
	public void comparacaoValorMonetarioDeMesmaQuantiaErro() throws Exception{

		ValorMonetario zero = new ValorMonetario(Moeda.BRL);
		ValorMonetario cinco = zero.somar(new Dinheiro(Moeda.BRL, 5, 0));
		ValorMonetario menosDois = zero.subtrair(new Dinheiro(Moeda.BRL, 2, 0));
		assertNotEquals(cinco.obterQuantia().formatado(), menosDois.obterQuantia().formatado());
	}
	
}
