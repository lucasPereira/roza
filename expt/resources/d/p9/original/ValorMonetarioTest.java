package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;
import br.ufsc.ine.leb.sistemaBancario.Dinheiro;;

public class ValorMonetarioTest {
	
	private ValorMonetario valorMonetarioBRL;
	private Dinheiro dinheiroCemReais;
	private Dinheiro dinheiroCinquentaReais;
	
	@Before
	public void setup() {
		this.valorMonetarioBRL = new ValorMonetario(Moeda.BRL);  
		this.dinheiroCemReais = TestHelper.criarDinheiroCemReais();
		this.dinheiroCinquentaReais = new Dinheiro(Moeda.BRL, 50, 00);
	}
	
	@Test
	public void criarValorMonetarioZerado(){
		//Fixture Setup
		//Exercise SUT
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL); 
		//Result Verification
		assertEquals("0,00", valorMonetario.toString());
		//Fixture Teardown
	}

	@Test
	public void somaValorMonetario(){
		//Fixture Setup
		//Exercise SUT
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCinquentaReais);
		//Result Verification
		assertEquals("+150,00 BRL", valorMonetarioBRL.toString());
		//Fixture Teardown
	}

	@Test
	public void subtraiValorMonetario(){
		//Fixture Setup
		//Exercise SUT
		valorMonetarioBRL = valorMonetarioBRL.subtrair(dinheiroCemReais);
		valorMonetarioBRL = valorMonetarioBRL.subtrair(dinheiroCinquentaReais);
		//Result Verification
		assertEquals("-150,00 BRL", valorMonetarioBRL.toString());
		//Fixture Teardown
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void somarBRLComUSD(){
		//Fixture Setup
		Dinheiro dinheiroCemDolares = new Dinheiro(Moeda.USD, 100, 00);
		//Exercise SUT
		valorMonetarioBRL = valorMonetarioBRL.subtrair(dinheiroCemReais);
		valorMonetarioBRL = valorMonetarioBRL.subtrair(dinheiroCemDolares);
		//Result Verification
		assertEquals("-150,00 BRL", valorMonetarioBRL.toString());
		//Fixture Teardown
	}
	
	@Test
	public void obterQuantia(){
		//Fixture Setup
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		//Exercise SUT
		Dinheiro dinheiroObtido = valorMonetarioBRL.obterQuantia();
		//Result Verification
		assertEquals("Dinheiro", dinheiroCemReais, dinheiroObtido);
		//Fixture Teardown
	}
	
	@Test
	public void metodoNegativoComValorMonetarioNegativo(){
		//Fixture Setup
		valorMonetarioBRL = valorMonetarioBRL.subtrair(dinheiroCemReais);
		//Exercise SUT
		boolean ehNegativo = valorMonetarioBRL.negativo();
		//Result Verification
		assertEquals(true, ehNegativo);
		//Fixture Teardown
	}
	
	@Test
	public void metodoNegativoComValorMonetarioPositivo(){
		//Fixture Setup
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		//Exercise SUT
		boolean ehNegativo = valorMonetarioBRL.negativo();
		//Result Verification
		assertEquals(false, ehNegativo);
		//Fixture Teardown
	}
	
	@Test
	public void metodoZeroComValorMonetarioCem(){
		//Fixture Setup
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		//Exercise SUT
		boolean ehZero = valorMonetarioBRL.zero();
		//Result Verification
		assertEquals(false, ehZero);
		//Fixture Teardown
	}
	
	@Test
	public void metodoZeroComValorMonetarioZero(){
		//Fixture Setup
		//Exercise SUT
		boolean ehZero = valorMonetarioBRL.zero();
		//Result Verification
		assertEquals(true, ehZero);
		//Fixture Teardown
	}
	
	@Test
	public void comparaValoresMonetariosIguais(){
		//Fixture Setup
		ValorMonetario valorMonetarioComparacao = new ValorMonetario(Moeda.BRL);
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		valorMonetarioComparacao = valorMonetarioComparacao.somar(dinheiroCemReais);
		//Exercise SUT
		boolean ehIgual = valorMonetarioBRL.equals(valorMonetarioComparacao);
		//Result Verification
		assertEquals(true, ehIgual);
		//Fixture Teardown
	}
	
	@Test
	public void comparaValoresMonetariosComQuantiasDiferentes(){
		//Fixture Setup
		ValorMonetario valorMonetarioComparacao = new ValorMonetario(Moeda.BRL);
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		valorMonetarioComparacao = valorMonetarioComparacao.somar(dinheiroCinquentaReais);
		//Exercise SUT
		boolean ehIgual = valorMonetarioBRL.equals(valorMonetarioComparacao);
		//Result Verification
		assertEquals(false, ehIgual);
		//Fixture Teardown
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void comparaValoresMonetariosComMoedasDiferentes(){
		//Fixture Setup
		ValorMonetario valorMonetarioComparacao = new ValorMonetario(Moeda.USD);
		valorMonetarioBRL = valorMonetarioBRL.somar(dinheiroCemReais);
		valorMonetarioComparacao = valorMonetarioComparacao.somar(dinheiroCemReais);
		//Exercise SUT
		boolean ehIgual = valorMonetarioBRL.equals(valorMonetarioComparacao);
		//Result Verification
		assertEquals(false, ehIgual);
		//Fixture Teardown
	}
}
