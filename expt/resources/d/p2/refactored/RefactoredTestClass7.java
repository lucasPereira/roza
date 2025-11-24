import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private SistemaBancario sistemaBancario;

	private Banco bb;

	private Agencia bbCentro;

	private Conta leandroConta;

	@Before()
	public void setup() {
		sistemaBancario = new SistemaBancario();
		bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
		bbCentro = bb.criarAgencia(TRINDADE);
		leandroConta = bbCentro.criarConta(LEANDRO);
	}

	@Test()
	public void operacaoSaque() {
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		sistemaBancario.depositar(leandroConta, dezReais);
		Operacao operacao = sistemaBancario.sacar(leandroConta, new Dinheiro(Moeda.BRL, 5, 0));
		assertEquals(EstadosDeOperacao.SUCESSO, operacao.obterEstado());
		assertEquals(new Dinheiro(Moeda.BRL, 5, 0).toString(), leandroConta.calcularSaldo().formatarSemSinal());
	}

	@Test()
	public void operacaoSaqueInsuficiente() {
		Dinheiro cincoReais = new Dinheiro(Moeda.BRL, 5, 0);
		sistemaBancario.depositar(leandroConta, cincoReais);
		Operacao operacao = sistemaBancario.sacar(leandroConta, new Dinheiro(Moeda.BRL, 10, 0));
		assertEquals(EstadosDeOperacao.SALDO_INSUFICIENTE, operacao.obterEstado());
		assertEquals(cincoReais.toString(), leandroConta.calcularSaldo().formatarSemSinal());
	}
}
