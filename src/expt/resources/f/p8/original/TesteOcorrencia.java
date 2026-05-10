package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import src.Empresa;
import src.Funcionario;
import src.Ocorrencia;
import src.Prioridade;
import src.Projeto;
import src.TipoOcorrencia;

public class TesteOcorrencia {
	private Empresa empresa;
	private Funcionario bob;
	private Projeto projeto;
	private GeradorDeOcorrencias gerador;

	@Before
	public void setup() {
		empresa = new Empresa();
		projeto = new Projeto();
		bob = new Funcionario();
		gerador = new GeradorDeOcorrencias();

		empresa.addFuncionario(bob);
		empresa.addProjeto(projeto);
	}

	private Ocorrencia exemploOcorrencia() {
		return new Ocorrencia(bob, TipoOcorrencia.Bug, Prioridade.Alta, "Soma com problemas");
	}

	private Ocorrencia ocorrenciaFechada() throws Exception {
		Ocorrencia somaBugada = exemploOcorrencia();
	
		projeto.addOcorrencia(somaBugada);
		bob.terminarOcorrencia(somaBugada);

		return somaBugada;
	}
	
	@Test
	public void verificarPrioridade() {
		Ocorrencia somaBugada = exemploOcorrencia();
		assertEquals(Prioridade.Alta, somaBugada.getPrioridade());
	}

	@Test
	public void alterarPrioridade() throws Exception {
		Ocorrencia somaBugada = exemploOcorrencia();
		somaBugada.setPrioridade(Prioridade.Media);
		assertEquals(Prioridade.Media, somaBugada.getPrioridade());
	}

	@Test
	public void unicidadeDeChave() {
		Ocorrencia somaBugada = exemploOcorrencia();
		Ocorrencia multiplicacaoLenta = new Ocorrencia(bob, TipoOcorrencia.Melhoria,
				Prioridade.Media, "Multiplicação lenta");

		assertNotEquals(somaBugada.getChave(), multiplicacaoLenta.getChave());
	}

	@Test
	public void igualdadeDeChave() {
		Ocorrencia somaBugada = exemploOcorrencia();
		assertEquals(somaBugada.getChave(), somaBugada.getChave());
	}

	@Test
	public void verificarResumo() {
		Ocorrencia somaBugada = exemploOcorrencia();
		assertEquals("Soma com problemas", somaBugada.getResumo());
	}

	@Test
	public void verificarTipo() {
		Ocorrencia somaBugada = exemploOcorrencia();
		assertEquals(TipoOcorrencia.Bug, somaBugada.getTipo());
	}

	@Test
	public void verificarEstadoAbertoNaCriacao() {
		Ocorrencia somaBugada = exemploOcorrencia();
		assertTrue(somaBugada.isAberta());
	}

	@Test
	public void verificarEstadoFechadoAposConclusao() throws Exception {
		Ocorrencia somaBugada = exemploOcorrencia();

		projeto.addOcorrencia(somaBugada);
		bob.terminarOcorrencia(somaBugada);
		
		assertFalse(somaBugada.isAberta());
	}

	@Test
	public void verificarResponsavel() {
		Ocorrencia somaBugada = exemploOcorrencia();
		assertEquals(bob, somaBugada.getResponsavel());
	}
	
	@Test
	public void alterarResponsavel() throws Exception {
		Funcionario charlie = new Funcionario();

		Ocorrencia somaBugada = exemploOcorrencia();
		somaBugada.setResponsavel(charlie);

		assertEquals(charlie, somaBugada.getResponsavel());
	}

	@Test(expected = Exception.class)
	public void alterarResponsavelParaFuncionarioSobrecarregado() throws Exception {
		Funcionario charlie = new Funcionario();
		Ocorrencia somaBugada = exemploOcorrencia();

		empresa.addFuncionario(charlie);
		
		List<Ocorrencia> ocorrencias = gerador.gerarOcorrencias(charlie, 10);

		projeto.addOcorrencias(ocorrencias);
		projeto.addOcorrencia(somaBugada);
		somaBugada.setResponsavel(charlie);
	}

	@Test(expected = Exception.class)
	public void alterarResponsavelAposConclusao() throws Exception {
		Ocorrencia somaBugada = ocorrenciaFechada();

		Funcionario charlie = new Funcionario();
		empresa.addFuncionario(charlie);

		somaBugada.setResponsavel(charlie);
	}

	@Test(expected = Exception.class)
	public void alterarPrioridadeAposConclusao() throws Exception {
		Ocorrencia somaBugada = ocorrenciaFechada();

		Funcionario charlie = new Funcionario();
		empresa.addFuncionario(charlie);

		somaBugada.setPrioridade(Prioridade.Baixa);
	}
}
