import org.junit.Test;

public class RefactoredTestClass14 {

	@Test()
	public void trocarPrioridadeDeUmaOcorrencia() {
		projetoWPP.trocarPrioridade(0, Prioridade.ALTA);
		assertEquals(Prioridade.ALTA, projetoWPP.getPrioridade(0));
	}
}
