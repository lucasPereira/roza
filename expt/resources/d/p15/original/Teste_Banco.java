package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class Teste_Banco {
	
	private SistemaBancario sistemaBancario;
	
	@Before
	public void setUp() {
		sistemaBancario = new SistemaBancario();
	}
	
	@Test
	public void criarBancoBRL() {
		//Fixture SetUp - implicit SetUp do sistema e delegate Banco Real
		Banco bancoReal = Delegates.criaBancoReal(sistemaBancario);
		//Exercise SUT
		//Results Verification
		assertEquals("Banco Real", bancoReal.obterNome());
		assertEquals(Moeda.BRL, bancoReal.obterMoeda());
		//Fixture TearDown
	}
	
	@Test
	public void criarBancoUSD() {
		//Fixture SetUp - implicit SetUp do sistema e inline do banco
		Banco bancoDolar = sistemaBancario.criarBanco("BancoDolar", Moeda.USD);
		//Exercise SUT
		//Results Verification
		assertEquals("BancoDolar", bancoDolar.obterNome());
		assertEquals(Moeda.USD, bancoDolar.obterMoeda());
		//Fixture TearDown
	}
	
	@Test
	public void criarBancoCHF() {
		//Fixture SetUp - implicit SetUp do sistema e inline do banco
		Banco bancoCHF = sistemaBancario.criarBanco("BancoCHF", Moeda.CHF);
		//Exercise SUT
		//Results Verification
		assertEquals("BancoCHF", bancoCHF.obterNome());
		assertEquals(Moeda.CHF, bancoCHF.obterMoeda());
		//Fixture TearDown
	}
	
	@Test
	public void criarAgenciaAdiocionaNoBanco() {
		//Fixture SetUp - implicit SetUp do sistema, Delegate do Banco Real
		Banco bancoReal = Delegates.criaBancoReal(sistemaBancario);
		bancoReal.criarAgencia("Trindade");
		Agencia agenciaTrindade2 = bancoReal.criarAgencia("Trindade2");
		//Exercise SUT
		//Results Verification
		assertEquals("Compara codigo", "002", agenciaTrindade2.obterIdentificador());
		//Fixture TearDown
	}
	
	

}
