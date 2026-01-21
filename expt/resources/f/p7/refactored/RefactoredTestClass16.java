import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass16 {

	private Projeto meuProjeto;

	@Before()
	public void setup() {
		meuProjeto = new Projeto();
	}

	@Test()
	public void cadastrarOcorrencia_bug() {
		cadastrarOcorrencia(Tipo.BUG);
	}

	@Test()
	public void cadastrarOcorrencia_melhoria() {
		cadastrarOcorrencia(Tipo.MELHORIA);
	}

	@Test()
	public void cadastrarOcorrencia_tarefa() {
		cadastrarOcorrencia(Tipo.TAREFA);
	}

	@Test()
	public void newProjeto() {
		assertTrue(meuProjeto.getOcorrencias().isEmpty());
	}
}
