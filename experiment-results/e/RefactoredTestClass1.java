import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void shouldCloseAccount() {
		BankingSystem bankingSystem = new BankingSystem();
		Bank bankOfAmerica = bankingSystem.createBank("Bank of America");
		BankAccount janeAccount = bankOfAmerica.createAccount("Jane Doe");
		Transaction closeTransaction = janeAccount.close();
		assertTrue(closeTransaction.isSuccess());
		assertTrue(janeAccount.isClosed());
		assertTrue(janeAccount.getBalance().isZero());
	}
}
