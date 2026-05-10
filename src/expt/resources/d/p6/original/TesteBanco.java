package teste.br.ufsc.ine.leb.sistemaBancario.banco;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;
import teste.br.ufsc.ine.leb.sistemaBancario.helpers.TesteHelper;

public class TesteBanco {

	@Test
	public void criaBanco() {
		// Fixture Setup - Inline Setup + Delegated Setup
		SistemaBancario sistemaBancario = TesteHelper.criaSistemabancario();
		String nomeDoBanco = new String("Banco Dahora");
		Moeda moedaDoBanco = Moeda.BRL;
		
		// Exercise SUT
		sistemaBancario.criarBanco(nomeDoBanco, moedaDoBanco);
		
		// Result Verification
		Banco banco = sistemaBancario.obterBancos().get(0);
		assertEquals(nomeDoBanco, banco.obterNome());
		assertEquals(moedaDoBanco, banco.obterMoeda());
		
		// Fixture Teardown
	}
	
}
