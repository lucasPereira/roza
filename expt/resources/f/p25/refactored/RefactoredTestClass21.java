import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass21 {

	@Before()
	public void setup() {
		projeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
	}

	@Test()
	public void funcionarioCom1_Ocorrencias() {
		assertEquals(1, funcionario.getOcorrencias().size());
	}

	@Test()
	public void projetoComMultiplasOcorrencias() {
		projeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.BUG, PrioridadeOcorrencia.MEDIA);
		assertEquals(2, projeto.getOcorrencias().size());
	}

	@Test()
	public void projetoComUmaOcorrencia() {
		assertEquals(1, projeto.getOcorrencias().size());
	}
}
