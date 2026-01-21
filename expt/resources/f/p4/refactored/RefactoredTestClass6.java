import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Funcionario joao;

	private Ocorrencia ocorrencia;

	private Funcionario carlos;

	@Before()
	public void setup() {
		joao = new Funcionario();
		ocorrencia = new Ocorrencia(joao);
		carlos = new Funcionario();
		ocorrencia.alterarResponsavel(carlos);
	}

	@Test()
	public void alteraResponsavel() {
		assertEquals(carlos, ocorrencia.obtemResponsavel());
	}

	@Test()
	public void alteraResponsavelComOcorrenciaCompletada() {
		ocorrencia.alterarEstado(Estados.COMPLETADA);
		ocorrencia.alterarResponsavel(joao);
	}
}
