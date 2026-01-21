import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass17 {

	private Ocorrencia newOcorrencia;

	@Before()
	public void setup() {
		newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
	}

	@Test()
	public void mudarPropriedadeOcorrencia() {
		newOcorrencia.changePrioridade("baixa");
		assertEquals("baixa", newOcorrencia.getPrioridade());
	}

	@Test()
	public void testarFuncionario() {
		assertEquals(stefanoFuncionario, newOcorrencia.getFuncionario());
	}

	@Test()
	public void testarID() {
		assertEquals(1, newOcorrencia.getID());
	}

	@Test()
	public void testarProjeto() {
		assertEquals(newProjeto, newOcorrencia.getProjeto());
	}

	@Test()
	public void testarPropriedade() {
		assertEquals("alta", newOcorrencia.getPrioridade());
	}

	@Test()
	public void testarTipo() {
		assertEquals("bug", newOcorrencia.getTipo());
	}
}
