
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class TestFuncionario implements InterfaceGerenciador{
	
	//--- Private variables:
	private Funcionario funcionario_guilherme;
	private Projeto digiclad;
	private Projeto anecom;
	private Integer codigo_projeto;
	
	private Integer codigo_ocorrencia = 1;
	private String nome_ocorrencia = "Bug no teste 1";
	private Estado aux_estado = Estado.ABERTA;
	private Prioridade aux_prioridade = Prioridade.ALTA;
	private Tipo aux_tipo = Tipo.BUG;
	private Ocorrencia nova_ocorrencia;
	
	@Before
	public void configuraFuncionario() throws Exception {
		
		Integer codigo = 1;
		String nome = "Guilherme";
		funcionario_guilherme = new Funcionario(codigo, nome);
		digiclad = new Projeto(codigo_projeto = 1, "DIGICLAD", funcionario_guilherme);
		anecom = new Projeto(codigo_projeto = 2, "ANECOM", funcionario_guilherme);
		funcionario_guilherme.addVinculaProjeto(digiclad);
		funcionario_guilherme.addVinculaProjeto(anecom);
			
		// Cria uma nova ocorrencia com o construtor para executar os testes
			nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia,
											funcionario_guilherme, digiclad, aux_estado,
					 						aux_prioridade, aux_tipo);
			
		funcionario_guilherme.addVinculaOcorrencia(nova_ocorrencia);
			
	}
	
	@Test
	public void testGetNomeFuncionario() {
		
		Integer codigo = 1;
		String nome = "Carlos";
		Funcionario funcionario_carlos = new Funcionario(codigo, nome);
		
		String nome_funcionario = funcionario_carlos.getNomeFuncionario();
		Integer codigo_funcionario = funcionario_carlos.getCodigoFuncionario();

		assertEquals(nome, nome_funcionario);
		assertEquals(codigo, codigo_funcionario);
		
	}
	
	@Test
	public void testAddVinculaProjeto() {
		
		Projeto calorimetro = new Projeto(codigo_projeto = 3, "Calorimetro", funcionario_guilherme);
		Projeto projeto = funcionario_guilherme.addVinculaProjeto(calorimetro);
		
		assertEquals(calorimetro, projeto);
		
	}
	
	@Test
	public void testGetNumeroProjetosVinculados() {
		
		Integer qtde = 2;
		
		assertEquals(qtde, funcionario_guilherme.getNumeroProjetosVinculados());
		
	}
	
	
	@Test
	public void testDeletaVinculoProjeto() {
		
		funcionario_guilherme.deletaVinculoProjeto(anecom);
		
		Integer numero_projetos = funcionario_guilherme.getNumeroProjetosVinculados();
		Integer qtde = 1;
		
		assertEquals(qtde, funcionario_guilherme.getNumeroProjetosVinculados());

	}
	
	@Test
	public void testAddVinculaOcorrencia() throws Exception {
		
		assertEquals(nova_ocorrencia, funcionario_guilherme.addVinculaOcorrencia(nova_ocorrencia));
		
	}
	
	@Test(expected=Exception.class)
	public void testErroMais10Ocorrencias() throws Exception {
		
		// Tentativa de vincular mais que 10 ocorrencias (gera exceção)
		TestAddOcorrenciaHelper.Ocorrencias(funcionario_guilherme, digiclad, 12);
			
	}
	
	
	@Test
	public void testGetNumeroOcorrenciasVinculados() throws Exception {
	
		Integer qtde = 1;
		
		assertEquals(qtde, funcionario_guilherme.getNumeroOcorrenciasVinculadas());
		
	}
	
	@Test
	public void testDeletaVinculoOcorrencia() {
		
		funcionario_guilherme.deletaVinculoOcorrencia(nova_ocorrencia);
		
		Integer qtde = 0;
		
		assertEquals(qtde, funcionario_guilherme.getNumeroOcorrenciasVinculadas());

	}
	
	@Test
	public void testDeletaVinculoOcorrenciaInvalida() {
		
		// Cria uma nova ocorrencia com que nao existe no funcionario_guilherme
		nova_ocorrencia = new Ocorrencia(2, nome_ocorrencia,
										funcionario_guilherme, digiclad, aux_estado,
							 			aux_prioridade, aux_tipo);
		
		
		assertEquals(null, funcionario_guilherme.deletaVinculoOcorrencia(nova_ocorrencia));

	}
	
}
