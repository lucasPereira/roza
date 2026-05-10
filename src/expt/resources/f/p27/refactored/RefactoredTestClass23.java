import org.junit.Test;

public class RefactoredTestClass23 {

	@Test()
	public void testeCriacaoEstado() {
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.MELHORIA, Prioridade.BAIXA);
		Estado resultado = oc.retornaEstado();
		assertEquals(Estado.ABERTO, resultado);
	}
}
