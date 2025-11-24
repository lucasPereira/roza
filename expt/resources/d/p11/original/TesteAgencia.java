package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteAgencia {
	
	private SistemaBancario sistemaBancario;
	private Banco bancoTeste;
	private Agencia agenciaTeste;
	
	@Before	// Implicit Setup
	public void setup() {
		sistemaBancario = new SistemaBancario();
		sistemaBancario.criarBanco("bantest", Moeda.BRL);
		bancoTeste = sistemaBancario.obterBancos().get(0);
		agenciaTeste = bancoTeste.criarAgencia("Agencia Teste");
	}
	
	@Test	// Implicit Setup
	public void testeCriaAgenciaTeste() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("Agencia Teste", agenciaTeste.obterNome());
		
		// 	Fixture Teardown
	}
	
	@Test	// Implicit Setup
	public void testeObterIdentificador() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("001", agenciaTeste.obterIdentificador());	
		
		// 	Fixture Teardown
	}
	
	@Test	// Implicit Setup
	public void testeObterBanco() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("bantest", agenciaTeste.obterBanco().obterNome());	
		
		// 	Fixture Teardown
	}
}
