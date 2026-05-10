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

public class testeOcorrencia {
		
	private Funcionario Joao;
	private Funcionario Marcio;
	private Funcionario Cesar;
	private Empresa Apple;
	private Empresa AppleR;
	private Projeto p1;
	private Ocorrencia o1;

	@Before
	public void setUp(){
		Apple = new Empresa("Apple");	// Existe a Apple dos computadores e Apple Records da musica
		AppleR = new Empresa("Apple");	// Existe a Apple dos computadores e Apple Records da musica
		Joao = new Funcionario("Joao",Apple);
		Marcio= new Funcionario("Marcio",Apple);
		Cesar= new Funcionario("Cesar",AppleR);
		p1=new Projeto("Macintosh",Apple);
		o1=new Ocorrencia("bug","bug na criacao de threads","alta",p1,Joao);
		return;
	}
	
	@Test
	public void mudaResponsavelOcorrencia() throws Exception {
		assertEquals(o1.getResponsavel(),Joao);	
		o1.mudaResponsavel(Marcio);
		assertEquals(o1.getResponsavel(),Marcio);		
		
	}
	@Test
	public void mudaResponsavelAposTerminoOcorrencia() throws Exception {
		Joao.terminaOcorrencia(o1);
		o1.mudaResponsavel(Marcio);
		assertNotEquals(o1.getResponsavel(),Marcio);		
		
	}
	@Test
	public void prioridadeInvalida() throws Exception {
		try {
			Ocorrencia teste=new Ocorrencia("bug","bug na criacao de threads","pequena",p1,Joao);
			}
			catch(Error err){
				System.out.println("Teste de prioridade invalida resultou em erro :  "+ err);
			}
	}
	
	
	@Test
	public void tipoVazio() throws Exception {
		try {
			Ocorrencia teste=new Ocorrencia("","bug na criacao de threads","baixa",p1,Joao);
			}
			catch(Error err){
				System.out.println("Teste de tipo vazio resultou em erro :  "+ err);
			}
	}
	
	
	@Test
	public void resumoVazio() throws Exception {
		try {
			Ocorrencia teste=new Ocorrencia("bug","","baixa",p1,Joao);
			}
			catch(Error err){
				System.out.println("Teste de resumo vazio resultou em erro :  "+ err);
			}
	}
	
	
	@Test
	public void prioridadeVazia() throws Exception {
		try {
			Ocorrencia teste=new Ocorrencia("bug","bug na criacao de threads","",p1,Joao);
			}
			catch(Error err){
				System.out.println("Teste de prioridade vazia resultou em erro :  "+ err);
			}
	}
	
	@Test
	public void mudaPrioridadeOcorrencia() throws Exception {
		o1.setPrioridadeBaixa();
		assertEquals(o1.getPrioridade(),"baixa");
		
	}	
	
	@Test
	public void limiteOcorrencia() throws Exception {
		Ocorrencia o1=new Ocorrencia("bug","bug na criacao de threads","alta",p1,Marcio);
		Ocorrencia o2=new Ocorrencia("bug","bug na criacao de threads","alta",p1,Marcio);
		Ocorrencia o3=new Ocorrencia("bug","bug na criacao de threads","alta",p1,Marcio);
		Ocorrencia o4=new Ocorrencia("bug","bug na criacao de threads","alta",p1,Marcio);
		Ocorrencia o5=new Ocorrencia("bug","bug na criacao de threads","alta",p1,Marcio);
		Ocorrencia o6=new Ocorrencia("bug","bug na criacao de threads","alta",p1,Marcio);
		Ocorrencia o7=new Ocorrencia("bug","bug na criacao de threads","alta",p1,Marcio);
		Ocorrencia o8=new Ocorrencia("bug","bug na criacao de threads","alta",p1,Marcio);
		Ocorrencia o9=new Ocorrencia("bug","bug na criacao de threads","alta",p1,Marcio);
		Ocorrencia o10=new Ocorrencia("bug","bug na criacao de threads","alta",p1,Marcio);
		try {
			Ocorrencia teste=new Ocorrencia("bug","bug na criacao de threads","alta",p1,Marcio);
			}
			catch(Error err){
				System.out.println("Teste de limite de ocorrencias resultou em erro :  "+ err);
			}
		
		
	}	
}
