package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Conta;

public class AgenciaTest {
	
	private Agencia agenciaTrindade;
	
	@Before
	public void setup() {
		this.agenciaTrindade = TestHelper.criarAgenciaTrindade();
	}
	

	@Test
	public void obterIdentificador(){
		//Fixture Setup
		//Exercise SUT
		String identificador = agenciaTrindade.obterIdentificador();
		//Result Verification
		assertEquals("001", identificador);
		//Fixture Teardown
	}

	@Test
	public void obterNome(){
		//Fixture Setup
		//Exercise SUT
		String nome = agenciaTrindade.obterNome();
		//Result Verification
		assertEquals("Trindade", nome);
		//Fixture Teardown
	}
	
	@Test
	public void obterBanco(){
		//Fixture Setup
		//Exercise SUT
		Banco banco = agenciaTrindade.obterBanco();
		//Result Verification
		assertEquals("Banco do Brasil", banco.obterNome());
		//Fixture Teardown
	}
	
	@Test
	public void criarConta(){
		//Fixture Setup
		//Exercise SUT
		Conta conta = agenciaTrindade.criarConta("Maria"); 
		//Result Verification
		assertEquals("Maria", conta.obterTitular());
		assertEquals("0001-5", conta.obterIdentificador());
		//Fixture Teardown
	}
}
