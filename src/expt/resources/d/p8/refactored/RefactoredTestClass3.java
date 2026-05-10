import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void verificarQuantiaEmEscala() {
		Dinheiro vinteReaisEVinteCentavos = new Dinheiro(Moeda.BRL, 20, 20);
		assertEquals(new Integer(2020), vinteReaisEVinteCentavos.obterQuantiaEmEscala());
	}
}
