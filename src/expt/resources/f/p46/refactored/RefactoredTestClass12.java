import org.junit.Test;

public class RefactoredTestClass12 {

	@Test()
	public void testarIdentificadorOcorrencia() {
		Integer ocorrenciaIdEsperado = 11;
		Ocorrencia falhaSoma = projetoCalculadora.criarOcorrencia("Falha na soma", gustavoKundlatsch, TipoOcorrencia.BUG);
		Integer idObtido = falhaSoma.obterIdentificador();
		assertEquals(ocorrenciaIdEsperado, idObtido);
	}
}
