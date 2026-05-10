import org.junit.Test;

public class RefactoredTestClass14 {

	@Test()
	public void testarTipoOcorrencia() {
		Ocorrencia adicionarMultiplicacao = projetoCalculadora.criarOcorrencia("Adicionar multiplicação", gustavoKundlatsch, TipoOcorrencia.TAREFA);
		TipoOcorrencia tipoOcorrenciaObtido = adicionarMultiplicacao.obterTipoDeOcorrencia();
		assertEquals(TipoOcorrencia.TAREFA, tipoOcorrenciaObtido);
	}
}
