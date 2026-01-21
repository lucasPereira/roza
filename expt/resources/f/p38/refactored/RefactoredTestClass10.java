import org.junit.Test;

public class RefactoredTestClass10 {

	@Test()
	public void shouldGetCorrectKindOfPryority() {
		assertEquals(this.ocorrencia.getPrioridade(), EPRIORIDADE_TAREFA.ALTA);
	}
}
