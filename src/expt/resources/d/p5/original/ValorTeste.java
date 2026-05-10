package br.ufsc.ine.leb.sistemaBancario;

import org.junit.*; 

import static org.junit.Assert.*; 
 
public class ValorTeste {
	public Moeda moeda;
	public Dinheiro zero;
	
	@Before
	public void init(){
		moeda = Moeda.USD;
		zero = new Dinheiro(moeda, 0, 0);
	}
	
	@Test
	public void creationTest(){
		ValorMonetario vm = new ValorMonetario(moeda);
		assertEquals(zero, vm.obterQuantia() );	
	}
	@Test
	public void add(){
		ValorMonetario vm = new ValorMonetario(moeda);
		Dinheiro current = vm.obterQuantia();
		Dinheiro mod = new Dinheiro(moeda, 5, 0);
		vm = vm.somar(mod);
		Dinheiro after = vm.obterQuantia();
		Integer result =  after.obterQuantiaEmEscala()- current.obterQuantiaEmEscala();
		assertEquals( new Integer(500), result );	
	}
	
	@Test
	public void negative(){
		ValorMonetario vm = new ValorMonetario(moeda);
		Dinheiro mod = new Dinheiro(moeda, 5, 0);
		vm= vm.subtrair(mod);
		assertEquals( vm.negativo(), true );	
	}
	@Test
	public void subtract(){
		ValorMonetario vm = new ValorMonetario(moeda);
		Dinheiro current = vm.obterQuantia();
		Dinheiro mod = new Dinheiro(moeda, 5, 0);
		vm = vm.subtrair(mod);
		Dinheiro after = vm.obterQuantia();
		Integer result = current.obterQuantiaEmEscala() - after.obterQuantiaEmEscala();
		assertEquals( new Integer(-500), result );	
	}
}
