import org.junit.Test;

public class RefactoredTestClass21 {

	@Test()
	public void nomeVazioProjeto() {
		assertThrows(NomeVazio.class, () -> new Projeto("", empresa1));
	}
}
