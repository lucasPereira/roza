import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void testCriaContaGuilhermeUfsc() {
		Banco banco_universitario = TestBancoAgenciaHelper.CriaBancoUniversitario();
		Agencia agencia_ufsc = TestBancoAgenciaHelper.CriaAgenciaUniversitaria();
		String titular_conta = "Guilherme";
		conta_guilherme = agencia_ufsc.criarConta(titular_conta);
		assertEquals(titular_conta, conta_guilherme.obterTitular());
		assertEquals(agencia_ufsc, conta_guilherme.obterAgencia());
	}
}
