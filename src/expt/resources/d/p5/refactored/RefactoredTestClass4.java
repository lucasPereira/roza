import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private SistemaBancario sisbank;

	private Banco bank;

	private Agencia agency;

	private Conta accWag;

	private Conta accBill;

	private Dinheiro dindin;

	@Before()
	public void setup() {
		sisbank = new SistemaBancario();
		bank = sisbank.criarBanco("Itau", Moeda.BRL);
		agency = bank.criarAgencia("12");
		accWag = agency.criarConta("Wagner");
		accBill = agency.criarConta("TioBill");
		dindin = new Dinheiro(Moeda.BRL, 500000, 0);
		sisbank.depositar(accBill, dindin);
	}

	@Test()
	public void bankCreation() {
		SistemaBancario sistema = new SistemaBancario();
		Banco bank = sistema.criarBanco("bradesco", Moeda.BRL);
		List<Banco> bancos = sistema.obterBancos();
		assertEquals(bancos.size(), 1);
		assertEquals(bank.obterNome(), "bradesco");
	}

	@Test()
	public void deposit() {
		try {
			Dinheiro currentAmount = accWag.calcularSaldo().obterQuantia();
			Integer centavos = currentAmount.obterQuantiaEmEscala();
			Dinheiro salario = new Dinheiro(Moeda.BRL, 5, 0);
			sisbank.depositar(accWag, salario);
			currentAmount = accWag.calcularSaldo().obterQuantia();
			centavos = currentAmount.obterQuantiaEmEscala() - centavos;
			assertEquals(new Integer(500), centavos);
		} catch (Exception e) {
		} finally {
			init();
		}
	}

	@Test()
	public void transfer() {
		try {
			Dinheiro currentAmountBill = accBill.calcularSaldo().obterQuantia();
			Dinheiro currentAmountWagner = accWag.calcularSaldo().obterQuantia();
			Integer centavosWagner = currentAmountWagner.obterQuantiaEmEscala();
			Integer centavosBill = currentAmountBill.obterQuantiaEmEscala();
			Dinheiro pagamento = new Dinheiro(Moeda.BRL, 500, 0);
			sisbank.transferir(accWag, accBill, pagamento);
			currentAmountBill = accBill.calcularSaldo().obterQuantia();
			currentAmountWagner = accWag.calcularSaldo().obterQuantia();
			Integer centavosWagnerAtual = currentAmountWagner.obterQuantiaEmEscala();
			Integer centavosBillAtual = currentAmountBill.obterQuantiaEmEscala();
			Integer diffWag = centavosWagner - centavosWagnerAtual;
			Integer diffBill = centavosBill - centavosBillAtual;
			Integer resultado = diffWag - diffBill;
			assertEquals(new Integer(0), resultado);
		} catch (Exception e) {
		} finally {
			init();
		}
	}

	@Test()
	public void withdraw() {
		try {
			Dinheiro currentAmount = accBill.calcularSaldo().obterQuantia();
			Integer centavos = currentAmount.obterQuantiaEmEscala();
			Dinheiro salario = new Dinheiro(Moeda.BRL, 500, 0);
			sisbank.depositar(accBill, salario);
			currentAmount = accBill.calcularSaldo().obterQuantia();
			centavos = currentAmount.obterQuantiaEmEscala() - centavos;
			assertEquals(new Integer(50000), centavos);
		} catch (Exception e) {
		} finally {
			init();
		}
	}
}
