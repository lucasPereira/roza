package sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Test;

import sistemaBancario.Dinheiro;
import sistemaBancario.Moeda;
import sistemaBancario.Banco;

public class TestarBanco {

	//In-line Setup
	@Test
	public void criarBancoBradesco() {
		//Fixture Setup
		Dinheiro milReais = new Dinheiro(Moeda.BRL, 1000, 0);
		//Exercise SUT
		Banco bradesco = new Banco("Bradesco", Moeda.BRL, milReais);
		//Result Verification
		assertEquals("Bradesco", bradesco.obterNome());
		//Fixture Teardown
	}
	
	@Test
	public void verificarTipoMoedaBanco() {
		//Fixture Setup
		Dinheiro milReais = new Dinheiro(Moeda.BRL, 1000, 0);
		//Exercise SUT
		Banco bradesco = new Banco("Bradesco", Moeda.BRL, milReais);
		//Result Verification
		assertEquals(Moeda.BRL, bradesco.obterMoeda());
		//Fixture Teardown
	}

}
