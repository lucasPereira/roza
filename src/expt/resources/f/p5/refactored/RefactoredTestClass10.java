import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass10 {

	private Ocorrencia umaOcorrencia;

	@Before()
	public void setup() {
		umaOcorrencia = new Ocorrencia("Trocar a caixeta azul", TipoOcorrencia.Melhoria, Status.Aberta, new Funcionario("Fabio"), Prioridade.Alta);
		umaOcorrencia.setStatus(Status.Fechada);
	}

	@Test()
	public void modificarPrioridadeAoFecharOcorrencia() {
		umaOcorrencia.setPrioridade(Prioridade.Media);
	}

	@Test()
	public void modificarResponsavelAoFecharOcorrencia() {
		umaOcorrencia.setResponsavel(new Funcionario("ABC D Lima"));
	}

	@Test()
	public void modificarStatusOcorrencia() {
		assertEquals(Status.Fechada, umaOcorrencia.getStatus());
	}
}
