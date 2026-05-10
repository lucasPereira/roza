package tdd.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tdd.PrioridadeOcorrencia;
import tdd.TipoOcorrencia;
import tdd.model.Empresa;
import tdd.model.Funcionario;
import tdd.model.Ocorrencia;
import tdd.model.Projeto;

public class TestProjeto {

	private Projeto projeto;
	private Funcionario funcionario;
	
	@Before
	public void setup() {
		Empresa empresa = new Empresa("Google");
		this.projeto = empresa.createProjeto("projeto1");
		this.funcionario = new Funcionario("Pedro");
	}
	
	@Test
	public void testCreateOcorrencia() {
		String resumo = "Resolver bug";
		
		Ocorrencia ocorrencia = this.projeto.createOcorrencia(this.funcionario, resumo,
				PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG);
		
		assertTrue(this.funcionario.getOcorrencias().contains(ocorrencia));
		assertEquals(ocorrencia.getResponsavel(), funcionario);
		assertEquals(ocorrencia.getResumo(), resumo);
		assertEquals(ocorrencia.getPrioridade(), PrioridadeOcorrencia.ALTA);
		assertEquals(ocorrencia.getTipo(), TipoOcorrencia.BUG);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateOcorrenciaOutOfLimit() {
		String resumo = "Resolver bug";
		
		TestProjetoHelper.createOcorrencias(this.projeto, this.funcionario, resumo,
				PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG, 10);
		
		this.projeto.createOcorrencia(this.funcionario, resumo,
				PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG);
	}
	
	@Test
	public void testCreateMaxAmountOfOcorrencias() {
		String resumo = "Resolver bug";
		
		TestProjetoHelper.createOcorrencias(this.projeto, this.funcionario, resumo,
				PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG, 10);
		
		assertEquals(10, this.projeto.getOcorrencias().size());
		assertEquals(10, this.funcionario.getOcorrencias().size());
	}
}
