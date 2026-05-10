package teste;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;

public class TesteValorMonetario {
	@Test
	public void criarValorMonetarioBRLETestarSaldoZero() {
		// Fixture Setup
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		
		// Result Verification
		assertTrue(valorMonetario.zero());
	}
	@Test
	public void criarValorMonetarioCHFETestarSaldoZero() {
		// Fixture Setup
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.CHF);
		
		// Result Verification
		assertTrue(valorMonetario.zero());
	}
	@Test
	public void criarValorMonetarioUSDETestarSaldoZero() {
		// Fixture Setup
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.USD);
		
		// Result Verification
		assertTrue(valorMonetario.zero());
	}
	@Test
	public void subtrairDinheiro() {
		// Fixture Setup
		ValorMonetario valor = new ValorMonetario(Moeda.BRL);
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 10, 0);
		
		// Exercise SUT
		ValorMonetario valorResultante = valor.subtrair(dinheiro);
		
		// Result Verification
		assertTrue(valorResultante.negativo());
	}
	@Test
	public void somarDinheiro() {
		// Fixture Setup
		ValorMonetario valor = new ValorMonetario(Moeda.BRL);
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 10, 0);
		
		// Exercise SUT
		ValorMonetario valorResultante = valor.somar(dinheiro);
		
		// Result Verification
		assertFalse(valorResultante.negativo());
	}
	
}
