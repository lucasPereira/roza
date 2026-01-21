import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void funcionarioCom10Ocorrencias() {
		criaOcorrenciasProjeto(projeto, funcionario, 10);
		assertEquals(10, funcionario.getOcorrencias().size());
	}
}
