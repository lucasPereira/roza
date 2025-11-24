import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void testeObterEmEscalaValorPositivo() {
		Dinheiro dezECinquenta = new Dinheiro(Moeda.BRL, 10, 50);
		Integer valorEmEscala = dezECinquenta.obterQuantiaEmEscala();
		assertEquals(1050, valorEmEscala.intValue());
	}
}
