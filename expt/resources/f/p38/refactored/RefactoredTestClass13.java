import org.junit.Test;

public class RefactoredTestClass13 {

	@Test()
	public void shouldHaveTheCorrectResumo() {
		assertEquals(this.ocorrencia.getResumo(), "Resumo teste");
	}
}
