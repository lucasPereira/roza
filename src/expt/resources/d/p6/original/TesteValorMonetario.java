package teste.br.ufsc.ine.leb.sistemaBancario.dinheiro;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;
import teste.br.ufsc.ine.leb.sistemaBancario.helpers.TesteHelper;

public class TesteValorMonetario {

	@Test
	public void criaValorMonetario() {
		// Fixture Setup - Inline
		Moeda moeda = Moeda.BRL;
		
		// Exercise SUT
		ValorMonetario valorMonetario = new ValorMonetario(moeda);
		
		// Result Verification
		assertEquals(TesteHelper.quantiaFormatadoComMoeda(0, 0, moeda), valorMonetario.formatarSemSinal());
		
		// Fixture Teardown
	}
	
	@Test
	public void somaQuantiaAoValorMonetario() {
		// Fixture Setup
		Moeda moeda = Moeda.BRL;
		Integer quinhentosDinheiros = new Integer(500);
		Integer zeroCentavosDeDinheiros = new Integer(10);
		ValorMonetario valor = new ValorMonetario(moeda);
		Dinheiro dinheiro = new Dinheiro(moeda, quinhentosDinheiros, zeroCentavosDeDinheiros);
		
		// Exercise SUT
		ValorMonetario valorSomado = valor.somar(dinheiro);
		
		String valorFormatado = new String(TesteHelper.quantiaFormatadoComMoeda(quinhentosDinheiros, zeroCentavosDeDinheiros, moeda));
		// Result Verification
		assertEquals(valorFormatado, valorSomado.formatarSemSinal());
		
		// Fixture Teardown
	}
	
	@Test
	public void subtraiQuantiaAoValorMonetario() {
		// Fixture Setup
		
		// Exercise SUT
		
		// Result Verification
		
		// Fixture Teardown
	}
}
