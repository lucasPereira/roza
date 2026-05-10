import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass20 {

	private Ocorrencia ocorrencia;

	@Before()
	public void setup() {
		ocorrencia = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		ocorrencia.setPrioridade(Ocorrencia.Prioridades.Baixa);
	}

	@Test()
	public void mudaPrioridadeBaixaDepoisMediaTest() {
		ocorrencia.setPrioridade(Ocorrencia.Prioridades.Media);
		assertEquals(Ocorrencia.Prioridades.Baixa, ocorrencia.getPrioridade());
		assertEquals(Ocorrencia.Prioridades.Media, ocorrencia.getPrioridade());
	}

	@Test()
	public void mudaPrioridadeBaixaTest() {
		assertEquals(Ocorrencia.Prioridades.Baixa, ocorrencia.getPrioridade());
	}
}
