public class TestAddOcorrenciaHelper implements InterfaceGerenciador{

	private static Funcionario funcionario;
	private static Projeto projeto;
	private static Integer num_ocorrencias;
	
	private Integer codigo_ocorrencia;
	private static String nome_ocorrencia = "Bug no teste";
	private static Estado aux_estado = Estado.ABERTA;
	private static Prioridade aux_prioridade = Prioridade.ALTA;
	private static Tipo aux_tipo = Tipo.BUG;
	private static Ocorrencia nova_ocorrencia;
	
	public static Funcionario Ocorrencias(Funcionario objeto_funcionario, Projeto objeto_projeto, Integer numero_ocorrencias) throws Exception {
		
		funcionario = objeto_funcionario;
		projeto = objeto_projeto;
		num_ocorrencias = numero_ocorrencias;
		AddOcorrencias();
		
		return funcionario;
	}
	
	private static void AddOcorrencias() throws Exception {
		
		for (int i = 1; i <= num_ocorrencias; i++) {
			
			// Cria uma nova ocorrencia com o construtor para executar os testes
			nova_ocorrencia = new Ocorrencia(i, nome_ocorrencia,
					 						funcionario, projeto, aux_estado,
					 						aux_prioridade, aux_tipo);
			
			funcionario.addVinculaOcorrencia(nova_ocorrencia);
			
		}
		
	}
	
}
