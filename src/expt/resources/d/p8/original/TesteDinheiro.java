package sistemaBancario;

import static org.junit.Assert.*;
import org.junit.Test;

import sistemaBancario.Dinheiro;
import sistemaBancario.Moeda;



public class TesteDinheiro {
	
	
	//In-line Setup
	@Test
	public void verificarQuantiaEmEscala (){
		//Fixture Setup
		//Exercise SUT
		Dinheiro vinteReaisEVinteCentavos =  new Dinheiro(Moeda.BRL, 20, 20);
		//Result Verification
		assertEquals(new Integer(2020),  vinteReaisEVinteCentavos.obterQuantiaEmEscala());
		//Fixture Teardown
	}
	
	@Test
	public void verificarBaseFracionaria (){
		//Fixture Setup
		//Exercise SUT
		Dinheiro reais =  new Dinheiro(Moeda.BRL, 20, 20);
		//Result Verification
		assertEquals(Moeda.BRL,  reais.obterMoeda());
		//Fixture Teardown
	}
	
	@Test
	public void verificarFormatacaoComMoeda (){
		//Fixture Setup
		//Exercise SUT
		Dinheiro formatacaoSemMoeda =  new Dinheiro(Moeda.BRL, 20, 20);
		//Result Verification
		assertEquals("20,20 BRL", formatacaoSemMoeda.formatado());
		//Fixture Teardown
	}
	
	
}
		
		