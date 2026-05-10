import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	private Projeto projetoA;

	@Before()
	public void setup() {
		projetoA = empresa.criarProjeto("Projeto A");
	}

	@Test()
	public void empresaCriarDoisProjeto() {
		Projeto projetoB = empresa.criarProjeto("Projeto B");
		assertEquals(0, projetoA.getId());
		assertEquals("Projeto A", projetoA.getNome());
		assertEquals(1, projetoB.getId());
		assertEquals("Projeto B", projetoB.getNome());
	}

	@Test()
	public void empresaCriarProjeto() {
		assertEquals(0, projetoA.getId());
		assertEquals("Projeto A", projetoA.getNome());
	}
}
