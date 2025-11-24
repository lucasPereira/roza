package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Conta;
import br.ufsc.ine.leb.sistemaBancario.Entrada;
import br.ufsc.ine.leb.sistemaBancario.Transacao;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;;

public class ContaTest {
	
	private Conta conta;
	
	@Before
	public void setup() {
		this.conta = TestHelper.criarNovaConta();
	}
	

	@Test
	public void obterIdentificador(){
		//Fixture Setup
		//Exercise SUT
		String identificador = conta.obterIdentificador();
		//Result Verification
		assertEquals("0001-5", identificador);
		//Fixture Teardown
	}

	@Test
	public void obterTitular(){
		//Fixture Setup
		//Exercise SUT
		String titular = conta.obterTitular();
		//Result Verification
		assertEquals("Maria", titular);
		//Fixture Teardown
	}
	
	@Test
	public void obterAgencia(){
		//Fixture Setup
		//Exercise SUT
		Agencia agencia = conta.obterAgencia();
		//Result Verification
		assertEquals("Trindade", agencia.obterNome());
		//Fixture Teardown
	}
	
	@Test
	public void calcularSaldoZerado(){
		//Fixture Setup
		//Exercise SUT
		ValorMonetario valorMonetario = conta.calcularSaldo(); 
		//Result Verification
		assertEquals("0,00", valorMonetario.formatado());
		//Fixture Teardown
	}
	
	@Test
	public void calcularComTransacao(){
		//Fixture Setup
		Transacao entrada = new Entrada(conta, TestHelper.criarDinheiroCemReais());
		//Exercise SUT
		conta.adicionarTransacao(entrada);
		ValorMonetario valorMonetario = conta.calcularSaldo(); 
		//Result Verification
		assertEquals("+100,00 BRL", valorMonetario.formatado());
		//Fixture Teardown
	}
}
