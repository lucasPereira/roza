import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Funcionario f1;

	private long id;

	@Before()
	public void setup() {
		f1 = new Funcionario("Chris", "0123");
		id = new OcorrenciaService().criaNovaOcorrencia(f1, PrioridadeOcorrencia.ALTA, "", TipoOcorrencia.MELHORIA);
	}

	@Test()
	public void alterarEstadoOcorrencia() {
		new OcorrenciaService().alterarEstadoOcorrencia(id, f1, PrioridadeOcorrencia.ALTA);
		Assert.assertEquals(f1.getOcorrencias().get(0).getPrioridade(), PrioridadeOcorrencia.ALTA);
	}

	@Test()
	public void funcionarioTerminaOcorrencia() {
		new FuncionarioService().terminarOcorrencia(id, f1);
		Assert.assertEquals(f1.getOcorrencias().get(0).getEstadoOcorrencia(), EstadoOcorrencia.FECHADA);
	}
}
