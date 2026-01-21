import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	private Empresa empresa;

	private Projeto projeto;

	private Funcionario bob;

	private GeradorDeOcorrencias gerador;

	@Before()
	public void setup() {
		empresa = new Empresa();
		projeto = new Projeto();
		bob = new Funcionario();
		gerador = new GeradorDeOcorrencias();
		empresa.addFuncionario(bob);
		empresa.addProjeto(projeto);
	}

	@Test()
	public void alterarPrioridade() {
		Ocorrencia somaBugada = exemploOcorrencia();
		somaBugada.setPrioridade(Prioridade.Media);
		assertEquals(Prioridade.Media, somaBugada.getPrioridade());
	}

	@Test()
	public void alterarPrioridadeAposConclusao() {
		Ocorrencia somaBugada = ocorrenciaFechada();
		Funcionario charlie = new Funcionario();
		empresa.addFuncionario(charlie);
		somaBugada.setPrioridade(Prioridade.Baixa);
	}

	@Test()
	public void alterarResponsavel() {
		Funcionario charlie = new Funcionario();
		Ocorrencia somaBugada = exemploOcorrencia();
		somaBugada.setResponsavel(charlie);
		assertEquals(charlie, somaBugada.getResponsavel());
	}

	@Test()
	public void alterarResponsavelAposConclusao() {
		Ocorrencia somaBugada = ocorrenciaFechada();
		Funcionario charlie = new Funcionario();
		empresa.addFuncionario(charlie);
		somaBugada.setResponsavel(charlie);
	}

	@Test()
	public void alterarResponsavelParaFuncionarioSobrecarregado() {
		Funcionario charlie = new Funcionario();
		Ocorrencia somaBugada = exemploOcorrencia();
		empresa.addFuncionario(charlie);
		List<Ocorrencia> ocorrencias = gerador.gerarOcorrencias(charlie, 10);
		projeto.addOcorrencias(ocorrencias);
		projeto.addOcorrencia(somaBugada);
		somaBugada.setResponsavel(charlie);
	}

	@Test()
	public void igualdadeDeChave() {
		Ocorrencia somaBugada = exemploOcorrencia();
		assertEquals(somaBugada.getChave(), somaBugada.getChave());
	}

	@Test()
	public void unicidadeDeChave() {
		Ocorrencia somaBugada = exemploOcorrencia();
		Ocorrencia multiplicacaoLenta = new Ocorrencia(bob, TipoOcorrencia.Melhoria, Prioridade.Media, "Multiplicação lenta");
		assertNotEquals(somaBugada.getChave(), multiplicacaoLenta.getChave());
	}

	@Test()
	public void verificarEstadoAbertoNaCriacao() {
		Ocorrencia somaBugada = exemploOcorrencia();
		assertTrue(somaBugada.isAberta());
	}

	@Test()
	public void verificarEstadoFechadoAposConclusao() {
		Ocorrencia somaBugada = exemploOcorrencia();
		projeto.addOcorrencia(somaBugada);
		bob.terminarOcorrencia(somaBugada);
		assertFalse(somaBugada.isAberta());
	}

	@Test()
	public void verificarPrioridade() {
		Ocorrencia somaBugada = exemploOcorrencia();
		assertEquals(Prioridade.Alta, somaBugada.getPrioridade());
	}

	@Test()
	public void verificarResponsavel() {
		Ocorrencia somaBugada = exemploOcorrencia();
		assertEquals(bob, somaBugada.getResponsavel());
	}

	@Test()
	public void verificarResumo() {
		Ocorrencia somaBugada = exemploOcorrencia();
		assertEquals("Soma com problemas", somaBugada.getResumo());
	}

	@Test()
	public void verificarTipo() {
		Ocorrencia somaBugada = exemploOcorrencia();
		assertEquals(TipoOcorrencia.Bug, somaBugada.getTipo());
	}
}
