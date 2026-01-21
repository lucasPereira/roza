import org.junit.Test;

public class RefactoredTestClass33 {

	@Test()
	public void testeResumoOcorrencia() {
		String resumo = "teste";
		Ocorrencia oc = new Ocorrencia(0, resumo, Tipo.BUG, Prioridade.BAIXA);
		String resultado = oc.retornaResumo();
		assertEquals("teste", resultado);
	}
}
