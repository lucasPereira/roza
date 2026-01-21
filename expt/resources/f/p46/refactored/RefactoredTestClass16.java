import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass16 {

	private Ocorrencia falhaSoma;

	private Integer ocorrenciaId;

	@Before()
	public void setup() {
		falhaSoma = projetoCalculadora.criarOcorrencia("Falha na soma", gustavoKundlatsch, TipoOcorrencia.BUG);
		ocorrenciaId = 11;
	}

	@Test()
	public void funcionarioFinalizarOcorrencia() {
		gustavoKundlatsch.finalizarOcorrencia(ocorrenciaId);
		Ocorrencia ocorrenciaObtida = gustavoKundlatsch.obterOcorrencia(ocorrenciaId);
		assertEquals(EstadoOcorrencia.COMPLETADA, ocorrenciaObtida.obterEstado());
	}

	@Test()
	public void funcionarioObterOcorrenciaPorId() {
		Ocorrencia ocorrenciaObtida = gustavoKundlatsch.obterOcorrencia(ocorrenciaId);
		assertEquals(ocorrenciaObtida, falhaSoma);
	}
}
