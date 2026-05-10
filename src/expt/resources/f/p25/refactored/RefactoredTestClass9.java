import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void ocoreenciasEmProjetosDiferentes() {
		Projeto novoProjeto = empresa.iniciaProjeto("Projeto Teste");
		Ocorrencia novaOcorrencia = novoProjeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
		assertEquals(ocorrencia.getChave(), novaOcorrencia.getChave());
	}
}
