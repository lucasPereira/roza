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

public class testeProjeto {
	
	private Funcionario Joao;
	private Funcionario Marcio;
	private Funcionario Cesar;
	private Empresa AppleR;
	private Empresa Apple;
	private Projeto p1,p2,p3;

	@Before
	public void setUp(){
		Apple = new Empresa("Apple");	// Existe a Apple dos computadores e Apple Records da musica
		AppleR = new Empresa("Apple"); //
		Joao = new Funcionario("Joao",Apple);
		Marcio= new Funcionario("Marcio",Apple);
		Cesar= new Funcionario("Cesar",AppleR);
		p1=new Projeto("Macintosh",Apple);
		p2=new Projeto("Ipod",Apple);
		p3=new Projeto("White Album",AppleR);
		return;
	}
	
	@Test
	public void checkProjects() throws Exception {
		List<Projeto> testeProjeto = new ArrayList<Projeto>();
		List<Projeto> testeProjetoA = new ArrayList<Projeto>();
		testeProjeto.add(p1);
		testeProjeto.add(p2);
		testeProjetoA.add(p3);
		assertThat(Apple.listProjetos(),hasItem(p1));
		assertThat(Apple.listProjetos(),hasItem(p2));
		assertEquals(Apple.listProjetos(),testeProjeto);
		assertEquals(AppleR.listProjetos(),testeProjetoA);
	}
	
	
	@Test
	public void nomeDoProjeto() throws Exception {
		assertThat(p3.getNome(),equalToIgnoringCase("white album"));
	}
	
	@Test
	public void testeEmpresaDoProjeto() throws Exception {
		assertThat(p3.getEmpresa(),is(AppleR));
	}
	
	
	
	@Test
	public void nomeDoProjetoVazio() throws Exception {
		try {
			Projeto ProjetoVazio = new Projeto("",Apple); 
			}
			catch(Error err){
				System.out.println("Teste de nome vazio resultou em erro :  "+ err);
			}
	}
	
	
	@Test
	public void listaOcorrenciaVaziaDoProjeto() throws Exception {
		Projeto teste=new Projeto("Abbey Road",AppleR);
		assertThat(teste.listOcorrencias(),is(new ArrayList<Ocorrencia>()));
		
	}
	
	
}