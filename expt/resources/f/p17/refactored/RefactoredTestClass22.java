import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass22 {

	private Ocorrencia ocorrencia;

	@Before()
	public void setup() {
		ocorrencia = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
	}

	@Test()
	public void VerificaPrioridadeMediaTest() {
		assertEquals(Ocorrencia.Prioridades.Media, ocorrencia.getPrioridade());
	}

	@Test()
	public void VerificaTipoBugTest() {
		Ocorrencia ocorrencia1 = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Bug);
		assertEquals(Ocorrencia.Tipos.Bug, ocorrencia1.getTipo());
	}

	@Test()
	public void VerificaTipoMelhoriaTest() {
		Ocorrencia ocorrencia1 = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Melhoria);
		assertEquals(Ocorrencia.Tipos.Melhoria, ocorrencia1.getTipo());
	}

	@Test()
	public void VerificaTipoTarefaTest() {
		Ocorrencia ocorrencia1 = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertEquals(Ocorrencia.Tipos.Tarefa, ocorrencia1.getTipo());
	}

	@Test()
	public void adicionaResumo() {
		ocorrencia.adicionaResumo("Resumo da ocorrencia");
		assertEquals("Resumo da ocorrencia", ocorrencia.getResumo());
	}

	@Test()
	public void criaOcorrencia() {
		assertEquals("Ocorrencia", ocorrencia.getNome());
	}

	@Test()
	public void mudaPrioridadeAltaTest() {
		ocorrencia.setPrioridade(Ocorrencia.Prioridades.Alta);
		assertEquals(Ocorrencia.Prioridades.Alta, ocorrencia.getPrioridade());
	}
}
