import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void testarQuantiaEmEscalaDezReaisCinquentaCentavos() {
		Dinheiro dezReaisCinquentaCentavos = new Dinheiro(Moeda.BRL, 10, 50);
		assertEquals(new Integer(1050), dezReaisCinquentaCentavos.obterQuantiaEmEscala());
	}
}
