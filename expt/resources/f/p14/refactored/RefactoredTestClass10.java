import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass10 {

	private Projeto projetoPreSal;

	@Before()
	public void setup() {
		projetoPreSal = new Projeto("Pré-Sal");
	}

	@Test()
	public void CriaProjetoPreSal() {
		assertEquals("Pré-Sal", projetoPreSal.getNome());
	}

	@Test()
	public void ProjetoPreSalNovoNaoTemOcorrencias() {
		assertEquals(0, projetoPreSal.getOcorrencias().size());
	}
}
