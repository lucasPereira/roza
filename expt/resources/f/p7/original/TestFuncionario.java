package tdd;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tdd.Funcionario;
import tdd.Ocorrencia;
import tdd.Projeto;
import tdd.Ocorrencia.Estado;
import tdd.Ocorrencia.Tipo;

public class TestFuncionario {

	private Funcionario meuFuncionario;
	@Before
	public void fixtureSetup() throws Exception {
		meuFuncionario = new Funcionario();
	}
	
	@Test
	public void atribuiProjeto() throws Exception {
		// Fixture Setup
		Projeto novoProjeto = new Projeto();
		// Exercise SUT
		meuFuncionario.atribuirProjeto(novoProjeto);
		// Result Verification
		assertEquals(1, meuFuncionario.getProjetos().size());
		assertTrue(meuFuncionario.getProjetos().contains(novoProjeto));
	}
	@Test(expected=ProjetoJaAtribuido.class)
	public void atribuiProjeto_jaAtribuido() throws Exception {
		// Fixture Setup
		Projeto novoProjeto = new Projeto();
		meuFuncionario.atribuirProjeto(novoProjeto);
		List<Projeto> listaDeProjetos = new LinkedList<>(meuFuncionario.getProjetos());
		try {
			// Exercise SUT
			meuFuncionario.atribuirProjeto(novoProjeto);
		} catch (ProjetoJaAtribuido e) {
			// Result Verification
			assertEquals(listaDeProjetos, meuFuncionario.getProjetos());
			throw e;
		}
	}
	@Test
	public void atribuiProjeto_2() throws Exception {
		// Fixture Setup
		Projeto primeiroProjeto = new Projeto();
		meuFuncionario.atribuirProjeto(primeiroProjeto);
		Projeto segundoProjeto = new Projeto();
		// Exercise SUT
		meuFuncionario.atribuirProjeto(segundoProjeto);
		// Result Verification
		assertEquals(2, meuFuncionario.getProjetos().size());
		assertTrue(meuFuncionario.getProjetos().contains(primeiroProjeto));
		assertTrue(meuFuncionario.getProjetos().contains(segundoProjeto));
	}
	
