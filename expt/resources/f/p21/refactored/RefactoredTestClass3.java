import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Integer codigo_funcionario;

	private String nome_funcionario;

	private Funcionario funcionario;

	private Integer codigo_projeto;

	private String nome_projeto;

	private Projeto digiclad;

	@Before()
	public void setup() {
		codigo_funcionario = 1;
		nome_funcionario = "Guilherme";
		funcionario = new Funcionario(codigo_funcionario, nome_funcionario);
		codigo_projeto = 1;
		nome_projeto = "DIGICLAD";
		digiclad = new Projeto(codigo_projeto, nome_projeto, funcionario);
	}

	@Test()
	public void testAtualizaEstadoOcorrencia() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		digiclad.criaOcorrencia(nova_ocorrencia);
		aux_estado = Estado.FECHADA;
		Ocorrencia teste_ocorrencia = digiclad.atualizaEstadoOcorrencia(nova_ocorrencia, aux_estado);
		assertEquals(aux_estado, teste_ocorrencia.getEstadoOcorrencia());
	}

	@Test()
	public void testAtualizaEstadoOcorrenciaInvalido() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		Ocorrencia teste_correncia = new Ocorrencia(codigo_ocorrencia = 2, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		aux_estado = Estado.FECHADA;
		assertEquals(null, digiclad.atualizaEstadoOcorrencia(teste_correncia, aux_estado));
	}

	@Test()
	public void testAtualizaFuncionarioOcorrencia() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		digiclad.criaOcorrencia(nova_ocorrencia);
		Integer codigo_funcionario = 2;
		String nome_funcionario = "Maria";
		Funcionario funcionario_maria = new Funcionario(codigo_funcionario, nome_funcionario);
		Ocorrencia teste_ocorrencia = digiclad.atualizaFuncionarioOcorrencia(nova_ocorrencia, funcionario_maria);
		assertEquals(funcionario_maria, teste_ocorrencia.getFuncionarioResponsavel());
	}

	@Test()
	public void testAtualizaFuncionarioOcorrenciaFechada() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		digiclad.criaOcorrencia(nova_ocorrencia);
		aux_estado = Estado.FECHADA;
		Ocorrencia teste_ocorrencia = digiclad.atualizaEstadoOcorrencia(nova_ocorrencia, aux_estado);
		Integer codigo_funcionario = 2;
		String nome_funcionario = "Maria";
		Funcionario funcionario_maria = new Funcionario(codigo_funcionario, nome_funcionario);
		assertEquals(null, digiclad.atualizaFuncionarioOcorrencia(nova_ocorrencia, funcionario_maria));
	}

	@Test()
	public void testAtualizaFuncionarioOcorrenciaInvalida() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		digiclad.criaOcorrencia(nova_ocorrencia);
		Integer codigo_funcionario = 2;
		String nome_funcionario = "Maria";
		Funcionario funcionario_maria = new Funcionario(codigo_funcionario, nome_funcionario);
		Ocorrencia teste_correncia = new Ocorrencia(codigo_ocorrencia = 2, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		assertEquals(null, digiclad.atualizaFuncionarioOcorrencia(teste_correncia, funcionario_maria));
	}

	@Test()
	public void testCriaOcorrencia() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		Ocorrencia teste_ocorrencia = digiclad.criaOcorrencia(nova_ocorrencia);
		assertEquals(nova_ocorrencia, teste_ocorrencia);
	}

	@Test()
	public void testCriaOcorrencia() {
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		assertEquals(codigo_ocorrencia, nova_ocorrencia.getCodigoOcorrencia());
		assertEquals(nome_ocorrencia, nova_ocorrencia.getNomeOcorrencia());
		assertEquals(funcionario, nova_ocorrencia.getFuncionarioResponsavel());
		assertEquals(digiclad, nova_ocorrencia.getProjetoVinculado());
		assertEquals(aux_estado, nova_ocorrencia.getEstadoOcorrencia());
		assertEquals(aux_prioridade, nova_ocorrencia.getPrioridadeOcorrencia());
		assertEquals(aux_tipo, nova_ocorrencia.getTipoOcorrencia());
	}

	@Test()
	public void testCriaOcorrenciaComProjetoErrado() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		codigo_projeto = 2;
		nome_projeto = "ANECOM";
		Projeto anecom = new Projeto(codigo_projeto, nome_projeto, funcionario);
		Ocorrencia ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, anecom, aux_estado, aux_prioridade, aux_tipo);
		Ocorrencia teste_ocorrencia = digiclad.criaOcorrencia(ocorrencia);
		assertEquals(null, teste_ocorrencia);
	}

	@Test()
	public void testCriaProjeto() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		assertEquals(codigo_projeto, digiclad.getCodigoProjeto());
		assertEquals(nome_projeto, digiclad.getNomeProjeto());
	}

	@Test()
	public void testDeletaFuncionario() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		Integer qtde = 1;
		digiclad.deletaFuncionarioVinculado(funcionario);
		assertEquals(qtde, digiclad.getNumeroFuncionarios());
	}

	@Test()
	public void testDeletaFuncionarioVinculadoNaoVinculado() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		Integer codigo_funcionario = 4;
		String nome_funcionario = "Maria";
		Funcionario funcionario_maria = new Funcionario(codigo_funcionario, nome_funcionario);
		Funcionario add_funcionario = digiclad.deletaFuncionarioVinculado(funcionario_maria);
		assertEquals(null, add_funcionario);
	}

	@Test()
	public void testMudaEstadoOcorrencia() {
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		aux_estado = Estado.FECHADA;
		nova_ocorrencia.setEstadoOcorrencia(aux_estado);
		assertEquals(aux_estado, nova_ocorrencia.getEstadoOcorrencia());
	}

	@Test()
	public void testMudaFuncionarioResponsavel() {
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		Integer codigo_funcionario = 2;
		String nome_funcionario = "Carlos";
		Funcionario novo_funcionario = new Funcionario(codigo_funcionario, nome_funcionario);
		nova_ocorrencia.setFuncionatioResponsavel(novo_funcionario);
		assertEquals(novo_funcionario, nova_ocorrencia.getFuncionarioResponsavel());
	}

	@Test()
	public void testMudaPrioridadeOcorrencia() {
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		aux_prioridade = Prioridade.BAIXA;
		nova_ocorrencia.setPrioridadeOcorrencia(aux_prioridade);
		assertEquals(aux_prioridade, nova_ocorrencia.getPrioridadeOcorrencia());
	}

	@Test()
	public void testNumeroFuncionariosVinculados() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		Integer qtde = 1;
		Integer qtde_funcionarios = digiclad.getNumeroFuncionarios();
		assertEquals(qtde, qtde_funcionarios);
	}

	@Test()
	public void testNumeroOcorrencias() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		Integer qtde = 1;
		digiclad.criaOcorrencia(nova_ocorrencia);
		Integer qtde_ocorrencias = digiclad.getNumeroOcorrencias();
		assertEquals(qtde, qtde_ocorrencias);
	}

	@Test()
	public void testVinculaNovoFuncionario() {
		Integer codigo_ocorrencia = 1;
		String nome_ocorrencia = "Bug no teste 1";
		Estado aux_estado = Estado.ABERTA;
		Prioridade aux_prioridade = Prioridade.ALTA;
		Tipo aux_tipo = Tipo.BUG;
		Ocorrencia nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia, funcionario, digiclad, aux_estado, aux_prioridade, aux_tipo);
		Integer codigo_funcionario = 2;
		String nome_funcionario = "Carlos";
		Funcionario novo_funcionario = new Funcionario(codigo_funcionario, nome_funcionario);
		Funcionario add_funcionario = digiclad.vinculaFuncionario(novo_funcionario);
		assertEquals(novo_funcionario, add_funcionario);
	}
}
