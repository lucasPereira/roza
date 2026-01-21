import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestOcorrencia implements InterfaceGerenciador {
	
	//--- Private Variables:
	private Projeto digiclad;
	private Integer codigo_projeto;
	private String nome_projeto;
	private Funcionario funcionario;
	
	private Integer codigo_ocorrencia = 1;
	private String nome_ocorrencia = "Bug no teste 1";
	private Estado aux_estado = Estado.ABERTA;
	private Prioridade aux_prioridade = Prioridade.ALTA;
	private Tipo aux_tipo = Tipo.BUG;
	private Ocorrencia nova_ocorrencia;
	
	@Before
	public void configuraOcorrencia() {
		
		Integer codigo_funcionario = 1;
		String nome_funcionario = "Guilherme";
		funcionario = new Funcionario(codigo_funcionario, nome_funcionario);
				
		codigo_projeto = 1;
		nome_projeto = "DIGICLAD";
		digiclad = new Projeto(codigo_projeto, nome_projeto, funcionario);
		
		// Cria uma nova ocorrencia com o construtor para executar os testes
		nova_ocorrencia = new Ocorrencia(codigo_ocorrencia, nome_ocorrencia,
				 						funcionario, digiclad, aux_estado,
				 						aux_prioridade, aux_tipo);
		
	}
	
	@Test
	public void testCriaOcorrencia() {
		
		 assertEquals(codigo_ocorrencia, nova_ocorrencia.getCodigoOcorrencia());
		 assertEquals(nome_ocorrencia, nova_ocorrencia.getNomeOcorrencia());
		 assertEquals(funcionario, nova_ocorrencia.getFuncionarioResponsavel());
		 assertEquals(digiclad, nova_ocorrencia.getProjetoVinculado());
		 assertEquals(aux_estado, nova_ocorrencia.getEstadoOcorrencia());
		 assertEquals(aux_prioridade, nova_ocorrencia.getPrioridadeOcorrencia());
		 assertEquals(aux_tipo, nova_ocorrencia.getTipoOcorrencia());

	}
	
	@Test
	public void testMudaEstadoOcorrencia() {
		
		aux_estado = Estado.FECHADA;
		
		nova_ocorrencia.setEstadoOcorrencia(aux_estado);
		
		 assertEquals(aux_estado, nova_ocorrencia.getEstadoOcorrencia());

	}
	
	@Test
	public void testMudaFuncionarioResponsavel() {
		
		Integer codigo_funcionario = 2;
		String nome_funcionario = "Carlos";
		Funcionario novo_funcionario = new Funcionario(codigo_funcionario, nome_funcionario);
		
		nova_ocorrencia.setFuncionatioResponsavel(novo_funcionario);
		
		assertEquals(novo_funcionario, nova_ocorrencia.getFuncionarioResponsavel());

	}
	
	@Test
	public void testMudaPrioridadeOcorrencia() {
		
		aux_prioridade = Prioridade.BAIXA;
		
		nova_ocorrencia.setPrioridadeOcorrencia(aux_prioridade);
		
		assertEquals(aux_prioridade, nova_ocorrencia.getPrioridadeOcorrencia());

	}
	
}