	@Test
	public void atribuiOcorrencia() throws Exception {
		// Fixture Setup
		Ocorrencia novaOcorrencia = new Ocorrencia("resumo", new Funcionario(), Tipo.TAREFA);
		// Exercise SUT
		meuFuncionario.atribuirOcorrencia(novaOcorrencia);
		// Result Verification
		assertEquals(1, meuFuncionario.getOcorrenciasAbertas().size());
		assertTrue(meuFuncionario.getOcorrenciasAbertas().contains(novaOcorrencia));
		assertEquals(meuFuncionario, novaOcorrencia.getFuncionarioResponsavel());
	}
	@Test(expected=OcorrenciaJaCompletada.class)
	public void atribuiOcorrencia_jaCompletada() throws Exception {
		// Fixture Setup
		Ocorrencia novaOcorrencia = new Ocorrencia("resumo", new Funcionario(), Tipo.TAREFA);
		meuFuncionario.atribuirOcorrencia(novaOcorrencia);
		meuFuncionario.terminaOcorrencia(novaOcorrencia);
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuFuncionario.getOcorrenciasAbertas());
		try {
			// Exercise SUT
			meuFuncionario.atribuirOcorrencia(novaOcorrencia);
		} catch (OcorrenciaJaCompletada e) {
			// Result Verification
			assertEquals(listaDeOcorrencias, meuFuncionario.getOcorrenciasAbertas());
			throw e;
		}
	}
	@Test(expected=FuncionarioJaPossuiTalOcorrencia.class)
	public void atribuiOcorrencia_jaLheFoiAtribuida() throws Exception {
		// Fixture Setup
		Ocorrencia novaOcorrencia = new Ocorrencia("resumo", new Funcionario(), Tipo.TAREFA);
		meuFuncionario.atribuirOcorrencia(novaOcorrencia);
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuFuncionario.getOcorrenciasAbertas());
		try {
			// Exercise SUT
			meuFuncionario.atribuirOcorrencia(novaOcorrencia);
		} catch (FuncionarioJaPossuiTalOcorrencia e) {
			// Result Verification
			assertEquals(listaDeOcorrencias, meuFuncionario.getOcorrenciasAbertas());
			assertEquals(meuFuncionario, novaOcorrencia.getFuncionarioResponsavel());
			throw e;
		}
	}
	@Test
	public void atribuiOcorrencia_10() throws Exception {
		// Fixture Setup
		String resumo = "meu resumo fofinho";
		Tipo tipo = Tipo.TAREFA;
		for(int i=0; i<9; ++i) new Ocorrencia(resumo, meuFuncionario, tipo);
		// Exercise SUT
		new Ocorrencia(resumo, meuFuncionario, tipo);
		// Result Verification
		assertEquals(10, meuFuncionario.getOcorrenciasAbertas().size());
	}
	@Test(expected=ExcedeOLimiteDeOcorrenciasPermitida.class)
	public void atribuiOcorrencia_maisDe10() throws Exception {
		// Fixture Setup
		String resumo = "meu resumo fofinho";
		Tipo tipo = Tipo.TAREFA;
		for(int i=0; i<10; ++i) new Ocorrencia(resumo, meuFuncionario, tipo);
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuFuncionario.getOcorrenciasAbertas());
		try {
			// Exercise SUT
			new Ocorrencia(resumo, meuFuncionario, tipo);
		} catch (ExcedeOLimiteDeOcorrenciasPermitida e) {
			// Result Verification
			assertEquals(listaDeOcorrencias, meuFuncionario.getOcorrenciasAbertas());
			throw e;
		}
	}
	
	@Test
	public void terminaOcorrencia() throws Exception {
		// Fixture Setup
		String resumo = "meu resumo bonitinho";
		Ocorrencia minhaOcorrencia = new Ocorrencia(resumo, meuFuncionario, Tipo.TAREFA);
		// Exercise SUT
		meuFuncionario.terminaOcorrencia(minhaOcorrencia);
		// Result Verification
		assertEquals(Estado.COMPLETADA, minhaOcorrencia.getEstado());
		assertFalse(meuFuncionario.getOcorrenciasAbertas().contains(minhaOcorrencia));
	}
	@Test(expected=OcorrenciaJaCompletada.class)
	public void terminaOcorrencia_jaTerminada() throws Exception {
		// Fixture Setup
		String resumo = "meu resumo bonitinho";
		Ocorrencia minhaOcorrencia = new Ocorrencia(resumo, meuFuncionario, Tipo.TAREFA);
		meuFuncionario.terminaOcorrencia(minhaOcorrencia);
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuFuncionario.getOcorrenciasAbertas());
		try {
			// Exercise SUT
			meuFuncionario.terminaOcorrencia(minhaOcorrencia);
		} catch (OcorrenciaJaCompletada e) {
			// Result Verification
			assertEquals(listaDeOcorrencias, meuFuncionario.getOcorrenciasAbertas());
			throw e;
		}
	}
	@Test(expected=OcorrenciaManipuladaPorUmNaoResponsavel.class)
	public void terminaOcorrencia_terminadaPorUmNaoResponsavel() throws Exception {
		// Fixture Setup
		String resumo = "meu resumo bonitinho";
		Ocorrencia minhaOcorrencia = new Ocorrencia(resumo, meuFuncionario, Tipo.TAREFA);
		Estado estadoOriginal = minhaOcorrencia.getEstado();
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuFuncionario.getOcorrenciasAbertas());
		Funcionario estranho = new Funcionario();
		try {
			// Exercise SUT
			estranho.terminaOcorrencia(minhaOcorrencia);
		} catch (OcorrenciaManipuladaPorUmNaoResponsavel e) {
			// Result Verification
			assertEquals(estadoOriginal, minhaOcorrencia.getEstado());
			assertEquals(listaDeOcorrencias, meuFuncionario.getOcorrenciasAbertas());
			throw e;
		}
	}
}
