import org.junit.Test;

public class RefactoredTestClass37 {

	@Test()
	public void testeTipoOcorrenciaNull() {
		Ocorrencia oc = new Ocorrencia(0, "resumo", null, Prioridade.BAIXA);
	}
}
