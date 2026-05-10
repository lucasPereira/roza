import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void funcionarioComOcorrenciasDeDiferentesProjetos() {
		Projeto novoProjeto = empresa.iniciaProjeto("Projeto Teste 2");
		projeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
		novoProjeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
		assertEquals(2, funcionario.getOcorrencias().size());
	}
}
