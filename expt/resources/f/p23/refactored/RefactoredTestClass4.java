import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Funcionario funcionario;

	private Projeto proj;

	@Before()
	public void setup() {
		funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996, 8, 1));
		proj = new Projeto("nome");
	}

	@Test()
	public void adicionaOcorrenciaProjeto() {
		Ocorrencia oc = proj.adicionaOcorrencia("ocorrencia", TipoOcorrencia.TAREFA, funcionario);
		assertEquals(funcionario, oc.responsavel());
		assertEquals(1, proj.numeroOcorrencias().intValue());
		assertEquals(1, proj.numeroOcorrenciasAbertas().intValue());
	}

	@Test()
	public void adicionaOcorrenciaProjetoComPrioridade() {
		Ocorrencia oc = proj.adicionaOcorrencia("ocorrencia", TipoOcorrencia.TAREFA, funcionario, PrioridadeOcorrencia.ALTA);
		assertEquals(PrioridadeOcorrencia.ALTA, oc.prioridade());
	}

	@Test()
	public void finalizarOcorrencia() {
		Ocorrencia oc = proj.adicionaOcorrencia("ocorrencia", TipoOcorrencia.TAREFA, funcionario, PrioridadeOcorrencia.ALTA);
		Boolean finalizar = funcionario.finalizarOcorrencia(oc);
		assertTrue(finalizar);
		assertEquals(0, proj.numeroOcorrenciasAbertas().intValue());
	}
}
