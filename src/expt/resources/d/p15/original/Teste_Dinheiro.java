package testes;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;

public class Teste_Dinheiro {

	@Test
	public void criaDinheiroBRL() {
		//Fixture SetUp	
		//Exercise SUT
		Dinheiro dezReais = Delegates.criaDezReais();
		//Results Verification
		assertEquals(1000, dezReais.obterQuantiaEmEscala().intValue());
		assertEquals(Moeda.BRL, dezReais.obterMoeda());
		//Fixture TearDown
	}
	
	@Test
	public void criaDinheiroUSD() {
		//Fixture SetUp	
		//Exercise SUT
		Dinheiro dezDolates = Delegates.criaDezDolares();
		//Results Verification
		assertEquals(1000, dezDolates.obterQuantiaEmEscala().intValue());
		assertEquals(Moeda.USD, dezDolates.obterMoeda());
		//Fixture TearDown
	}
	
	@Test
	public void criaDinheiroCHF() {
		//Fixture SetUp	
		//Exercise SUT
		Dinheiro dezCHF = Delegates.criaDezCHF();
		//Results Verification
		assertEquals(1000, dezCHF.obterQuantiaEmEscala().intValue());
		assertEquals(Moeda.CHF, dezCHF.obterMoeda());
		//Fixture TearDown
	}
	
	@Test
	public void stringDinheiroDezReais() {
		//Fixture SetUp
		Dinheiro dezReais = Delegates.criaDezReais();
		//Exercise SUT
		String formatado = dezReais.toString();
		//Results Verification
		assertEquals("10,00 BRL", formatado);
		//Fixture TearDown
	}
	
	@Test
	public void stringDinheiroDezDolares() {
		//Fixture SetUp
		Dinheiro dezDolates = Delegates.criaDezDolares();
		//Exercise SUT
		String formatado = dezDolates.toString();
		//Results Verification
		assertEquals("10,00 USD", formatado);
		//Fixture TearDown
	}
	
	@Test
	public void stringDinheiroDezCHF() {
		//Fixture SetUp
		Dinheiro dezCHF = Delegates.criaDezCHF(); 
		//Exercise SUT
		String formatado = dezCHF.toString();
		//Results Verification
		assertEquals("10,00 CHF", formatado);
		//Fixture TearDown
	}
}
