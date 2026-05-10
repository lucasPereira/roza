package br.ufsc.ine.leb.sistemaBancario;

import org.junit.*; 

import static org.junit.Assert.*; 
 
//implicit tests set by the private function
public class BankTest {
	
	SistemaBancarioTest sisBankTest;
	Banco bancoNormal;
	
	@Before
	public void setTestBank(){
		sisBankTest = new SistemaBancarioTest();
		bancoNormal = new Banco ("Banco do Brasil", Moeda.BRL, new Dinheiro(Moeda.BRL, 0,0));
	}
	
	@Test 
	public void bankConstructor(){
		Banco banco = new Banco ("Banco do Brasil", Moeda.BRL, new Dinheiro(Moeda.BRL, 0,0));
		assertNotNull(banco);
	}
	@Test
	public void weirdAgencyName() {
		String nome = "kl2jkj\0\n\n\t\231";
		Agencia agencia= bancoNormal.criarAgencia(nome);
		
		assertEquals( agencia.obterBanco() , bancoNormal );
		assertEquals(agencia.obterNome(), nome);	
	}
	
	@Test
	public void longAgencyName() {
		String nome= "";
		
		for(int i =0; i<10000;i++)
			nome = nome.concat("aa");
		
		Agencia agencia= bancoNormal.criarAgencia(nome);		
		assertEquals( agencia.obterBanco() , bancoNormal );
		assertEquals(agencia.obterNome(), nome);
	}
	
	private void moedaTest(Moeda testCoin){
		Banco bank =  new Banco ("Banco", testCoin, new Dinheiro(testCoin, 0,0));
		Moeda bankCoin = bank.obterMoeda();
		assertEquals( bankCoin.obterSimbolo(), testCoin.obterSimbolo());
		assertEquals( bankCoin.obterBaseFracionaria(), testCoin.obterBaseFracionaria());
	}
	
	@Test
	public void moedaBancoBrasil(){
		moedaTest( Moeda.BRL );
	}
	@Test
	public void moedaBancoUSA(){
		moedaTest( Moeda.USD );
	}
	@Test
	public void moedaBancoFr(){
		moedaTest( Moeda.CHF );
	}
	
}
