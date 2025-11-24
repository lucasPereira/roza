import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void testConsultarSaldo() {
		Conta conta = TestHelper.criaContaBradesco();
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 10, 0);
		SistemaBancario sistemaBancario = new SistemaBancario();
		sistemaBancario.depositar(conta, dinheiro);
		assertEquals("+10,00 BRL", conta.calcularSaldo().formatado());
	}
}
