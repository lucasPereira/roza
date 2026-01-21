import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass17 {

	private Ocorrencia falhaSoma;

	@Before()
	public void setup() {
		falhaSoma = projetoCalculadora.criarOcorrencia("Falha na soma", gustavoKundlatsch, TipoOcorrencia.BUG);
	}

	@Test()
	public void associarFuncionarioAOcorrencia() {
		Integer numeroDeOcorrencias = gustavoKundlatsch.obterNumeroDeOcorrencias();
		assertEquals(um, numeroDeOcorrencias);
	}

	@Test()
	public void obterEstadoInicialDaOcorrencia() {
		EstadoOcorrencia estadoObtido = falhaSoma.obterEstado();
		assertEquals(EstadoOcorrencia.ABERTA, estadoObtido);
	}

	@Test()
	public void testarPrioridadeNaoAtribuida() {
		Prioridade proridadeObtida = falhaSoma.obterPrioridade();
		assertEquals(Prioridade.NAO_ATRIBUIDA, proridadeObtida);
	}
}
