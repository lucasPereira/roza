import org.junit.Test;

public class RefactoredTestClass21 {

	@Test()
	public void testeCriaProjeto() {
		Projeto proj = new Projeto("projeto x");
		String nome = "projeto x";
		Projeto result = new Projeto(nome);
		assertEquals(nome, result.getNome());
	}
}
