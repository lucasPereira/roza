import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Empresa empresa;

	private Funcionario joao;

	@Before()
	public void setup() {
		empresa = new Empresa("Empresa", 12345);
		cadastrarFuncionarioJoao();
		cadastrarProjetoDesenvolvimentoAplicativo();
		joao = empresa.getFuncionarios().get(0);
	}

	@Test()
	public void cadastrarFuncionario() {
		assertEquals("João", joao.getNome());
		assertEquals(1234, joao.getCPF());
		assertEquals(1, empresa.getFuncionarios().size());
	}

	@Test()
	public void cadastrarOcorrencia() {
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		Ocorrencia ocorrencia = desenvolvimentoAplicativo.getOcorrencias().get(0);
		assertEquals(001, ocorrencia.getChave());
		assertEquals(joao, ocorrencia.getResponsavel());
		assertEquals(Prioridade.ALTA, ocorrencia.getPrioridade());
		assertEquals(TipoOcorrencia.TAREFA, ocorrencia.getTipo());
		assertEquals(true, ocorrencia.getEstadoOcorrencia());
		assertEquals(1, desenvolvimentoAplicativo.getOcorrencias().size());
	}

	@Test()
	public void cadastrarOcorrenciaComChaveRepetida() {
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 1, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 2, joao, Prioridade.MEDIA, TipoOcorrencia.BUG, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 1, joao, Prioridade.BAIXA, TipoOcorrencia.MELHORIA, true);
	}

	@Test()
	public void cadastrarOcorrenciaSemProjetoNaEmpresa() {
		Projeto modelagemConceitual = new Projeto("Modelagem Conceitual", joao);
		empresa.cadastrarOcorrencia(modelagemConceitual, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
	}

	@Test()
	public void cadastrarProjeto() {
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		assertEquals("Desenvolvimento Aplicativo", desenvolvimentoAplicativo.getNome());
		assertEquals(joao, desenvolvimentoAplicativo.getLider());
		assertEquals(1, empresa.getProjetos().size());
	}

	@Test()
	public void fecharOcorrencia() {
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.fecharOcorrencia(desenvolvimentoAplicativo.getOcorrencias().get(0), joao);
		assertEquals(false, desenvolvimentoAplicativo.getOcorrencias().get(0).getEstadoOcorrencia());
	}

	@Test()
	public void modificarPrioridadeDeOcorrenciaEmProjetoCadastrado() {
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.modificarPrioridadeOcorrencia(desenvolvimentoAplicativo, desenvolvimentoAplicativo.getOcorrencias().get(0), Prioridade.BAIXA);
		assertEquals(Prioridade.BAIXA, desenvolvimentoAplicativo.getOcorrencias().get(0).getPrioridade());
	}

	@Test()
	public void modificarPrioridadeDeOcorrenciaEmProjetoNaoCadastrado() {
		Projeto layoutSite = new Projeto("Layout do Site", joao);
		empresa.cadastrarOcorrencia(layoutSite, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.modificarPrioridadeOcorrencia(layoutSite, layoutSite.getOcorrencias().get(0), Prioridade.BAIXA);
	}

	@Test()
	public void modificarPrioridadeDeOcorrenciaFechada() {
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.fecharOcorrencia(desenvolvimentoAplicativo.getOcorrencias().get(0), joao);
		empresa.modificarPrioridadeOcorrencia(desenvolvimentoAplicativo, desenvolvimentoAplicativo.getOcorrencias().get(0), Prioridade.BAIXA);
	}

	@Test()
	public void modificarPrioridadeDeOcorrenciaNaoCadastrada() {
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		Ocorrencia ocorrencia = new Ocorrencia(005, joao, Prioridade.ALTA, TipoOcorrencia.BUG, true);
		empresa.modificarPrioridadeOcorrencia(desenvolvimentoAplicativo, ocorrencia, Prioridade.BAIXA);
	}

	@Test()
	public void modificarResponsavelOcorrencia() {
		empresa.cadastrarFuncionario("Pedro", 4567);
		Funcionario pedro = empresa.getFuncionarios().get(1);
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.modificarResponsavelOcorrencia(desenvolvimentoAplicativo.getOcorrencias().get(0), pedro);
		assertEquals(pedro, desenvolvimentoAplicativo.getOcorrencias().get(0).getResponsavel());
	}

	@Test()
	public void modificarResponsavelOcorrenciaFechada() {
		Projeto desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		empresa.fecharOcorrencia(desenvolvimentoAplicativo.getOcorrencias().get(0), joao);
		empresa.cadastrarFuncionario("Funcionario2", 4567);
		Funcionario pedro = empresa.getFuncionarios().get(1);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 001, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.modificarResponsavelOcorrencia(desenvolvimentoAplicativo.getOcorrencias().get(0), pedro);
	}
}
