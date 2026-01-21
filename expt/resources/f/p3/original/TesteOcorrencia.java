package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import src.Empresa;
import src.Funcionario;
import src.Ocorrencia;
import src.Prioridade;
import src.Projeto;

public class TesteOcorrencia {
	
	private Empresa empresa;
	private Projeto projeto;
	private Funcionario responsavel;
	private String resumo;
	
	@Before
	public void config(){
		this.empresa = new Empresa();
		this.empresa.cadastrarProjeto("Projeto");
		this.projeto = this.empresa.getProjetos().get(0);
		this.responsavel = new Funcionario("Joao");
		resumo = "A primeira ocorrencia.";
	}

	@Test
	public void modificarPrioridade() throws Exception {
		this.projeto.cadastrarOcorrencias("OC1", resumo, responsavel);
		Ocorrencia ocorrencia = (Ocorrencia) projeto.getOcorrencias().get(0);
		ocorrencia.setPrioridade(Prioridade.ALTA);
		
		assertEquals(Prioridade.ALTA, ocorrencia.getPrioridade());
	}
	
	@Test
	public void modificarEstado() throws Exception {
		this.projeto.cadastrarOcorrencias("OC1", resumo, responsavel);
		Ocorrencia ocorrencia = (Ocorrencia) projeto.getOcorrencias().get(0);
		ocorrencia.setEstado(false);
		assertEquals(false, ocorrencia.getEstado());
	}
	
	@Test
	public void modificarResponsavel() throws Exception {
		this.projeto.cadastrarOcorrencias("OC1", resumo, responsavel);
		Ocorrencia ocorrencia = (Ocorrencia) projeto.getOcorrencias().get(0);
		Funcionario novoResponsavel = new Funcionario("Novo");
		ocorrencia.setResponsavel(novoResponsavel);
		
		assertEquals(novoResponsavel.getName(), ocorrencia.getResponsavel().getName());
	}
}
