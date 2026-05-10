import org.junit.Test;

public class RefactoredTestClass22 {

	@Test()
	public void testeCriaProjetoNomeDiferente() {
		Projeto proj = new Projeto("projeto x");
		String nome = "projeto y";
		Projeto result = new Projeto(nome);
		assertEquals(nome, result.getNome());
	}
}
