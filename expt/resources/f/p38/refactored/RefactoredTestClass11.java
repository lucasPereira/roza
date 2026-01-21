import org.junit.Test;

public class RefactoredTestClass11 {

	@Test()
	public void shouldGetCorrectTypeOcorrencia() {
		assertEquals(this.ocorrencia.getTipoTarefa(), ETIPO_TAREFA.TAREFA);
	}
}
