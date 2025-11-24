package sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Test;

import sistemaBancario.Dinheiro;
import sistemaBancario.Moeda;

public class TestarDinheiro {

	@Test
	public void testarValorDezReais() {
		//Fixture Setup
		//Exercise SUT
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		//Result Verification
		assertEquals(Moeda.BRL, dezReais.obterMoeda());
		//Fixture Teardown
	}
	
	@Test
	public void testarQuantiaEmEscalaDezReaisCinquentaCentavos() {
		//Fixture Setup
		//Exercise SUT
		Dinheiro dezReaisCinquentaCentavos = new Dinheiro(Moeda.BRL, 10, 50);
		//Result Verification
		assertEquals(new Integer(1050), dezReaisCinquentaCentavos.obterQuantiaEmEscala());
		//Fixture Teardown
	}
	
	@Test
	public void testarQuinzeReaisFormatado() {
		//Fixture Setup
		//Exercise SUT
		Dinheiro quinzeReais = new Dinheiro(Moeda.BRL, 15, 0);
		//Result Verification
		assertEquals("15,00 BRL", quinzeReais.formatado());
		//Fixture Teardown
	}
	
	@Test
	public void testarObterMoedaCincoReais() {
		//Fixture Setup
		//Exercise SUT
		Dinheiro cincoReais = new Dinheiro(Moeda.BRL, 5, 0);
		//Result Verification
		assertEquals(Moeda.BRL, cincoReais.obterMoeda());
		//Fixture Teardown
	}

}
