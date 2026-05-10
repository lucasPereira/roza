package teste.br.ufsc.ine.leb.sistemaBancario;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Conta;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteAgencia {

	private SistemaBancario sistemaBancario;
	
	@Before
	public void setUp() {
		sistemaBancario = new SistemaBancario();
	}
	
	@Test
	public void criarBanco() {
		//Fixture Setup
		//Exercise SUT
		sistemaBancario.criarBanco("Santander", Moeda.BRL);
		//Result Verification
		assertEquals(1, sistemaBancario.obterBancos().size());
		//Fixture Teardown
		
	}
	
	@Test
	public void criarAgencia() {
		//Fixture Setup
		sistemaBancario.criarBanco("Santander", Moeda.BRL);
		//Exercise SUT
		Agencia santanderTrindade = sistemaBancario.obterBancos().get(0).criarAgencia("SantanderTrindade");
		//Result Verification
		assertEquals("Santander", santanderTrindade.obterBanco().obterNome());
		assertEquals("SantanderTrindade", santanderTrindade.obterNome());
		//Fixture Teardown
	}
	
	@Test
	public void criarConta() {
		//Fixture Setup
		sistemaBancario.criarBanco("Santander", Moeda.BRL);
		Agencia santanderTrindade = sistemaBancario.obterBancos().get(0).criarAgencia("SantanderTrindade");
		//Exercise SUT
		Conta contaSalario = santanderTrindade.criarConta("Vinicius Alves");
		//Result Verification
		assertEquals("SantanderTrindade", contaSalario.obterAgencia().obterNome());
		//Fixture Teardown
	}
	
}
