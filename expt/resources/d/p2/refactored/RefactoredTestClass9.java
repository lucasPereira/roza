import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	private SistemaBancario sistemaBancario;

	private Banco bb;

	private Agencia bbTrindade;

	private Conta contaLeandro;

	private Dinheiro dezReais;

	private Entrada deposito;

	private ValorMonetario zero;

	@Before()
	public void setup() {
		sistemaBancario = new SistemaBancario();
		bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
		bbTrindade = bb.criarAgencia(TRINDADE);
		contaLeandro = bbTrindade.criarConta(LEANDRO);
		dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		deposito = new Entrada(contaLeandro, dezReais);
		contaLeandro.adicionarTransacao(deposito);
		zero = new ValorMonetario(Moeda.BRL);
	}

	@Test()
	public void saldoAposDepositoSucessoErro() {
		assertNotEquals(zero.obterQuantia().formatado(), contaLeandro.calcularSaldo().obterQuantia().formatado());
	}

	@Test()
	public void saqueSucessoErro() {
		Dinheiro cincoReais = new Dinheiro(Moeda.BRL, 5, 0);
		Saida saque = new Saida(contaLeandro, cincoReais);
		contaLeandro.adicionarTransacao(saque);
		Dinheiro doisReais = new Dinheiro(Moeda.BRL, 2, 0);
		ValorMonetario dois = zero.somar(doisReais);
		assertNotEquals(zero.obterQuantia().formatado(), contaLeandro.calcularSaldo().obterQuantia().formatado());
	}
}
