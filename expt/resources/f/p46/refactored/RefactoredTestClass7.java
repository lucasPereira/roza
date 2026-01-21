import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void obterNomeFuncionario() {
		String nomeEsperado = "Gustavo Kundlatsch";
		String nomeObtido = gustavoKundlatsch.obterNome();
		assertEquals(nomeEsperado, nomeObtido);
	}
}
