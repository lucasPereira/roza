import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private Funcionario pedro;

	private Ocorrencia o;

	@Before()
	public void setup() {
		pedro = new Funcionario("Pedro");
		o = new Ocorrencia("", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.ALTA);
		pedro.adicionarOcorrencia(o);
	}

	@Test()
	public void testAdicionarOcorrenciaAoFuncionario() {
		assertTrue(pedro.ocorrencias().contains(o));
	}

	@Test()
	public void testRemoverOcorrenciaDoFuncionario() {
		pedro.removerOcorrencia(o);
		assertFalse(pedro.ocorrencias().contains(o));
	}
}
