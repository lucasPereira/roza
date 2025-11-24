import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void testeCriaBanco() {
		String nome_banco = "Universitario";
		Moeda moeda_banco = Moeda.BRL;
		Dinheiro dinheiro_banco = new Dinheiro(moeda_banco, 100000, 0);
		Banco banco_universitario = new Banco(nome_banco, moeda_banco, dinheiro_banco);
		assertEquals(nome_banco, banco_universitario.obterNome());
		assertEquals(moeda_banco, banco_universitario.obterMoeda());
		assertEquals(dinheiro_banco, banco_universitario.obterDinheiro());
	}
}
