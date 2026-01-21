import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("Empresa", 12345);
		criaFuncionarioJoao();
		criaProjetoDesenvolvimentoSite();
	}

	@Test()
	public void cadastrarResumoOcorrencia() {
		Ocorrencia ocorrencia = new Ocorrencia(004, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		ocorrencia.setResumo("Organizar equipes do projeto.");
		assertEquals("Organizar equipes do projeto.", ocorrencia.getResumo());
	}

	@Test()
	public void criarEmpresa() {
		assertEquals("Empresa", empresa.getNome());
		assertEquals(12345, empresa.getCNPJ());
	}

	@Test()
	public void criarFuncionarioJoao() {
		assertEquals("João", joao.getNome());
		assertEquals(1234, joao.getCPF());
	}

	@Test()
	public void criarOcorrencia() {
		Ocorrencia ocorrencia = new Ocorrencia(001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		assertEquals(001, ocorrencia.getChave());
		assertEquals(joao, ocorrencia.getResponsavel());
		assertEquals(Prioridade.ALTA, ocorrencia.getPrioridade());
		assertEquals(TipoOcorrencia.TAREFA, ocorrencia.getTipo());
		assertEquals(true, ocorrencia.getEstadoOcorrencia());
	}

	@Test()
	public void criarProjetoDesenvolvimentoSite() {
		assertEquals("Desenvolvimento de Site", desenvolvimentoSite.getNome());
		assertEquals(joao, desenvolvimentoSite.getLider());
	}

	@Test()
	public void fecharOcorrencia() {
		Ocorrencia ocorrencia = new Ocorrencia(002, joao, Prioridade.ALTA, TipoOcorrencia.BUG, true);
		ocorrencia.fecharOcorrencia(joao);
		assertEquals(false, ocorrencia.getEstadoOcorrencia());
	}

	@Test()
	public void fecharOcorrenciaComFuncionarioNaoAutorizado() {
		Ocorrencia ocorrencia = new Ocorrencia(002, joao, Prioridade.ALTA, TipoOcorrencia.BUG, true);
		Funcionario pedro = new Funcionario("Pedro", 5678);
		ocorrencia.fecharOcorrencia(pedro);
	}

	@Test()
	public void inserirFuncionarioNaEmpresa() {
		empresa.inserirFuncionario(joao);
		assertEquals(joao, empresa.getFuncionarios().get(0));
		assertEquals(1, empresa.getFuncionarios().size());
	}

	@Test()
	public void inserirNovoProjetoNaEmpresa() {
		empresa.inserirProjeto(desenvolvimentoSite);
		assertEquals(desenvolvimentoSite, empresa.getProjetos().get(0));
		assertEquals(1, empresa.getProjetos().size());
	}

	@Test()
	public void inserirOcorrenciaNoProjeto() {
		Ocorrencia ocorrencia = new Ocorrencia(003, joao, Prioridade.ALTA, TipoOcorrencia.MELHORIA, true);
		desenvolvimentoSite.inserirOcorrencia(ocorrencia);
		assertEquals(ocorrencia, desenvolvimentoSite.getOcorrencias().get(0));
	}

	@Test()
	public void modificarPrioridadeOcorrenciaAberta() {
		Ocorrencia ocorrencia = new Ocorrencia(005, joao, Prioridade.ALTA, TipoOcorrencia.BUG, true);
		ocorrencia.setPrioridade(Prioridade.BAIXA);
		assertEquals(Prioridade.BAIXA, ocorrencia.getPrioridade());
	}

	@Test()
	public void modificarPrioridadeOcorrenciaFechada() {
		Ocorrencia ocorrencia = new Ocorrencia(005, joao, Prioridade.ALTA, TipoOcorrencia.BUG, true);
		ocorrencia.fecharOcorrencia(joao);
		ocorrencia.setPrioridade(Prioridade.BAIXA);
	}

	@Test()
	public void modificarResponsavelOcorrenciaAberta() {
		Ocorrencia ocorrencia = new Ocorrencia(006, joao, Prioridade.ALTA, TipoOcorrencia.MELHORIA, true);
		Funcionario pedro = new Funcionario("Pedro", 5678);
		ocorrencia.setResponsavel(pedro);
		assertEquals(pedro, ocorrencia.getResponsavel());
	}

	@Test()
	public void modificarResponsavelOcorrenciaFechada() {
		Ocorrencia ocorrencia = new Ocorrencia(007, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		Funcionario pedro = new Funcionario("Pedro", 5678);
		ocorrencia.fecharOcorrencia(joao);
		ocorrencia.setResponsavel(pedro);
	}
}
