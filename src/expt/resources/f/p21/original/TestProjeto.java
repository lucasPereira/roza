import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestProjeto implements InterfaceGerenciador{
	
	//--- Private Variables:
	private Projeto digiclad;
	private Integer codigo_projeto;
	private String nome_projeto;
	private Funcionario funcionario;
	
	private Ocorrencia nova_ocorrencia;
	private Integer codigo_ocorrencia;
	private String nome_ocorrencia;
	private Estado aux_estado;
	private Prioridade aux_prioridade;
	private Tipo aux_tipo;
	
	@Before
	public void configuraProjeto() {
		
		Integer codigo_funcionario = 1;
		String nome_funcionario = "Guilherme";
		funcionario = new Funcionario(codigo_funcionario, nome_funcionario);
				
		codigo_projeto = 1;
		nome_projeto = "DIGICLAD";
		
		// Cria um novo projeto com o construtor para executar os testes
			digiclad = new Projeto(codigo_projeto, nome_projeto, funcionario);
		
		// Instancia para criar ocorrencia no projeto Digiclad
			codigo_ocorrencia = 1;
			nome_ocorrencia = "Bug no teste 1";
			aux_estado = Estado.ABERTA;
			aux_prioridade = Prioridade.ALTA;
			aux_tipo = Tipo.BUG;
			
		    nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia,
													funcionario, digiclad, aux_estado, aux_prioridade,
													aux_tipo);
			
	}
	
	@Test
	public void testCriaProjeto() {
		
		assertEquals(codigo_projeto, digiclad.getCodigoProjeto());
		assertEquals(nome_projeto, digiclad.getNomeProjeto());

	}
	
	@Test
	public void testVinculaNovoFuncionario() {
		
		Integer codigo_funcionario = 2;
		String nome_funcionario = "Carlos";
		Funcionario novo_funcionario = new Funcionario(codigo_funcionario, nome_funcionario);
		
		Funcionario add_funcionario = digiclad.vinculaFuncionario(novo_funcionario);
		
		assertEquals(novo_funcionario, add_funcionario);
		
	}
	
	@Test
	public void testNumeroFuncionariosVinculados() {
		
		Integer qtde = 1;
		Integer qtde_funcionarios = digiclad.getNumeroFuncionarios();	
		
		assertEquals(qtde, qtde_funcionarios);
		
	}
	
	@Test
	public void testDeletaFuncionarioVinculadoNaoVinculado() {
		
		Integer codigo_funcionario = 4;
		String nome_funcionario = "Maria";
		Funcionario funcionario_maria = new Funcionario(codigo_funcionario, nome_funcionario);
		Funcionario add_funcionario = digiclad.deletaFuncionarioVinculado(funcionario_maria);
		
		// Como não existe o funcionario Maria, então, retornara null
		assertEquals(null, add_funcionario);
		
	}
	
	@Test
	public void testDeletaFuncionario() {
		
		Integer qtde = 1;
		digiclad.deletaFuncionarioVinculado(funcionario);
		
		assertEquals(qtde, digiclad.getNumeroFuncionarios());
		
	}
	
	@Test
	public void testCriaOcorrencia() {
		
		Ocorrencia teste_ocorrencia = digiclad.criaOcorrencia(nova_ocorrencia);
		
		assertEquals(nova_ocorrencia, teste_ocorrencia);
		
	}
	
	@Test
	public void testCriaOcorrenciaComProjetoErrado() {
		
		codigo_projeto = 2;
		nome_projeto = "ANECOM";
		Projeto anecom = new Projeto(codigo_projeto, nome_projeto, funcionario);
		
		Ocorrencia ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia,
												funcionario, anecom, aux_estado, aux_prioridade,
												aux_tipo);
		
		Ocorrencia teste_ocorrencia = digiclad.criaOcorrencia(ocorrencia);
		
		assertEquals(null, teste_ocorrencia);
		
	}
	
	@Test
	public void testNumeroOcorrencias() {
		
		Integer qtde = 1;
		digiclad.criaOcorrencia(nova_ocorrencia);
		Integer qtde_ocorrencias = digiclad.getNumeroOcorrencias();
				
		assertEquals(qtde, qtde_ocorrencias);		

	}
	
	@Test
	public void testAtualizaEstadoOcorrenciaInvalido() {
		
		//Cria ocorrencia nao atrelada a este projeto
		Ocorrencia teste_correncia = new Ocorrencia(codigo_ocorrencia = 2, nome_ocorrencia,
												funcionario, digiclad, aux_estado, aux_prioridade,
													aux_tipo);
		
		aux_estado = Estado.FECHADA;
		
		assertEquals(null, digiclad.atualizaEstadoOcorrencia(teste_correncia, aux_estado));
		
	}
	
	@Test
	public void testAtualizaEstadoOcorrencia() {
		
		digiclad.criaOcorrencia(nova_ocorrencia);
		
		//Antes a ocorrencia estava em aberto, agora é para fechar (finalizada)
		aux_estado = Estado.FECHADA;
		
		Ocorrencia teste_ocorrencia = digiclad.atualizaEstadoOcorrencia(nova_ocorrencia, aux_estado);
		
		assertEquals(aux_estado, teste_ocorrencia.getEstadoOcorrencia());
		
	}
	
	@Test
	public void testAtualizaFuncionarioOcorrencia() {
		
		digiclad.criaOcorrencia(nova_ocorrencia);
		
		Integer codigo_funcionario = 2;
		String nome_funcionario = "Maria";
		Funcionario funcionario_maria = new Funcionario(codigo_funcionario, nome_funcionario);
		
		Ocorrencia teste_ocorrencia = digiclad.atualizaFuncionarioOcorrencia(nova_ocorrencia, funcionario_maria);
		
		assertEquals(funcionario_maria, teste_ocorrencia.getFuncionarioResponsavel());
		
	}
	
	@Test
	public void testAtualizaFuncionarioOcorrenciaFechada() {
		
		digiclad.criaOcorrencia(nova_ocorrencia);
		
		//Antes a ocorrencia estava em aberto, agora é para fechar (finalizada)
		aux_estado = Estado.FECHADA;
				
		Ocorrencia teste_ocorrencia = digiclad.atualizaEstadoOcorrencia(nova_ocorrencia, aux_estado);
		
		Integer codigo_funcionario = 2;
		String nome_funcionario = "Maria";
		Funcionario funcionario_maria = new Funcionario(codigo_funcionario, nome_funcionario);
		
		assertEquals(null, digiclad.atualizaFuncionarioOcorrencia(nova_ocorrencia, funcionario_maria));
		
	}
	
	@Test
	public void testAtualizaFuncionarioOcorrenciaInvalida() {
		
		digiclad.criaOcorrencia(nova_ocorrencia);
		
		Integer codigo_funcionario = 2;
		String nome_funcionario = "Maria";
		Funcionario funcionario_maria = new Funcionario(codigo_funcionario, nome_funcionario);
		
		//Cria ocorrencia nao atrelada a este projeto
		Ocorrencia teste_correncia = new Ocorrencia(codigo_ocorrencia = 2, nome_ocorrencia,
													funcionario, digiclad, aux_estado, aux_prioridade,
													aux_tipo);
		
		assertEquals(null, digiclad.atualizaFuncionarioOcorrencia(teste_correncia, funcionario_maria));
		
	}
	
	
}
