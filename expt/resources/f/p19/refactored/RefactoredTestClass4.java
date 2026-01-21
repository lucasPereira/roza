import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Controlador controlador;

	private Funcionario funcionario;

	private Projeto projeto;

	@Before()
	public void setup() {
		controlador = new Controlador();
		funcionario = controlador.cadastrarFuncionario("Joao");
		projeto = controlador.cadastrarProjeto("Propaganda na home");
	}

	@Test()
	public void testCadastrarDezOcorrenciasParaFuncionario() {
		int totalOcorrencias = 10;
		gerarNOcorrencias(totalOcorrencias);
		assertEquals(10, funcionario.getQuantidadeDeOcorrencias());
		assertEquals(10, controlador.getOcorrencias().size());
		assertEquals(10, projeto.getOcorrencias().size());
	}

	@Test()
	public void testCadastrarFuncionario() {
		Funcionario funcionarioTeste = controlador.cadastrarFuncionario("Maria");
		assertEquals(2, funcionarioTeste.getId());
		assertEquals(2, controlador.getFuncionarios().size());
		assertTrue(controlador.existeFuncionario(funcionarioTeste.getId()));
	}

	@Test()
	public void testCadastrarOcorrencia() {
		Ocorrencia ocorrenciaTeste = controlador.cadastrarOcorrencia("Mudar background da home", Tipo.MELHORIA, Prioridade.BAIXA, funcionario.getId(), projeto.getId());
		assertEquals(1, ocorrenciaTeste.getId());
		assertEquals(1, controlador.getOcorrencias().size());
		assertTrue(controlador.existeOcorrencia(ocorrenciaTeste.getId()));
		assertEquals(1, funcionario.getQuantidadeDeOcorrencias());
		assertTrue(projeto.getOcorrencias().contains(ocorrenciaTeste));
	}

	@Test()
	public void testCadastrarOcorrenciaFuncionarioNaoExistente() {
		int idFuncionarioInvalido = 1000;
		controlador.cadastrarOcorrencia(resumoOcorrencia, tipoOcorrencia, prioridadeOcorrencia, projeto.getId(), idFuncionarioInvalido);
	}

	@Test()
	public void testCadastrarOcorrenciaProjetoNaoExistente() {
		int idProjetoInvalido = 1000;
		controlador.cadastrarOcorrencia(resumoOcorrencia, tipoOcorrencia, prioridadeOcorrencia, idProjetoInvalido, funcionario.getId());
	}

	@Test()
	public void testCadastrarOnzeOcorrenciasParaFuncionario() {
		int totalOcorrencias = 11;
		gerarNOcorrencias(totalOcorrencias);
	}

	@Test()
	public void testCadastrarProjeto() {
		Projeto projetoTeste = controlador.cadastrarProjeto("Sugestão de amigos");
		assertEquals(2, projetoTeste.getId());
		assertEquals(2, controlador.getProjetos().size());
		assertTrue(controlador.existeProjeto(projetoTeste.getId()));
	}

	@Test()
	public void testCriarControladorEmpresa() {
		Controlador controladorTeste = new Controlador();
		assertEquals(0, controladorTeste.getFuncionarios().size());
		assertEquals(0, controladorTeste.getOcorrencias().size());
		assertEquals(0, controladorTeste.getProjetos().size());
	}

	@Test()
	public void testFinalizarOcorrencia() {
		Ocorrencia ocorrencia = controlador.cadastrarOcorrencia(resumoOcorrencia, tipoOcorrencia, prioridadeOcorrencia, projeto.getId(), funcionario.getId());
		controlador.finalizarOcorrencia(1);
		Set<Ocorrencia> ocorrenciasDoProjeto = projeto.getOcorrencias();
		Ocorrencia ocorrenciaDoProjeto = ocorrenciasDoProjeto.iterator().next();
		assertEquals(0, funcionario.getQuantidadeDeOcorrencias());
		assertEquals(Estado.FINALIZADA, ocorrencia.getEstado());
		assertEquals(Estado.FINALIZADA, ocorrenciaDoProjeto.getEstado());
	}

	@Test()
	public void testFinalizarOcorrenciaNaoExistente() {
		int idOcorrenciaInvalida = 1000;
		controlador.finalizarOcorrencia(idOcorrenciaInvalida);
	}
}
