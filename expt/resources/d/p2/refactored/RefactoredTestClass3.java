import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void criacaoAgencia() {
		Agencia bbTrindade = this.novaAgencia();
		assertEquals("001", bbTrindade.obterIdentificador());
		assertEquals(TRINDADE, bbTrindade.obterNome());
		assertEquals(BANCO_DO_BRASIL, bbTrindade.obterBanco().obterNome());
	}
}
