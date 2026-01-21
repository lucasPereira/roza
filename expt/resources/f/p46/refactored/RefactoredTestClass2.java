import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void alterarResponsavel() {
		Ocorrencia falhaSoma = projetoCalculadora.criarOcorrencia("Falha na soma", gustavoKundlatsch, TipoOcorrencia.BUG);
		Funcionario jorge = empresaDeSoftware.adicionarFuncionario("Jorge");
		falhaSoma.definirResponsavel(jorge);
		Integer ocorrenciaId = 11;
		Ocorrencia ocorrenciaObtida = jorge.obterOcorrencia(ocorrenciaId);
		assertEquals(falhaSoma, ocorrenciaObtida);
	}
}
