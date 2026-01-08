package br.dev.lucas.doctorateexamples.bankingsystem.rozarefactoring;

import br.dev.lucas.doctorateexamples.bankingsystem.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankAccountWithDuplicationTest {
  @Test
  void shouldWithdrawFromAccount() {
    BankingSystem bankingSystem = new BankingSystem();
    Mint unitedStatesMint = bankingSystem.createMint(Currency.USD);
    Bank bankOfAmerica = bankingSystem.createBank("Bank of America");
    BankAccount janeAccount = bankOfAmerica.createAccount("Jane Doe");
    Money tenDollars = unitedStatesMint.issue(10);
    janeAccount.deposit(tenDollars);
    Transaction withdrawTransaction = janeAccount.withdraw(tenDollars);
    assertTrue(withdrawTransaction.isSuccess());
    assertTrue(janeAccount.getBalance().isZero());
  }

  @Test
  void shouldNotAllowToCloseAnAccountWithPositiveBalance() {
    BankingSystem bankingSystem = new BankingSystem();
    Mint unitedStatesMint = bankingSystem.createMint(Currency.USD);
    Bank bankOfAmerica = bankingSystem.createBank("Bank of America");
    BankAccount janeAccount = bankOfAmerica.createAccount("Jane Doe");
    Money tenDollars = unitedStatesMint.issue(10);
    janeAccount.deposit(tenDollars);
    Transaction closeTransaction = janeAccount.close();
    assertFalse(closeTransaction.isSuccess());
    assertFalse(janeAccount.isClosed());
    assertEquals(tenDollars, janeAccount.getBalance());
  }

  @Test
  void shouldNotAllowToWithdrawMoreThanBalance() {
    BankingSystem bankingSystem = new BankingSystem();
    Mint unitedStatesMint = bankingSystem.createMint(Currency.USD);
    Bank bankOfAmerica = bankingSystem.createBank("Bank of America");
    BankAccount janeAccount = bankOfAmerica.createAccount("Jane Doe");
    Money tenDollars = unitedStatesMint.issue(10);
    Transaction withdrawTransaction = janeAccount.withdraw(tenDollars);
    assertFalse(withdrawTransaction.isSuccess());
    assertEquals(tenDollars, janeAccount.getBalance());
  }

  @Test
  void shouldCloseAccount() {
    BankingSystem bankingSystem = new BankingSystem();
    Bank bankOfAmerica = bankingSystem.createBank("Bank of America");
    BankAccount janeAccount = bankOfAmerica.createAccount("Jane Doe");
    Transaction closeTransaction = janeAccount.close();
    assertTrue(closeTransaction.isSuccess());
    assertTrue(janeAccount.isClosed());
    assertTrue(janeAccount.getBalance().isZero());
  }
}
