import org.junit.Test;

public class RefactoredTestClass12 {

	@Test()
	public void testeChaveNegativa() {
		int chave = -1;
		Ocorrencia oc = new Ocorrencia(chave, "resumo", Tipo.BUG, Prioridade.BAIXA);
	}
}
