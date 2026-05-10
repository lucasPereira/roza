import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void shouldCorrectlyChangePriority() {
		this.ocorrencia.setPrioridade(EPRIORIDADE_TAREFA.MEDIA);
		assertEquals(this.ocorrencia.getPrioridade(), EPRIORIDADE_TAREFA.MEDIA);
	}
}
