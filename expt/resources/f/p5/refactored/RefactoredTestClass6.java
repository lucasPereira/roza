import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Projeto umProjeto;

	@Before()
	public void setup() {
		umProjeto = new Projeto("Projeto X");
	}

	@Test()
	public void addUmaOcorrencia() {
		Ocorrencia umaOcorrencia = new Ocorrencia("Corrigir Bug", TipoOcorrencia.Bug, Status.Aberta, new Funcionario("Fabio"), Prioridade.Alta);
		umProjeto.addOcorrencia(umaOcorrencia);
		assertEquals(umaOcorrencia, umProjeto.getOcorrencia(0));
	}

	@Test()
	public void retornaNomeProjeto() {
		assertEquals("Projeto X", umProjeto.getNomeProjeto());
	}
}
