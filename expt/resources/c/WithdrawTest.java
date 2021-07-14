public class WithdrawTest {
    private Account janeDoe;
    private Mint royalMint;
    private Money fivePoundsSterling;
    @BeforeEach void setup() {
        BankingSystem bs = new BankingSystem();
        Bank hsbc = bs.createBank("HSBC Holdings", Currency.GBP);
        janeDoe = hsbc.createAccount("Jane Doe");
        royalMint = bs.createMint("Royal Mint", Currency.GBP);
        fivePoundsSterling = royalMint.issue(5);
        janeDoe.deposit(fivePoundsSterling);
    }
    @Test void depositeFiveAndWithdrawFive() {
        Transaction withdraw = janeDoe.withdraw(fivePoundsSterling);
        assertTrue(withdraw.sucesss());
        assertEquals(Money.ZERO, janeDoe.getBalance());
    }
    @Test void depositeFiveAndWithdrawTen() {
        Money tenPoundsSterling = royalMint.issue(10);
        Transaction withdraw = janeDoe.withdraw(tenPoundsSterling);
        assertFalse(withdraw.sucesss());
        assertEquals(fivePoundsSterling, janeDoe.getBalance());
    }
}
