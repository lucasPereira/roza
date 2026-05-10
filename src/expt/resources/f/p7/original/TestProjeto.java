package tdd;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tdd.Funcionario;
import tdd.Ocorrencia;
import tdd.Projeto;
import tdd.Ocorrencia.Tipo;

public class TestProjeto {

	private Projeto meuProjeto;
	
	@Before
	public void fixtureSetup() throws Exception {
		meuProjeto = new Projeto();
	}
	
	public void cadastrarOcorrencia(Tipo tipo) throws Exception {
		// Fixture Setup
		String resumo = "Minha tarefa bonitinha";
		Funcionario responsavel = new Funcionario();
		Ocorrencia novaTarefa = new Ocorrencia(resumo, responsavel, tipo);
		// Exercise SUT
		meuProjeto.cadastrarOcorrencia(novaTarefa);
		// Result Verification
		assertEquals(1, meuProjeto.getOcorrencias().size());
		assertTrue(meuProjeto.getOcorrencias().contains(novaTarefa));
	}
	@Test	public void cadastrarOcorrencia_tarefa() throws Exception	{cadastrarOcorrencia(Tipo.TAREFA);}
	@Test	public void cadastrarOcorrencia_bug() throws Exception		{cadastrarOcorrencia(Tipo.BUG);}
	@Test	public void cadastrarOcorrencia_melhoria() throws Exception	{cadastrarOcorrencia(Tipo.MELHORIA);}
	@Test(expected=OcorrenciaJaCadastrada.class)
	public void cadastrarOcorrencia_jaCadastrada() throws Exception {
		// Fixture Setup
		String resumo = "Minha tarefa bonitinha";
		Funcionario responsavel = new Funcionario();
		Ocorrencia novaTarefa = new Ocorrencia(resumo, responsavel, Tipo.TAREFA);
		meuProjeto.cadastrarOcorrencia(novaTarefa);
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuProjeto.getOcorrencias());
		try {
			// Exercise SUT
			meuProjeto.cadastrarOcorrencia(novaTarefa);
		}catch (OcorrenciaJaCadastrada e) {
			// Result Verification
			assertEquals(listaDeOcorrencias, meuProjeto.getOcorrencias());
			throw e;
		}
	}
}
