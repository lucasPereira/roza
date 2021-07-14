public class WithdrawTestWithoutBalance {
    @Test void closeAccountWithoutBalance() {
        BankingSystem bs = new BankingSystem();
        Bank hsbc = bs.createBank("HSBC Holdings", Currency.GBP);
        janeDoe = hsbc.createAccount("Jane Doe");
        janeDoe.close();
        assertTrue(janeDoe.isClosed());
        assertEquals(Money.ZERO, janeDoe.getBalance());
    }
}
