import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void testCriarBanco() {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Banco bancoBradesco = sistemaBancario.criarBanco("Bradesco", Moeda.BRL);
		assertEquals("Bradesco", bancoBradesco.obterNome());
		assertEquals(Moeda.BRL, bancoBradesco.obterMoeda());
	}
}
