package Enunciado;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class Teste_empresa {
public Empresa empresaPaulo;
public Empresa empresaHiroki;
	
	@Before
	public void configurar() {
		empresaPaulo = new Empresa("Restaurante Massashin");
		empresaPaulo.contrataFunc("Igor");
	}
	
// FAZENDO TESTES EM RELAÇÃO A EMPRESA
	
	@Test
	public void geraEmpresaComNome() throws Exception {
		String nomeEmpresa = "Restaurante Massashin";
		empresaHiroki = new Empresa(nomeEmpresa);
		assertEquals("Restaurante Massashin", empresaPaulo.geraNomeEmpresa()); //altera Empresa.java
		assertEquals(0, empresaHiroki.numFuncionarios()); //altera Empresa.java
	}
	
	@Test
	public void criaEmpresaAnonima() throws Exception {
		String nomeEmpresa = "";
		empresaHiroki = new Empresa(nomeEmpresa);
		assertEquals("Anonimo", empresaHiroki.geraNomeEmpresa());
		assertEquals(0, empresaHiroki.numFuncionarios()); //altera Empresa.java
	}
	
//	@Test
//	public void criaEmpresa() throws Exception { // realmente uma criacao de empresa: Nome + funcionarios
//		String nomeEmpresa = "Restaurante Massashin";
//		empresaHiroki = new Empresa(nomeEmpresa);
//		assertEquals(nomeEmpresa, empresaHiroki.geraNomeEmpresa());
//		
//	}
	
	@Test
	public void contrataFuncionarioReal() throws Exception{
		empresaPaulo.contrataFunc("Lucas"); //Adiciona o metodo contrataFunc() em Empresa.java
		// Ja tinhamos add Igor como funcionario da empresa Paulo
		assertEquals(2, empresaPaulo.numFuncionarios());
	}
	
	@Test
	public void contrataFuncionarioAnonimo() throws Exception{
		empresaPaulo.contrataFunc("");
		//podemos verificar se houve ou não contratação de um funcionario sem nome
		// Ja tinhamos add Igor como funcionario da empresa Paulo
		assertEquals(1, empresaPaulo.numFuncionarios());
	}
	

}
