import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void criarProjeto() {
		String nomeProjeto = projetoCalculadora.obterNome();
		assertEquals("Calculadora", nomeProjeto);
	}
}
