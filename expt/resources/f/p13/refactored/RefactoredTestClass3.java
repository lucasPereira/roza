import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void testAdicionarOcorrenciaAUmProjetoInexistente() {
		Ocorrencia o = new Ocorrencia("0000001", "002", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "000001");
		assertThrows(Exception.class, () -> {
			p.addOcorrencia(o);
		});
	}
}
