package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Conta;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class Teste_Agencia {

	private SistemaBancario sistemaBancario;
	private Banco bancoReal;
	
	@Before
	public void setUp() {
		sistemaBancario = new SistemaBancario();
		bancoReal = sistemaBancario.criarBanco("Banco Real", Moeda.BRL);
	}
	
	@Test
	public void criaAgencia() {
		//Fixture Setup
		//Exercise SUT
		Agencia trindade = bancoReal.criarAgencia("Trindade");
		//Results Verification
		assertEquals("Trindade", trindade.obterNome());
		assertEquals(bancoReal, trindade.obterBanco());
		assertEquals("001", trindade.obterIdentificador());
		//Fixture TearDown
	}
	
	@Test
	public void criarContaAdicionaNaAgencia() {
		//Fixture Setup
		Agencia trindade = bancoReal.criarAgencia("Trindade");
		Conta codigo1 = trindade.criarConta("Jose");
		Conta codigo2 = trindade.criarConta("Joseph");
		//Exercise SUT
		//Results Verification
		assertEquals("0002-6", codigo2.obterIdentificador());
		//Fixture TearDown
	}

}
