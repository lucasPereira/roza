import org.junit.Test;

public class RefactoredTestClass13 {

	@Test()
	public void testarLimiteDeOcorrenciasPorFuncionario() {
		Ocorrencia ocorrencia0 = projetoCalculadora.criarOcorrencia("Oc0", gustavoKundlatsch, TipoOcorrencia.TAREFA);
		Ocorrencia ocorrencia1 = projetoCalculadora.criarOcorrencia("Oc1", gustavoKundlatsch, TipoOcorrencia.TAREFA);
		Ocorrencia ocorrencia2 = projetoCalculadora.criarOcorrencia("Oc2", gustavoKundlatsch, TipoOcorrencia.TAREFA);
		Ocorrencia ocorrencia3 = projetoCalculadora.criarOcorrencia("Oc3", gustavoKundlatsch, TipoOcorrencia.TAREFA);
		Ocorrencia ocorrencia4 = projetoCalculadora.criarOcorrencia("Oc4", gustavoKundlatsch, TipoOcorrencia.TAREFA);
		Ocorrencia ocorrencia5 = projetoCalculadora.criarOcorrencia("Oc5", gustavoKundlatsch, TipoOcorrencia.TAREFA);
		Ocorrencia ocorrencia6 = projetoCalculadora.criarOcorrencia("Oc6", gustavoKundlatsch, TipoOcorrencia.TAREFA);
		Ocorrencia ocorrencia7 = projetoCalculadora.criarOcorrencia("Oc7", gustavoKundlatsch, TipoOcorrencia.TAREFA);
		Ocorrencia ocorrencia8 = projetoCalculadora.criarOcorrencia("Oc8", gustavoKundlatsch, TipoOcorrencia.TAREFA);
		Ocorrencia ocorrencia9 = projetoCalculadora.criarOcorrencia("Oc9", gustavoKundlatsch, TipoOcorrencia.TAREFA);
		Ocorrencia falhaSoma = projetoCalculadora.criarOcorrencia("Falha na soma", gustavoKundlatsch, TipoOcorrencia.BUG);
		Integer numeroOcorrencias = gustavoKundlatsch.obterNumeroDeOcorrencias();
		Integer dez = 10;
		assertNotEquals(null, ocorrencia9);
		assertEquals(null, falhaSoma);
		assertEquals(dez, numeroOcorrencias);
	}
}
