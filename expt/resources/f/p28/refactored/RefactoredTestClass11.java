import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass11 {

	private int maxOcorrencias;

	private String resumoOcorrencia;

	private Funcionario funcionario;

	@Before()
	public void setup() {
		maxOcorrencias = 10;
		resumoOcorrencia = "Problemas ao visualizar email";
		funcionario = new Funcionario("Gilmar Douglas", maxOcorrencias);
	}

	@Test()
	public void alterarPrioridadeOcorrencia() {
		ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
		ocorrencia.alterarPrioridade(PrioridadeOcorrenciaEnum.BAIXA);
		assertEquals(PrioridadeOcorrenciaEnum.BAIXA, ocorrencia.getPrioridade());
	}

	@Test()
	public void criarOcorrencia() {
		ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
		assertEquals(resumoOcorrencia, ocorrencia.getResumo());
		assertEquals(funcionario, ocorrencia.getFuncionario());
		assertEquals(TipoOcorrenciaEnum.TAREFA, ocorrencia.getTipo());
		assertEquals(PrioridadeOcorrenciaEnum.ALTA, ocorrencia.getPrioridade());
	}

	@Test()
	public void trocarFuncionario() {
		Funcionario novoFuncionario = new Funcionario("Thiago Souza", 10);
		ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.MELHORIA, PrioridadeOcorrenciaEnum.BAIXA);
		ocorrencia.trocarFuncionario(novoFuncionario);
		assertEquals(novoFuncionario, ocorrencia.getFuncionario());
	}

	@Test()
	public void verificarEstadoOcorrenciaAoCriar() {
		ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.MELHORIA, PrioridadeOcorrenciaEnum.BAIXA);
		assertTrue(ocorrencia.estaAberta());
	}

	@Test()
	public void verificarEstadoOcorrenciaAoFechar() {
		ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.BUG, PrioridadeOcorrenciaEnum.MEDIA);
		ocorrencia.fecharOcorrencia();
		assertFalse(ocorrencia.estaAberta());
	}
}
