import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private Empresa empresa;

	private Funcionario joao;

	private Projeto desenvolvimentoAplicativo;

	private Projeto bancoDeDados;

	@Before()
	public void setup() {
		empresa = new Empresa("Empresa", 12345);
		cadastrarFuncionarioJoao();
		cadastrarProjetoDesenvolvimentoAplicativo();
		joao = empresa.getFuncionarios().get(0);
		empresa.cadastrarProjeto("Banco de Dados", joao);
		desenvolvimentoAplicativo = empresa.getProjetos().get(0);
		bancoDeDados = empresa.getProjetos().get(1);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 1, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 2, joao, Prioridade.MEDIA, TipoOcorrencia.BUG, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 3, joao, Prioridade.BAIXA, TipoOcorrencia.MELHORIA, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 4, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 5, joao, Prioridade.MEDIA, TipoOcorrencia.MELHORIA, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 6, joao, Prioridade.BAIXA, TipoOcorrencia.BUG, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 7, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 8, joao, Prioridade.MEDIA, TipoOcorrencia.BUG, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 9, joao, Prioridade.BAIXA, TipoOcorrencia.MELHORIA, true);
		empresa.cadastrarOcorrencia(bancoDeDados, 10, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
	}

	@Test()
	public void limiteCorretoDeOcorrenciasPorFuncionario() {
		assertEquals(10, desenvolvimentoAplicativo.getOcorrencias().size() + bancoDeDados.getOcorrencias().size());
	}

	@Test()
	public void limiteIncorretoDeOcorrenciasPorFuncionario() {
		empresa.cadastrarOcorrencia(desenvolvimentoAplicativo, 11, joao, Prioridade.MEDIA, TipoOcorrencia.BUG, true);
	}
}
