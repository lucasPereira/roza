import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private BankingSystem bankingSystem;

	private Mint unitedStatesMint;

	private Bank bankOfAmerica;

	private BankAccount janeAccount;

	private Money tenDollars;

	@Before()
	public void setup() {
		bankingSystem = new BankingSystem();
		unitedStatesMint = bankingSystem.createMint(Currency.USD);
		bankOfAmerica = bankingSystem.createBank("Bank of America");
		janeAccount = bankOfAmerica.createAccount("Jane Doe");
		tenDollars = unitedStatesMint.issue(10);
	}

	@Test()
	public void shouldNotAllowToCloseAnAccountWithPositiveBalance() {
		janeAccount.deposit(tenDollars);
		Transaction closeTransaction = janeAccount.close();
		assertFalse(closeTransaction.isSuccess());
		assertFalse(janeAccount.isClosed());
		assertEquals(tenDollars, janeAccount.getBalance());
	}

	@Test()
	public void shouldNotAllowToWithdrawMoreThanBalance() {
		Transaction withdrawTransaction = janeAccount.withdraw(tenDollars);
		assertFalse(withdrawTransaction.isSuccess());
		assertEquals(tenDollars, janeAccount.getBalance());
	}

	@Test()
	public void shouldWithdrawFromAccount() {
		janeAccount.deposit(tenDollars);
		Transaction withdrawTransaction = janeAccount.withdraw(tenDollars);
		assertTrue(withdrawTransaction.isSuccess());
		assertTrue(janeAccount.getBalance().isZero());
	}
}
