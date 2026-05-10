import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass14 {

	private Funcionario meuResponsavel;

	@Before()
	public void setup() {
		meuResponsavel = new Funcionario();
	}

	@Test()
	public void newOcorrencia_BUG() {
		newOcorrencia(Tipo.BUG);
	}

	@Test()
	public void newOcorrencia_MELHORIA() {
		newOcorrencia(Tipo.MELHORIA);
	}

	@Test()
	public void newOcorrencia_TAREFA() {
		newOcorrencia(Tipo.TAREFA);
	}
}
