import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass15 {

	private Ocorrencia falhaSoma;

	@Before()
	public void setup() {
		falhaSoma = projetoCalculadora.criarOcorrencia("Falha na soma", gustavoKundlatsch, TipoOcorrencia.BUG);
		falhaSoma.definirPrioridade(Prioridade.ALTA);
	}

	@Test()
	public void alterarEstadoOcorrenciaFechada() {
		falhaSoma.definirEstado(EstadoOcorrencia.COMPLETADA);
		falhaSoma.definirPrioridade(Prioridade.MEDIA);
		Prioridade proridadeObtida = falhaSoma.obterPrioridade();
		assertEquals(Prioridade.ALTA, proridadeObtida);
	}

	@Test()
	public void atribuirPrioridade() {
		Prioridade proridadeObtida = falhaSoma.obterPrioridade();
		assertEquals(Prioridade.ALTA, proridadeObtida);
	}
}
