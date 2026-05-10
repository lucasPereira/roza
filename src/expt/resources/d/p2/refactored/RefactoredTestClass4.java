import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void operacaoSaqueInsuficiente() {
		Conta leandroConta = this.novaContaComQuatroReaisDeSaldo();
		Dinheiro cincoReais = new Dinheiro(Moeda.BRL, 5, 0);
		Operacao operacao = this.getSistemaBancario().sacar(leandroConta, cincoReais);
		assertEquals(EstadosDeOperacao.SALDO_INSUFICIENTE, operacao.obterEstado());
		assertEquals(new Dinheiro(Moeda.BRL, 4, 0).toString(), leandroConta.calcularSaldo().formatarSemSinal());
	}
}
