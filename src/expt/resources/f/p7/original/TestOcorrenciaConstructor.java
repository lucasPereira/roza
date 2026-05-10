package tdd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tdd.Funcionario;
import tdd.Ocorrencia;
import tdd.Ocorrencia.Estado;
import tdd.Ocorrencia.Prioridade;
import tdd.Ocorrencia.Tipo;

public class TestOcorrenciaConstructor {
	
	private static String meuResumo;
	@BeforeClass
	public static void staticFixtureSetup() throws Exception {
		meuResumo = "Do que se trata a ocorrência";
	}

	private Funcionario meuResponsavel;
	@Before
	public void fixtureSetup() throws Exception {
		meuResponsavel = new Funcionario();
	}
	
	public void newOcorrencia(Tipo tipo) throws Exception {
		// Exercise SUT
		Ocorrencia minhaOcorrencia = new Ocorrencia(meuResumo, meuResponsavel, tipo);
		// Result Verification
		assertEquals(meuResumo, minhaOcorrencia.getResumo());
		assertEquals(meuResponsavel, minhaOcorrencia.getFuncionarioResponsavel());
		assertEquals(tipo, minhaOcorrencia.getTipo());
		assertEquals(Estado.ABERTA, minhaOcorrencia.getEstado());
		assertEquals(Prioridade.MEDIA, minhaOcorrencia.getPrioridade());
	}
	@Test	public void newOcorrencia_TAREFA() throws Exception		{newOcorrencia(Tipo.TAREFA);}
	@Test	public void newOcorrencia_BUG() throws Exception		{newOcorrencia(Tipo.BUG);}
	@Test	public void newOcorrencia_MELHORIA() throws Exception	{newOcorrencia(Tipo.MELHORIA);}

	@Test
	public void newOcorrencia_2() throws Exception {
		// Fixture Setup
		Ocorrencia primeiraOcorrencia = new Ocorrencia("", new Funcionario(), Tipo.TAREFA);
		Tipo meuTipo = Tipo.TAREFA;
		// Exercise SUT
		Ocorrencia segundaOcorrencia = new Ocorrencia(meuResumo, meuResponsavel, meuTipo);
		// Result Verification
		assertEquals(primeiraOcorrencia.getChave() +1, segundaOcorrencia.getChave());
		assertEquals(meuResumo, segundaOcorrencia.getResumo());
		assertEquals(meuResponsavel, segundaOcorrencia.getFuncionarioResponsavel());
		assertEquals(meuTipo, segundaOcorrencia.getTipo());
		assertEquals(Estado.ABERTA, segundaOcorrencia.getEstado());
		assertEquals(Prioridade.MEDIA, segundaOcorrencia.getPrioridade());
	}
	
	@Test
	public void enumValues_coverage() throws Exception {
		for(Tipo val		: Tipo.values())		Tipo.valueOf(val.toString());
		for(Estado val		: Estado.values())		Estado.valueOf(val.toString());
		for(Prioridade val	: Prioridade.values())	Prioridade.valueOf(val.toString());
	}
}
