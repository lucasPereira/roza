import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void criaAgencia() {
		Banco bancoDoBrasil = TesteHelper.criaBancoDoBrasil();
		String nomeDaAgencia = "Agencia da Trindade";
		Agencia agenciaTrindade = bancoDoBrasil.criarAgencia(nomeDaAgencia);
		assertEquals(nomeDaAgencia, agenciaTrindade.obterNome());
		assertEquals("Banco do Brasil", bancoDoBrasil.obterNome());
		assertEquals(new String("001"), agenciaTrindade.obterIdentificador());
	}
}
