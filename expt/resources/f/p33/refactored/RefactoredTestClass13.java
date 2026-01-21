import org.junit.Test;

public class RefactoredTestClass13 {

	@Test()
	public void listaDeProjetosVazia() {
		assertEquals(new ArrayList<Projeto>(), empresa1.getProjetos());
	}
}
