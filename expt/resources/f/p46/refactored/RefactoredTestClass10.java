import org.junit.Test;

public class RefactoredTestClass10 {

	@Test()
	public void testarDescricaoOcorrencia() {
		String ocorrenciaDescricaoEsperada = "Falha na soma";
		Ocorrencia falhaSoma = projetoCalculadora.criarOcorrencia("Falha na soma", gustavoKundlatsch, TipoOcorrencia.BUG);
		String descricaoObtida = falhaSoma.obterDescricao();
		assertEquals(ocorrenciaDescricaoEsperada, descricaoObtida);
	}
}
