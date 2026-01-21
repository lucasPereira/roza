import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass1 {

	private Empresa umaEmpresa;

	private Funcionario ana;

	private Funcionario beto;

	private int codigoBug;

	private String nomeProjeto;

	private Ocorrencia bug;

	private Projeto windows;

	@Before()
	public void setup() {
		umaEmpresa = new Empresa();
		ana = new Funcionario("ana");
		beto = new Funcionario("beto");
		codigoBug = 1;
		nomeProjeto = "Windows 100";
		bug = new Ocorrencia(codigoBug, Tipos.BUG, Prioridades.ALTA, "Tela Azul");
		windows = new Projeto(nomeProjeto);
	}

	@Test()
	public void atualizarPrioridadeAltaOcorrencia() {
		configurarCadastros();
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		umaEmpresa.atulizarOcorrencia(nomeProjeto, codigoBug, Prioridades.BAIXA, null);
		Ocorrencia bugCadastrado = umaEmpresa.obterOcorrenciaDeProjeto(nomeProjeto, codigoBug);
		Funcionario responsavel = bugCadastrado.obterResponsavel();
		assertEquals(Prioridades.BAIXA, bugCadastrado.obterPrioridade());
	}

	@Test()
	public void cadastrar10ocorrenciasWindowsAna() {
		configurarCadastros();
		int num_ocorrencias = 10;
		for (int i = 0; i < num_ocorrencias; i++) {
			umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, new Ocorrencia(i + 1, Tipos.TAREFA, Prioridades.MEDIA, "Tarefa " + i), ana.obterNome());
		}
		assertEquals(num_ocorrencias, umaEmpresa.obterFuncionario(ana.obterNome()).obterOcorrencias().size());
		assertEquals(num_ocorrencias, umaEmpresa.obterProjeto(nomeProjeto).obterOcorrencias().size());
	}

	@Test()
	public void cadastrar11ocorrenciasAna() {
		configurarCadastros();
		int num_ocorrencias = 11;
		for (int i = 0; i < num_ocorrencias; i++) {
			umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, new Ocorrencia(i + 1, Tipos.TAREFA, Prioridades.MEDIA, "Tarefa " + i), ana.obterNome());
		}
	}

	@Test()
	public void cadastrarOcorrenciaBug() {
		configurarCadastros();
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		Ocorrencia ocorrenciaCadastrada = umaEmpresa.obterOcorrenciaDeProjeto(nomeProjeto, codigoBug);
		assertEquals(bug, ocorrenciaCadastrada);
	}

	@Test()
	public void completarOcorrenciaBug() {
		configurarCadastros();
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		umaEmpresa.atulizarOcorrencia(nomeProjeto, codigoBug, null, Estados.COMPLETADA);
		Ocorrencia bugCadastrado = umaEmpresa.obterOcorrenciaDeProjeto(nomeProjeto, codigoBug);
		Funcionario responsavel = bugCadastrado.obterResponsavel();
		assertEquals(Estados.COMPLETADA, bugCadastrado.obterEstado());
		assertEquals(0, responsavel.obterOcorrencias().size());
	}

	@Test()
	public void doisProjetosDiferentesDuasOcorrenciasAna() {
		configurarCadastros();
		Projeto windows3000 = new Projeto("windows3000");
		Ocorrencia tarefa = new Ocorrencia(2, Tipos.TAREFA, Prioridades.ALTA, "Corrigir Paint");
		umaEmpresa.cadastrarProjeto(windows3000);
		umaEmpresa.cadastrarFuncionarioProjeto(ana, windows3000.obterNome());
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		umaEmpresa.cadastrarOcorrenciaDeProjeto(windows3000.obterNome(), tarefa, ana.obterNome());
		Funcionario recuperarAna = umaEmpresa.obterFuncionario(ana.obterNome());
		assertEquals(2, recuperarAna.obterOcorrencias().size());
		assertEquals(2, recuperarAna.obterProjetos().size());
	}

	@Test()
	public void funcionarioInexistenteOlegario() {
		umaEmpresa.obterFuncionario("Olegario");
	}

	@Test()
	public void listarDoisFuncionarios() {
		umaEmpresa.cadastrarProjeto(windows);
		umaEmpresa.cadastrarFuncionario(ana);
		umaEmpresa.cadastrarFuncionario(beto);
		assertEquals(2, umaEmpresa.obterFuncionarios().size());
	}

	@Test()
	public void listarDoisProjetos() {
		Projeto leilao = new Projeto("Leilao");
		Projeto banco = new Projeto("Sistema Bancario");
		umaEmpresa.cadastrarProjeto(leilao);
		umaEmpresa.cadastrarProjeto(banco);
		assertEquals(2, umaEmpresa.obterProjetos().size());
	}

	@Test()
	public void novoResponsavelBugBeto() {
		configurarCadastros();
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		Ocorrencia oBug = umaEmpresa.obterOcorrenciaDeProjeto(nomeProjeto, bug.obterCodigo());
		umaEmpresa.atulizarResponsavelOcorrencia(nomeProjeto, bug.obterCodigo(), beto.obterNome());
		assertEquals(1, umaEmpresa.obterFuncionario(beto.obterNome()).obterOcorrencias().size());
		assertEquals(0, umaEmpresa.obterFuncionario(ana.obterNome()).obterOcorrencias().size());
	}

	@Test()
	public void ocorrenciaCodigoExistente() {
		configurarCadastros();
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		Ocorrencia melhoria = new Ocorrencia(1, Tipos.MELHORIA, Prioridades.BAIXA, "Otimizacao de algoritmo");
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, melhoria, beto.obterNome());
		;
	}

	@Test()
	public void ocorrenciaInexistenteNegativa() {
		int codigoNegativo = -1;
		configurarCadastros();
		umaEmpresa.obterOcorrenciaDeProjeto(nomeProjeto, codigoNegativo);
	}

	@Test()
	public void ocorrenciaResumoTelaAzul() {
		configurarCadastros();
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		Ocorrencia bugCadastrado = umaEmpresa.obterOcorrenciaDeProjeto(nomeProjeto, codigoBug);
		assertEquals("Tela Azul", bugCadastrado.obterResumo());
	}

	@Test()
	public void projetoInexistenteUbuntdows() {
		umaEmpresa.obterProjeto("Ubuntdows");
	}
}
