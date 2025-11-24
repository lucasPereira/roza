import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void testeObtemDinheiroBanco() {
		Banco banco_universitario = TestBancoAgenciaHelper.CriaBancoUniversitario();
		Dinheiro dinheiro_banco = banco_universitario.obterDinheiro();
		Dinheiro dinheiro_banco_valor = new Dinheiro(Moeda.BRL, 100000, 0);
		assertEquals(dinheiro_banco_valor, dinheiro_banco);
	}
}
