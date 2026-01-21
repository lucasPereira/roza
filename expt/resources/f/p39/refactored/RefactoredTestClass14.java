import org.junit.Test;

public class RefactoredTestClass14 {

	@Test()
	public void trocarPrioridadeDeUmaOcorrencia() {
		projeto.trocarPrioridade(0, Prioridade.ALTA);
		assertEquals(Prioridade.ALTA, projeto.pegarPrioridade(0));
	}
}
