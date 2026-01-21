import org.junit.Test;

public class RefactoredTestClass14 {

	@Test()
	public void testaFuncionarioNulo() {
		assertThrows(NullPointerException.class, () -> {
			projeto.criaOcorrencia("Ocorrencia Para Teste", null, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
		}, "Uma ocorrencia fechada n�o pode receber funcion�rio nulo");
	}
}
