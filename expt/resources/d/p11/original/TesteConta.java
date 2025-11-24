package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Conta;
import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Entrada;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteConta {
	
	private SistemaBancario sistemaBancario;
	private Banco bancoTeste;
	private Agencia agenciaTeste;
	private Conta contaTeste;
	
	@Before	// Implicit Setup
	public void setup() {
		sistemaBancario = new SistemaBancario();
		sistemaBancario.criarBanco("bantest", Moeda.BRL);
		bancoTeste = sistemaBancario.obterBancos().get(0);
		agenciaTeste = bancoTeste.criarAgencia("Agencia Teste");
		contaTeste = agenciaTeste.criarConta("teste");
	}
	
	@Test	// Implicit Setup
	public void testeObterIdentificador() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("0001-5", contaTeste.obterIdentificador());
		
		// 	Fixture Teardown
	}

	@Test	// Implicit Setup
	public void obterTitular() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("teste", contaTeste.obterTitular());
		
		// 	Fixture Teardown
	}
	
	@Test	// Implicit Setup
	public void obterAgencia() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("Agencia Teste", contaTeste.obterAgencia().obterNome());
		
		// 	Fixture Teardown
	}
	
	@Test	// Implicit Setup
	public void calcularSaldo() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("0,00", contaTeste.calcularSaldo().toString());
		
		// 	Fixture Teardown
	}
	
	@Test	// Implicit Setup
	public void calcularSaldoAposTransacao() {
		//	Fixture Setup
		
		//	Exercise SUT
		Dinheiro reais10Centavos20 = new Dinheiro(Moeda.BRL, 10, 20);
		Entrada entradaDinheiro = new Entrada(contaTeste, reais10Centavos20);
		contaTeste.adicionarTransacao(entradaDinheiro);
		
		//	Result Verification
		assertEquals("+10,20 BRL", contaTeste.calcularSaldo().toString());
		
		// 	Fixture Teardown
	}
}
