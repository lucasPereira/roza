package testes;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;

public class TesteValorMonetario {

	@Test
	public void testeConstrutor() {
		//Fixture SetUp
		//Exercise SUT
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		//Results Verification
		assertTrue(valorMonetario.zero());
		assertEquals("0,00", valorMonetario.formatado());
		//Fixture TearDown
	}
	
	@Test (expected=UnsupportedOperationException.class)
	public void testeSomaValorMonetarioInvalido() {
		//Fixture SetUp - Delegated
		ValorMonetario valorMonetario = AjudanteDeTestes.retornaValorMonetarioBrasileiro();
		Dinheiro dezDolares = AjudanteDeTestes.retornaDezDolares();
		//Exercise SUT
		valorMonetario.somar(dezDolares);
		//Results Verification
		
		//Fixture TearDown
	}
	
	@Test
	public void testeSomaValorMonetarioValido() {
		//Fixture SetUp - Delegated
		ValorMonetario valorMonetario = AjudanteDeTestes.retornaValorMonetarioBrasileiro();
		Dinheiro dezReais = AjudanteDeTestes.retornaDezReais();
		//Exercise SUT
		ValorMonetario novoValor = valorMonetario.somar(dezReais);
		//Results Verification
		assertEquals(dezReais, novoValor.obterQuantia());
		assertFalse(novoValor.negativo());
		//Fixture TearDown
	}
	
	@Test (expected=UnsupportedOperationException.class)
	public void testeSubtraiValorMonetarioInvalido() {
		//Fixture SetUp - Delegated
		ValorMonetario valorMonetario = AjudanteDeTestes.retornaValorMonetarioBrasileiro();
		Dinheiro dezDolares = AjudanteDeTestes.retornaDezDolares();
		//Exercise SUT
		valorMonetario.subtrair(dezDolares);
		//Results Verification
		
		//Fixture TearDown
	}
	
	@Test
	public void testeSubtraiValorMonetarioValido() {
		//Fixture SetUp - Delegated
		ValorMonetario valorMonetario = AjudanteDeTestes.retornaValorMonetarioBrasileiro();
		Dinheiro dezReais = AjudanteDeTestes.retornaDezReais();
		//Exercise SUT
		ValorMonetario novoValor = valorMonetario.subtrair(dezReais);
		//Results Verification
		assertTrue(novoValor.negativo());
		assertEquals(dezReais.negativo(), novoValor);
		//Fixture TearDown
	}
	
	

}
