package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Moeda;;

public class BancoTest {
	
	private Banco bancoDoBrasil;
	
	@Before
	public void setup() {
		this.bancoDoBrasil = TestHelper.criarBancoDoBrasil();
	}
	
	@Test
	public void obterNome(){
		//Fixture Setup
		//Exercise SUT
		String nomeDoBanco = bancoDoBrasil.obterNome(); 
		//Result Verification
		assertEquals("Banco do Brasil", nomeDoBanco);
		//Fixture Teardown
	}

	@Test
	public void obterMoeda(){
		//Fixture Setup
		//Exercise SUT
		Moeda moeda = bancoDoBrasil.obterMoeda();
		//Result Verification
		assertEquals("Moeda", moeda, Moeda.BRL);
		//Fixture Teardown
	}

	@Test
	public void criarAgencia(){
		//Fixture Setup
		//Exercise SUT
		Agencia agencia = bancoDoBrasil.criarAgencia("Trindade");
		//Result Verification
		assertEquals("Trindade", agencia.obterNome());
		assertEquals(1, Integer.parseInt(agencia.obterIdentificador()));
		//Fixture Teardown
	}
	
}
