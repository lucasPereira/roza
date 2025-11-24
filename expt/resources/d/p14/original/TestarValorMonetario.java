package sistemaBancario;

import static org.junit.Assert.*;
import org.junit.*;
import sistemaBancario.Dinheiro;
import sistemaBancario.Moeda;
import sistemaBancario.ValorMonetario;

public class TestarValorMonetario {

	Dinheiro DezReais;
	ValorMonetario valorMonetario;
	
	//Implicit Setup 
	@Before
	public void criarValorMonetario(){
		DezReais =  new Dinheiro(Moeda.BRL, 10, 00);
		valorMonetario = new ValorMonetario(Moeda.BRL);	
	}

	@Test
	public void somarValorMonetario (){
		//Fixture Setup
		//Exercise SUT
		ValorMonetario monetario = valorMonetario.somar(DezReais);	
		//Result Verification
		assertEquals("+10,00 BRL", monetario.formatado());
		//Fixture Teardown
	}
	
	@Test
	public void subtrairValorMonetario (){
		//Fixture Setup
		//Exercise SUT
		ValorMonetario monetario = valorMonetario.subtrair(DezReais);	
		//Result Verification
		assertEquals("-10,00 BRL", monetario.formatado());
		//Fixture Teardown
	}

}
