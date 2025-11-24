import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void criaBanco() {
		SistemaBancario sistemaBancario = TesteHelper.criaSistemabancario();
		String nomeDoBanco = new String("Banco Dahora");
		Moeda moedaDoBanco = Moeda.BRL;
		sistemaBancario.criarBanco(nomeDoBanco, moedaDoBanco);
		Banco banco = sistemaBancario.obterBancos().get(0);
		assertEquals(nomeDoBanco, banco.obterNome());
		assertEquals(moedaDoBanco, banco.obterMoeda());
	}
}
