public class WithdrawTest {

	private Account jane;
	private Money fiveEuros;
	private Mint mint;

	@Before
	public void setup() {
		mint = new Mint(Currency.EUR);
		BankingSystem bs = new BankingSystem();
		Bank ecb = new Bank("European Central Bank", Currency.EUR);
		jane = ecb.createAccount("Jane Doe");
		fiveEuros = mint.issue(5);
		jane.deposit(fiveEuros);
	}

	@Test
	public void withdrawAccountBalance() {
		Transaction withdraw = jane.withdraw(fiveEuros);
		assertTrue(withdraw.success());
		assertTrue(jane.getBalance().isZero());
	}

	@Test
	public void withdrawMoreThanBalance() {
		Money tenEuros = mint.issue(10);
		Transaction withdraw = jane.withdraw(tenEuros);
		assertFalse(withdraw.success());
		assertTrue(fiveEuros, jane.getBalance());
	}

}
