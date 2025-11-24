package teste.br.ufsc.ine.leb.sistemaBancario;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Conta;
import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Entrada;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;

public class TesteConta {
	
	@Test
	public void saldoConta() {
		//Fixture Setup
		Conta novaConta = TesteSetup.criaConta();
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		Entrada entrada = new Entrada(novaConta, nove);
		novaConta.adicionarTransacao(entrada);
		//Exercise SUT
		ValorMonetario saldoConta = novaConta.calcularSaldo();
		//Result Verification
		assertEquals("9,00 BRL", saldoConta.obterQuantia().formatado());
		//Fixture Teardown
		
	}

}
