import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	@Before()
	public void setup() {
		this.funcionario = new Funcionario();
	}

	@Test()
	public void deveAceitarAceitar10_Ocorrencias() {
		for (int i = 0; i < 10; i++) {
			this.funcionario.addOcorrencia(new Ocorrencia());
		}
		assertEquals(10, this.funcionario.getOcorrencias().size());
	}

	@Test()
	public void deveRespeitarMaximoDeOcorrencias() {
		for (int i = 0; i < 11; i++) {
			this.funcionario.addOcorrencia(new Ocorrencia());
		}
	}
}
