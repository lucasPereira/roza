import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass15 {

	private Funcionario meuFuncionario;

	private String resumo;

	private Ocorrencia minhaOcorrencia;

	@Before()
	public void setup() {
		meuFuncionario = new Funcionario();
		resumo = "meu resumo bonitinho";
		minhaOcorrencia = new Ocorrencia(resumo, meuFuncionario, Tipo.TAREFA);
	}

	@Test()
	public void terminaOcorrencia() {
		meuFuncionario.terminaOcorrencia(minhaOcorrencia);
		assertEquals(Estado.COMPLETADA, minhaOcorrencia.getEstado());
		assertFalse(meuFuncionario.getOcorrenciasAbertas().contains(minhaOcorrencia));
	}

	@Test()
	public void terminaOcorrencia_jaTerminada() {
		meuFuncionario.terminaOcorrencia(minhaOcorrencia);
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuFuncionario.getOcorrenciasAbertas());
		try {
			meuFuncionario.terminaOcorrencia(minhaOcorrencia);
		} catch (OcorrenciaJaCompletada e) {
			assertEquals(listaDeOcorrencias, meuFuncionario.getOcorrenciasAbertas());
			throw e;
		}
	}

	@Test()
	public void terminaOcorrencia_terminadaPorUmNaoResponsavel() {
		Estado estadoOriginal = minhaOcorrencia.getEstado();
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuFuncionario.getOcorrenciasAbertas());
		Funcionario estranho = new Funcionario();
		try {
			estranho.terminaOcorrencia(minhaOcorrencia);
		} catch (OcorrenciaManipuladaPorUmNaoResponsavel e) {
			assertEquals(estadoOriginal, minhaOcorrencia.getEstado());
			assertEquals(listaDeOcorrencias, meuFuncionario.getOcorrenciasAbertas());
			throw e;
		}
	}
}
