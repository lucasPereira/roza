import org.junit.Test;

public class RefactoredTestClass11 {

	@Test()
	public void testeVerificaProjetoSemOcorrencias() {
		assertThrows(OcorrenciaException.class, () -> {
			projeto.obterOcorrencia(0);
		});
	}
}
