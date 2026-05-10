import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void umProjetoNaoPodeTerUmNomeNulo() {
		String nomeProjeto = null;
		new Projeto(nomeProjeto);
	}
}
