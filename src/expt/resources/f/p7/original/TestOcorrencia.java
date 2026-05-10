package tdd;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tdd.Funcionario;
import tdd.Ocorrencia;
import tdd.Ocorrencia.Estado;
import tdd.Ocorrencia.Prioridade;
import tdd.Ocorrencia.Tipo;

public class TestOcorrencia {

	private Funcionario meuResponsavel;
	private Ocorrencia minhaOcorrencia;
	
	@Before
	public void fixtureSetup() throws Exception {
		String resumo = "Do que se trata a ocorrência";
		meuResponsavel = new Funcionario();
		minhaOcorrencia = new Ocorrencia(resumo, meuResponsavel, Tipo.MELHORIA);
	}
	
	@Test
	public void completarOcorrencia() throws Exception {
		// Exercise SUT
		minhaOcorrencia.completar(meuResponsavel);
		// Result Verification
		assertEquals(Estado.COMPLETADA, minhaOcorrencia.getEstado());
		assertFalse(meuResponsavel.getOcorrenciasAbertas().contains(minhaOcorrencia));
	}
	@Test(expected=OcorrenciaJaCompletada.class)
	public void completarOcorrencia_jaCompletada() throws Exception {
		// Fixture Setup
		minhaOcorrencia.completar(meuResponsavel);
		// Exercise SUT
		minhaOcorrencia.completar(meuResponsavel);
	}
	@Test(expected=OcorrenciaManipuladaPorUmNaoResponsavel.class)
	public void completarOcorrencia_manipuladaPorUmNaoResponsavel() throws Exception {
		Estado estadoOriginal = minhaOcorrencia.getEstado();
		try {
			// Exercise SUT
			minhaOcorrencia.completar(new Funcionario());
		} catch (OcorrenciaManipuladaPorUmNaoResponsavel e) {
			// Result Verification
			assertEquals(estadoOriginal, minhaOcorrencia.getEstado());
			throw e;
		}
	}

	void setPrioridade(Prioridade novaPrioridade) throws Exception {
		// Exercise SUT
		minhaOcorrencia.setPrioridade(novaPrioridade);
		// Result Verification
		assertEquals(novaPrioridade, minhaOcorrencia.getPrioridade());
	}
	@Test	public void setPrioridade_ALTA() throws Exception	{setPrioridade(Prioridade.ALTA);}
	@Test	public void setPrioridade_MEDIA() throws Exception	{setPrioridade(Prioridade.MEDIA);}
	@Test	public void setPrioridade_BAIXA() throws Exception	{setPrioridade(Prioridade.BAIXA);}
	@Test(expected=OcorrenciaJaCompletada.class)
	public void setPrioridade_deUmaOcorrenciaJaCompletada() throws Exception {
		// Fixture Setup
		Prioridade prioridadeOriginal = minhaOcorrencia.getPrioridade();
		minhaOcorrencia.completar(meuResponsavel);
		try {
			// Exercise SUT
			minhaOcorrencia.setPrioridade(Prioridade.ALTA);
		} catch (OcorrenciaJaCompletada e) {
			assertEquals(prioridadeOriginal, minhaOcorrencia.getPrioridade());
			throw e;
		}
	}
	
	@Test
	public void setResponsavel() throws Exception {
		// Fixture Setup
		Funcionario novoResponsavel = new Funcionario();
		// Exercise SUT
		minhaOcorrencia.setResponsavel(novoResponsavel);
		// Result Verification
		assertFalse(meuResponsavel.getOcorrenciasAbertas().contains(minhaOcorrencia));
		assertEquals(novoResponsavel, minhaOcorrencia.getFuncionarioResponsavel());
	}
	@Test(expected=OcorrenciaJaCompletada.class)
	public void setResponsavel_deUmaOcorrenciaJaCompletada() throws Exception {
		// Fixture Setup
		minhaOcorrencia.completar(meuResponsavel);
		Funcionario novoResponsavel = new Funcionario();
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(novoResponsavel.getOcorrenciasAbertas());
		try {
			// Exercise SUT
			minhaOcorrencia.setResponsavel(novoResponsavel);
		}catch (OcorrenciaJaCompletada e) {
			// Result Verification
			assertEquals(listaDeOcorrencias, novoResponsavel.getOcorrenciasAbertas());
			throw e;
		}
	}
	@Test(expected=FuncionarioJaPossuiTalOcorrencia.class)
	public void setResponsavel_atualResponsavel() throws Exception {
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuResponsavel.getOcorrenciasAbertas());
		try {
			// Exercise SUT
			minhaOcorrencia.setResponsavel(meuResponsavel);
		}catch (FuncionarioJaPossuiTalOcorrencia e) {
			// Result Verification
			assertEquals(listaDeOcorrencias, meuResponsavel.getOcorrenciasAbertas());
			throw e;
		}
	}
}
