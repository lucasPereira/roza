import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private String nomeDoProjeto;

	private Projeto projeto;

	private String nomeDoResponsavel;

	private Funcionario responsavel;

	private String nomeDaTarefa;

	private String nomeDoBug;

	private String nomeDaMelhoria;

	@Before()
	public void setup() {
		nomeDoProjeto = "Sistema Bancario";
		projeto = new Projeto(nomeDoProjeto);
		nomeDoResponsavel = "Joao";
		responsavel = new Funcionario(nomeDoResponsavel);
		nomeDaTarefa = "Implementar nova funcionalidade";
		nomeDoBug = "Corrigir problema";
		nomeDaMelhoria = "Melhorar performance";
	}

	@Test()
	public void adicionarOcorrencia() {
		Ocorrencia ocorrencia = projeto.adicionarOcorrencia(1, nomeDaTarefa, TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.ALTA, responsavel);
		assertEquals(responsavel, ocorrencia.obterResponsavel());
		assertEquals(Integer.valueOf(1), ocorrencia.obterChave());
		assertEquals(nomeDaTarefa, ocorrencia.obterResumo());
		assertEquals(TipoDeOcorrencia.TAREFA, ocorrencia.obterTipo());
		assertEquals(PrioridadeDeOcorrencia.ALTA, ocorrencia.obterPrioridade());
		assertEquals(ocorrencia, responsavel.obterOcorrenciaResponsavel(1));
	}

	@Test()
	public void alterarResponsavelPorOcorrenciaAberta() {
		Ocorrencia ocorrencia = projeto.adicionarOcorrencia(2, nomeDoBug, TipoDeOcorrencia.BUG, PrioridadeDeOcorrencia.ALTA, responsavel);
		Funcionario novoResponsavel = new Funcionario("Maria");
		projeto.alterarResponsavel(ocorrencia.obterChave(), novoResponsavel);
		assertEquals(novoResponsavel, ocorrencia.obterResponsavel());
		assertEquals(null, responsavel.obterOcorrenciaResponsavel(ocorrencia.obterChave()));
	}

	@Test()
	public void alterarResponsavelPorOcorrenciaCompletada() {
		Ocorrencia ocorrencia = projeto.adicionarOcorrencia(2, nomeDoBug, TipoDeOcorrencia.BUG, PrioridadeDeOcorrencia.ALTA, responsavel);
		ocorrencia.terminar();
		Funcionario novoResponsavel = new Funcionario("Maria");
		projeto.alterarResponsavel(ocorrencia.obterChave(), novoResponsavel);
		assertEquals(novoResponsavel, ocorrencia.obterResponsavel());
		assertEquals(null, responsavel.obterOcorrenciaResponsavel(ocorrencia.obterChave()));
	}

	@Test()
	public void obterNome() {
		String nome = projeto.obterNome();
		assertEquals(nomeDoProjeto, nome);
	}

	@Test()
	public void obterOcorrencia() {
		Ocorrencia ocorrenciaCriada = projeto.adicionarOcorrencia(1, nomeDaTarefa, TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.ALTA, responsavel);
		Ocorrencia ocorrenciaObtida = projeto.obterOcorrencia(1);
		assertEquals(ocorrenciaCriada, ocorrenciaObtida);
	}

	@Test()
	public void obterSegundaOcorrenciaAdicionadaDentreTres() {
		projeto.adicionarOcorrencia(1, nomeDaTarefa, TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA, responsavel);
		Ocorrencia ocorrenciaCriada = projeto.adicionarOcorrencia(2, nomeDoBug, TipoDeOcorrencia.BUG, PrioridadeDeOcorrencia.ALTA, responsavel);
		projeto.adicionarOcorrencia(3, nomeDaMelhoria, TipoDeOcorrencia.MELHORIA, PrioridadeDeOcorrencia.BAIXA, responsavel);
		Ocorrencia ocorrenciaObtida = projeto.obterOcorrencia(2);
		assertEquals(ocorrenciaCriada, ocorrenciaObtida);
	}
}
