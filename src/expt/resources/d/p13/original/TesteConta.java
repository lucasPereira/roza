package testes;
import br.ufsc.ine.leb.sistemaBancario.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TesteConta {
	
	private Conta contaJohn;
	@Before
	public void testa() {
		contaJohn = AjudanteDeTestes.retornaContaJohnBancoDoBrasilAgTrindade(); //Delegated
	}
	@Test
	public void testeObterIdentificador() {
		//Fixture SetUp
		//Exercise SUT
		String id = contaJohn.obterIdentificador();
		//Results Verification
		assertEquals("0001-4", id);
		//Fixture TearDown
	}

	@Test
	public void testeObterTitular() {
		//Fixture SetUp
		//Exercise SUT
		String titular = contaJohn.obterTitular();
		//Results Verification
		assertEquals("John", titular);
		//Fixture TearDown
	}
	
	@Test
	public void testeObterAgencia() {
		//Fixture SetUp
		//Exercise SUT
		String ag = contaJohn.obterAgencia().obterNome();
		//Results Verification
		assertEquals("Trindade", ag);
		//Fixture TearDown
	}
	
	@Test
	public void testeSaldoZero() {
		//Fixture SetUp
		ValorMonetario valor = new ValorMonetario(Moeda.BRL);
		//Exercise SUT
		ValorMonetario saldoJohn = contaJohn.calcularSaldo();
		//Results Verification
		assertEquals(valor, saldoJohn);
		//Fixture TearDown
	}
	
	@Test
	public void efetuaTransacaoEntrada() {
		//Fixture SetUp -- Delegated
		Dinheiro dezReais = AjudanteDeTestes.retornaDezReais();
		Transacao deposita10Reais = new Entrada(contaJohn, dezReais);
		ValorMonetario valor = new ValorMonetario(Moeda.BRL);
		valor.somar(dezReais);
		//Exercise SUT
		contaJohn.adicionarTransacao(deposita10Reais);
		//Results Verification
		assertEquals(valor, contaJohn.calcularSaldo());
	}
	
	@Test
	public void efetuaTransacaoSaida() {
		//Fixture SetUp -- Delegated
		Dinheiro dezReais = AjudanteDeTestes.retornaDezReais();
		Transacao retira10Reais = new Saida(contaJohn, dezReais);
		//Exercise SUT
		contaJohn.adicionarTransacao(retira10Reais);
		//Results Verification
		assertTrue(contaJohn.calcularSaldo().negativo());
		assertTrue(contaJohn.calcularSaldo().somar(dezReais).zero());
	}
}
