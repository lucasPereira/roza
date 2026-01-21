import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass17 {

	private String resumo;

	private Funcionario meuResponsavel;

	private Ocorrencia minhaOcorrencia;

	@Before()
	public void setup() {
		resumo = "Do que se trata a ocorrência";
		meuResponsavel = new Funcionario();
		minhaOcorrencia = new Ocorrencia(resumo, meuResponsavel, Tipo.MELHORIA);
	}

	@Test()
	public void completarOcorrencia() {
		minhaOcorrencia.completar(meuResponsavel);
		assertEquals(Estado.COMPLETADA, minhaOcorrencia.getEstado());
		assertFalse(meuResponsavel.getOcorrenciasAbertas().contains(minhaOcorrencia));
	}

	@Test()
	public void completarOcorrencia_jaCompletada() {
		minhaOcorrencia.completar(meuResponsavel);
		minhaOcorrencia.completar(meuResponsavel);
	}

	@Test()
	public void completarOcorrencia_manipuladaPorUmNaoResponsavel() {
		Estado estadoOriginal = minhaOcorrencia.getEstado();
		try {
			minhaOcorrencia.completar(new Funcionario());
		} catch (OcorrenciaManipuladaPorUmNaoResponsavel e) {
			assertEquals(estadoOriginal, minhaOcorrencia.getEstado());
			throw e;
		}
	}

	@Test()
	public void setPrioridade_ALTA() {
		setPrioridade(Prioridade.ALTA);
	}

	@Test()
	public void setPrioridade_BAIXA() {
		setPrioridade(Prioridade.BAIXA);
	}

	@Test()
	public void setPrioridade_MEDIA() {
		setPrioridade(Prioridade.MEDIA);
	}

	@Test()
	public void setPrioridade_deUmaOcorrenciaJaCompletada() {
		Prioridade prioridadeOriginal = minhaOcorrencia.getPrioridade();
		minhaOcorrencia.completar(meuResponsavel);
		try {
			minhaOcorrencia.setPrioridade(Prioridade.ALTA);
		} catch (OcorrenciaJaCompletada e) {
			assertEquals(prioridadeOriginal, minhaOcorrencia.getPrioridade());
			throw e;
		}
	}

	@Test()
	public void setResponsavel() {
		Funcionario novoResponsavel = new Funcionario();
		minhaOcorrencia.setResponsavel(novoResponsavel);
		assertFalse(meuResponsavel.getOcorrenciasAbertas().contains(minhaOcorrencia));
		assertEquals(novoResponsavel, minhaOcorrencia.getFuncionarioResponsavel());
	}

	@Test()
	public void setResponsavel_atualResponsavel() {
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuResponsavel.getOcorrenciasAbertas());
		try {
			minhaOcorrencia.setResponsavel(meuResponsavel);
		} catch (FuncionarioJaPossuiTalOcorrencia e) {
			assertEquals(listaDeOcorrencias, meuResponsavel.getOcorrenciasAbertas());
			throw e;
		}
	}

	@Test()
	public void setResponsavel_deUmaOcorrenciaJaCompletada() {
		minhaOcorrencia.completar(meuResponsavel);
		Funcionario novoResponsavel = new Funcionario();
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(novoResponsavel.getOcorrenciasAbertas());
		try {
			minhaOcorrencia.setResponsavel(novoResponsavel);
		} catch (OcorrenciaJaCompletada e) {
			assertEquals(listaDeOcorrencias, novoResponsavel.getOcorrenciasAbertas());
			throw e;
		}
	}
}
