package testes;

import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import domain.Empresa;
import domain.Funcionario;
import domain.Ocorrencia;
import domain.Projeto;

public class TesteEmpresa {
	private Empresa Apple;
	private Empresa AppleR;
	private Empresa Angeloni;
	private Funcionario Joao;
	private Funcionario Marcio;
	private Funcionario Cesar;
	
	@Before
	public void setUp(){
		Apple = new Empresa("Apple");	// Existe a Apple dos computadores e Apple Records da musica
		AppleR = new Empresa("Apple"); //
		Angeloni = new Empresa("Angeloni"); //
		Joao = new Funcionario("Joao",Apple);
		Marcio= new Funcionario("Marcio",Apple);
		Cesar= new Funcionario("Cesar",AppleR);
		return;
	}
	
	
	@Test
	public void nomeDeEmpresa() throws Exception {
		assertThat(Apple.nome(),equalToIgnoringCase("Apple"));
		assertThat(AppleR.nome(),equalToIgnoringCase("apple"));
		
	}
	
	@Test
	public void nomeVazioEmpresa() throws Exception {
		try {
		Empresa EmpresaVazia = new Empresa(""); 
		}
		catch(Error err){
			System.out.println("Teste de nome vazio resultou em erro :  "+ err);
		}
	}
	
	@Test
	public void listaFuncionarioVaziaDaEmpresa() throws Exception {
		assertThat(Angeloni.listFuncionarios(),is(new ArrayList<Funcionario>()));
		
	}
	@Test
	public void listaProjetoVaziaDaEmpresa() throws Exception {
		assertThat(Angeloni.listProjetos(),is(new ArrayList<Projeto>()));
		
	}
	
}


