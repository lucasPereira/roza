import org.junit.Test;

public class RefactoredTestClass32 {

	@Test()
	public void testeResumoDiferenteOcorrencia() {
		String resumo = "resumo";
		Ocorrencia oc = new Ocorrencia(0, resumo, Tipo.BUG, Prioridade.BAIXA);
		String resultado = oc.retornaResumo();
		assertEquals("resumo", resultado);
	}
}
