import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	@Before()
	public void setup() {
		this.ocorrencia = new Ocorrencia();
		this.ocorrencia.completar();
	}

	@Test()
	public void nadaAconteceAoFecharOcorrenciaDuasVezes() {
		this.ocorrencia.completar();
		assertTrue(this.ocorrencia.isCompletada());
		assertTrue(this.ocorrencia.isCompletada());
	}

	@Test()
	public void naoDevePermitirAlterarPrioridadeDepoisDeCompletada() {
		this.ocorrencia.alterarPrioridade(null);
	}

	@Test()
	public void naoDevePermitirAlterarResponsavelDepoisDeCompletada() {
		this.ocorrencia.alterarResponsavel(null);
	}
}
