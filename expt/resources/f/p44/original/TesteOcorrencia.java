package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tests.Ocorrencia.Estado;
import tests.Ocorrencia.Prioridade;
import tests.Ocorrencia.Tipo;

public class TesteOcorrencia {
	private Empresa empresa;
	private Projeto projeto;
	private Funcionario func;
	
	@Before
	public void setup() throws Exception {
		empresa = new Empresa("Google");
		projeto = new Projeto("Pixel 5a");
		func = new Funcionario("Bryan Lima", 0);
		empresa.addFunc(func);
		
	}

	@Test
	public void getResponsavelOcorrencia() throws Exception {
		Ocorrencia ocorr1 = new TestsHelper().makeOcorrencia(0, func);
		projeto.addOcorrencia(ocorr1);
		Funcionario responsavel = projeto.getOcorrencias().get(0).getResponsavel();
		
		assertEquals(0, responsavel.getId());
	}
	
	@Test
	public void trocaDeResponsavelOcorrenciaAberta() throws Exception {
		Ocorrencia ocorr1 = new TestsHelper().makeOcorrencia(0, func);
		projeto.addOcorrencia(ocorr1);
		Funcionario func2 = new Funcionario("Patricia Vilain", 1);
		projeto.getOcorrencias().get(0).trocarResponsavel(func2);
		
		Funcionario responsavel = projeto.getOcorrencias().get(0).getResponsavel();
		assertEquals(1, responsavel.getId());
	}
	
	@Test(expected = Exception.class)
	public void trocaDeResponsavelOcorrenciaFinalizada() throws Exception {
		Ocorrencia ocorr1 = new TestsHelper().makeOcorrencia(0, func);
		projeto.addOcorrencia(ocorr1);
		func.finalizarOcorrencia(ocorr1.getId());
		
		Funcionario func2 = new Funcionario("Patricia Vilain", 1);
		ocorr1.trocarResponsavel(func2);		
	}
	
	@Test
	public void trocaDePrioridadeOcorrenciaAberta() throws Exception {
		Ocorrencia ocorr1 = new TestsHelper().makeOcorrencia(0, func);
		projeto.addOcorrencia(ocorr1);
		ocorr1.alterarPrioridade(Prioridade.BAIXA);
		Prioridade prioridade = ocorr1.getPrioridade();
		assertEquals(Prioridade.BAIXA, prioridade);
	}
	
	@Test (expected = Exception.class)
	public void trocaDePrioridadeOcorrenciaFinalizade() throws Exception {
		Ocorrencia ocorr1 = new TestsHelper().makeOcorrencia(0, func);
		projeto.addOcorrencia(ocorr1);
		func.finalizarOcorrencia(ocorr1.getId());
		ocorr1.alterarPrioridade(Prioridade.BAIXA);
	}
	
	@Test
	public void ocorrenciaCriadaAberta() throws Exception {
		Ocorrencia ocorr1 = new TestsHelper().makeOcorrencia(0, func);
		Estado estado = ocorr1.getEstado();
		
		assertEquals(Estado.ABERTA, estado);
	}
	
	@Test
	public void ocorrenciaTipo() throws Exception {
		Ocorrencia ocorr1 = new TestsHelper().makeOcorrencia(0, func);
		Tipo tipo = ocorr1.getTipo();
		
		assertEquals(Tipo.TAREFA, tipo);
	}
	
}
