import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void umProjetoNaoPodeTerUmNomeVazio() {
		String nomeProjeto = "";
		new Projeto(nomeProjeto);
	}
}
