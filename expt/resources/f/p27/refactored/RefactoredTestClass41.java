import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass41 {

	private Ocorrencia oc;

	@Before()
	public void setup() {
		oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.BAIXA);
	}

	@Test()
	public void testeCriacaoPrioridade() {
		Prioridade resultado = oc.retornaPrioridade();
		assertEquals(Prioridade.BAIXA, resultado);
	}

	@Test()
	public void testeTipoOcorrencia() {
		Tipo resultado = oc.retornaTipo();
		assertEquals(Tipo.BUG, resultado);
	}
}
