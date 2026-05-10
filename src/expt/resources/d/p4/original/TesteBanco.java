package teste;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteBanco {
	@Test
	public void criarBancoEVerificarNome() {
		// Fixture Setup
		SistemaBancario sistemaBancario = new SistemaBancario();
		
		// Exercise SUT
		Banco bancoCaixa = sistemaBancario.criarBanco("Caixa", Moeda.BRL);
		
		// Result Verification
		assertEquals(bancoCaixa.obterNome(), "Caixa");
	}
	@Test
	public void criarBancoEVerificarMoeda() {
		// Fixture Setup
		SistemaBancario sistemaBancario = new SistemaBancario();
		
		// Exercise SUT
		Banco bancoCaixa = sistemaBancario.criarBanco("Caixa", Moeda.BRL);
		
		// Result Verification
		assertEquals(bancoCaixa.obterMoeda(), Moeda.BRL);
	}
}
