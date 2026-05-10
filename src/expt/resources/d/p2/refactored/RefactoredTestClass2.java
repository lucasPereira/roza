import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void contaLeandroErro() {
		Banco bb = this.novoBanco();
		Conta leandroConta = this.novaConta();
		assertNotEquals("0001-5", leandroConta.obterIdentificador());
		assertNotEquals("Mario", leandroConta.obterTitular());
		assertNotEquals(bb.criarAgencia("Campeche"), leandroConta.obterAgencia());
	}
}
