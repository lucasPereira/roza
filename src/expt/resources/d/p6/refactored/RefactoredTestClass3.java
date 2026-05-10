import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void criaConta() {
		String nomeDaAgencia = new String("Agencia Trindade");
		String nomeDoTitular = new String("Fulano");
		Agencia agencia = TesteHelper.criaAgencia(nomeDaAgencia);
		Conta conta = agencia.criarConta(nomeDoTitular);
		assertEquals(agencia, conta.obterAgencia());
		assertEquals(nomeDoTitular, conta.obterTitular());
		assertEquals(new String("0001-6"), conta.obterIdentificador());
	}
}
