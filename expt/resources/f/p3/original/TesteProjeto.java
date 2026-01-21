package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.Empresa;
import src.Funcionario;
import src.Ocorrencia;
import src.Projeto;

public class TesteProjeto {
	
	private Empresa empresa;
	private Projeto projeto;
	private Funcionario responsavel;
	
	@Before
	public void config(){
		this.empresa = new Empresa();
		this.empresa.cadastrarProjeto("Projeto");
		this.projeto = this.empresa.getProjetos().get(0);
		this.responsavel = new Funcionario("Joao");
	}
	
	@Test (expected = Exception.class)
	public void cadastrar11Ocorrencia() throws Exception{
		String resumo = "A primeira ocorrencia.";
		this.projeto.cadastrarOcorrencias("OC1", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC2", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC3", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC4", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC5", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC6", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC7", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC8", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC9", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC10", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC11", resumo, responsavel);
		Ocorrencia ocorrencia = (Ocorrencia) projeto.getOcorrencias().get(0);
		
		assertEquals(resumo, ocorrencia.getResumo());
		assertEquals(1, this.projeto.getOcorrencias().size());
		assertEquals(1, ocorrencia.getId());
	}

}
