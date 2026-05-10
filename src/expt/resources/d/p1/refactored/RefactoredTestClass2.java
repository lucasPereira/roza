import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void testObterMoeda() {
		Dinheiro dinheiro_banco = new Dinheiro(moeda_dinheiro, qtde_dinheiro, 0);
		Moeda moeda_local = dinheiro_banco.obterMoeda();
		assertEquals(moeda_dinheiro, moeda_local);
	}
}
