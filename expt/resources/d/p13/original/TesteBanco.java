package testes;
import br.ufsc.ine.leb.sistemaBancario.*;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteBanco {

	
	@Test
	public void testeConstrutorBanco () {
		//fixture SetUp - inline
		SistemaBancario sistemaBancario = new SistemaBancario();
		//Exercise SUT
		Banco bancoDoBrasil = sistemaBancario.criarBanco("Banco do Brasil", Moeda.BRL);
		//Results Verification
		assertEquals("Banco do Brasil", bancoDoBrasil.obterNome());
		assertEquals(Moeda.BRL, bancoDoBrasil.obterMoeda());
		//Fixture TearDown
	}
	
	@Test //era pra dar erro
	public void testeConstrutorBancoInvalidoNomeRepetido () {
		//fixture SetUp - inline
		SistemaBancario sistemaBancario = new SistemaBancario();
		//Exercise SUT
		Banco bancoDoBrasil = sistemaBancario.criarBanco("Banco do Brasil", Moeda.BRL);
		//Results Verification
		assertEquals("Banco do Brasil", bancoDoBrasil.obterNome());
		assertEquals(Moeda.BRL, bancoDoBrasil.obterMoeda());
		//Fixture TearDown
	}
	
	@Test //era pra obter erro
	public void testeConstrutorBancoInvalidoNomeVazio () {
		//fixture SetUp - inline
		SistemaBancario sistemaBancario = new SistemaBancario();
		//Exercise SUT
		Banco bancoDoBrasil = sistemaBancario.criarBanco("", Moeda.BRL);
		//Results Verification
		assertEquals("", bancoDoBrasil.obterNome());
		assertEquals(Moeda.BRL, bancoDoBrasil.obterMoeda());
		//Fixture TearDown
	}
	
	@Test
	public void testeCriacaoAgencia () {
		//fixture SetUp - Delegated
		Banco bancoDoBrasil = AjudanteDeTestes.retornaBancoDoBrasil(); 
		//Exercise SUT
		Agencia trindade = bancoDoBrasil.criarAgencia("Trindade");
		//Results Verification
		assertEquals("001", trindade.obterIdentificador());
		assertEquals("Trindade", trindade.obterNome());
		assertEquals(bancoDoBrasil, trindade.obterBanco());
		//Fixture TearDown
	}
	
	

}
