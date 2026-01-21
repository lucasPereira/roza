import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void testAdicionarOcorrenciaDeMesmoID() {
		Ocorrencia o = new Ocorrencia("0000001", "001", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "000001");
		Ocorrencia o1 = new Ocorrencia("0000001", "001", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "000001");
		assertThrows(Exception.class, () -> {
			p.addOcorrencia(o1);
		});
		assertTrue(p.addOcorrencia(o));
	}
}
