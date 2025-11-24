package teste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Conta;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteConta {
	private SistemaBancario sistemaBancario;
	
	private Banco bancoCaixa;
	private Agencia agenciaCaixaTrindade;
	private Conta contaCaixaTrindade;
	private String titularCaixaTrindade;
	
	@Before
	public void setup() {
		// Fixture Setup
		this.sistemaBancario = new SistemaBancario();
		this.titularCaixaTrindade = "Ricardo";
		
		this.bancoCaixa = sistemaBancario.criarBanco("Caixa", Moeda.BRL);
		this.agenciaCaixaTrindade = bancoCaixa.criarAgencia("Trindade");
		this.contaCaixaTrindade = agenciaCaixaTrindade.criarConta(this.titularCaixaTrindade);
	}
	
	@Test
	public void verificarRetornoCorretoDaAgencia() {
		// Exercise SUT
		Agencia agenciaRetornada = contaCaixaTrindade.obterAgencia();
		
		// Result Verification
		assertEquals(this.agenciaCaixaTrindade, agenciaRetornada);
	}
	@Test
	public void verificarRetornoCorretoDoTitular() {
		// Exercise SUT
		String titularObtido = this.contaCaixaTrindade.obterTitular();
		
		// Result Verification
		assertEquals(this.titularCaixaTrindade, titularObtido);
	}
	@Test
	public void verificarSeSaldoZero() {
		// Result Verification
		assertTrue(this.contaCaixaTrindade.calcularSaldo().zero());
	}
}
