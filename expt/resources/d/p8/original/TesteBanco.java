package sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Test;

import sistemaBancario.Dinheiro;
import sistemaBancario.Moeda;
import sistemaBancario.Banco;

public class TesteBanco {

	//In-line Setup
	@Test
	public void criarBancoBradesco() {
		//Fixture Setup
		Dinheiro quinhentosReais = new Dinheiro(Moeda.BRL, 500, 0);
		//Exercise SUT
		Banco bancoBrasil = new Banco("Brasil", Moeda.BRL, quinhentosReais);
		//Result Verification
		assertEquals("Brasil", bancoBrasil.obterNome());
		//Fixture Teardown
	}
	
	@Test
	public void verificarTipoMoedaBanco() {
		//Fixture Setup
		Dinheiro quinhentosReais = new Dinheiro(Moeda.BRL, 500, 0);
		//Exercise SUT
		Banco bancoBrasil = new Banco("Brasil", Moeda.BRL, quinhentosReais);
		//Result Verification
		assertEquals(Moeda.BRL, bancoBrasil.obterMoeda());
		//Fixture Teardown
	}

}
