import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void testAdicionarOnzeOcorrencias() {
		for (int i = 0; i < 11; i++) {
			Ocorrencia o = new Ocorrencia("0000001", "001", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "0000" + (i < 10 ? "0" + i : i));
			if (i < 10) {
				assertTrue(p.addOcorrencia(o));
			} else {
				assertFalse(p.addOcorrencia(o));
			}
		}
	}
}
