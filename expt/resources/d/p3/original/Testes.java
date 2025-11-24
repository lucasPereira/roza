package Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Conta;
import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;

public class Testes {
	
	private Dinheiro vinteBRL;
	SistemaBancario sistemaBancario0;
	Banco caixa;
	Agencia agenciaCaixaTrindade;
	Conta contaCaixa;
	ValorMonetario meuValorMonetario;
	
	@Before
	public void setUp() {
		vinteBRL = new Dinheiro(Moeda.BRL, 20, 0);
		
		sistemaBancario0 = new SistemaBancario();
		caixa = sistemaBancario0.criarBanco("Caixa", Moeda.BRL);
		agenciaCaixaTrindade = caixa.criarAgencia("Agencia da Trindade");
		contaCaixa = agenciaCaixaTrindade.criarConta("Usuario");
		meuValorMonetario = new ValorMonetario(Moeda.BRL);
	} 

	@Test
	public void criaDinheiro() {
		//Fixture Setup
		//in-line
		Integer valorEmEscala = 9080;
		//Exercise SUT
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 90, 80);
		//Result Verification
		assertEquals(Moeda.BRL, dinheiro.obterMoeda());
		assertEquals(valorEmEscala, dinheiro.obterQuantiaEmEscala());
		assertEquals("90,80 BRL", dinheiro.toString());
		//Fixture Teardown
	}
	
	@Test
	public void toStringMaiorQueZero() {
		//Fixture Setup
		//Exercise SUT
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 90, 80);
		//Result Verification
		assertEquals("90,80 BRL", dinheiro.toString());
		//Fixture Teardown
	}
	
	@Test
	public void toStringIgualAZero() {
		//Fixture Setup
		//Exercise SUT
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 0, 0);
		//Result Verification
		assertEquals("0,00", dinheiro.toString());
		//Fixture Teardown
	}
	
	@Test
	public void criaValorMonetario() {
		//Fixture Setup
		//Exercise SUT
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		//Result Verification
		assertTrue(valorMonetario.zero());
		assertEquals("0,00", valorMonetario.toString());
		//Fixture Teardown
	}
	
	@Test
	public void somaValorMonetario() {
		//Fixture Setup
		//in-line and implicit
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		//Exercise SUT
		valorMonetario = valorMonetario.somar(vinteBRL);
		//Result Verification
		assertEquals("+20,00 BRL", valorMonetario.toString());
		//Fixture Teardown
	}
	
	@Test
	public void subtraiValorMonetario() {
		//Fixture Setup
		//in-line and implicit
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		//Exercise SUT
		valorMonetario = valorMonetario.subtrair(vinteBRL);
		//Result Verification
		assertEquals("-20,00 BRL", valorMonetario.toString());
		//Fixture Teardown
	}
	
	@Test
	public void criaBanco() {
		//Fixture Setup
		//Delegated
		SistemaBancario sistemaBancario = TestHelper.criaSistemaBancario();
		//Exercise SUT
		Banco bradesco = sistemaBancario.criarBanco("Bradesco", Moeda.BRL);
		//Result Verification
		assertEquals("Bradesco", bradesco.obterNome());
		assertEquals(Moeda.BRL, bradesco.obterMoeda());
		//Fixture Teardown
	}
	
	@Test
	public void criaAgencia() {
		//Fixture Setup
		//Delegated
		SistemaBancario sistemaBancario = TestHelper.criaSistemaBancarioComBancos();
		//Exercise SUT
		Agencia agenciaBBtrindade = sistemaBancario.obterBancos().get(1).criarAgencia("Agencia da Trindade");
		//Result Verification
		assertEquals("Agencia da Trindade", agenciaBBtrindade.obterNome());
		assertEquals("001", agenciaBBtrindade.obterIdentificador());
		assertEquals(sistemaBancario.obterBancos().get(1), agenciaBBtrindade.obterBanco());
		//Fixture Teardown
	}
	
	@Test
	public void criaConta() {
		//Fixture Setup
		//Delegated
		SistemaBancario sistemaBancario = TestHelper.criaSistemaBancarioComBancos();
		Agencia agenciaBBtrindade = sistemaBancario.obterBancos().get(1).criarAgencia("Agencia da Trindade");
		//Exercise SUT
		Conta minhaConta = agenciaBBtrindade.criarConta("Rafael");
		//Result Verification
		assertEquals("Rafael", minhaConta.obterTitular());
		assertEquals("0001-6", minhaConta.obterIdentificador());
		assertEquals(agenciaBBtrindade, minhaConta.obterAgencia());
		//Fixture Teardown
	}
	
	@Test
	public void testaCalculaSaldo(){
		//Fixture Setup
		//implicit
		//Exercise SUT
		meuValorMonetario = contaCaixa.calcularSaldo();
		//Result Verification
		assertEquals("0,00", meuValorMonetario.toString());
		//Fixture Teardown		
	}	

}
