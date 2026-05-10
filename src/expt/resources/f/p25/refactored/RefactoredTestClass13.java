import org.junit.Test;

public class RefactoredTestClass13 {

	@Test()
	public void testaChaveUnicaOcorrencia() {
		Ocorrencia novaOcorrencia = projeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
		assertNotEquals(ocorrencia.getChave(), novaOcorrencia.getChave());
	}
}
