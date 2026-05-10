package teste;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteAgencia {
	@Test
	public void criarAgenciaEVerificarRetornoCorretoDoBanco() {
		// Fixture Setup
		SistemaBancario sistemaBancario = new SistemaBancario();
		
		Banco bancoCaixa = sistemaBancario.criarBanco("Caixa", Moeda.BRL);
		Agencia agenciaCaixaTrindade = bancoCaixa.criarAgencia("Trindade");
		
		// Exercise SUT
		Banco bancoDaAgenciaTrindade = agenciaCaixaTrindade.obterBanco();
		
		// Result Verification
		assertEquals(bancoDaAgenciaTrindade, bancoCaixa);
	}
	@Test
	public void criarAgenciaEVerificarRetornoCorretoDoNome() {
		// Fixture Setup
		SistemaBancario sistemaBancario = new SistemaBancario();
		
		Banco bancoCaixa = sistemaBancario.criarBanco("Caixa", Moeda.BRL);
		Agencia agenciaCaixaTrindade = bancoCaixa.criarAgencia("Trindade");
		
		// Exercise SUT
		String nomeAgenciaCaixaTrindadeObtido = agenciaCaixaTrindade.obterNome();
		
		// Result Verification
		assertEquals("Trindade", nomeAgenciaCaixaTrindadeObtido);
	}
}
