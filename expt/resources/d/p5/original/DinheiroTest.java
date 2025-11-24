package br.ufsc.ine.leb.sistemaBancario;

import org.junit.*; 

import static org.junit.Assert.*; 
 
//inline tests
public class DinheiroTest {

	Moeda moeda;
	
	@Before
	public void init()
	{
		moeda = Moeda.BRL;
	}
	
	@Test
	public void creationTest()
	{
		Dinheiro dindin = new Dinheiro(moeda, new Integer(5),new Integer(50));
		assertEquals(  moeda, dindin.obterMoeda());
		assertEquals( dindin.obterQuantiaEmEscala(), new Integer(550));	
	}

	@Test
	public void formatTest()
	{
		Dinheiro dindin = new Dinheiro(moeda, new Integer(5),new Integer(50));
		String str = dindin.formatado()	;
		assertEquals( "5,50 BRL", str);
	}
}
