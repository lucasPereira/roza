import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void verificarFormatacaoComMoeda() {
		Dinheiro formatacaoSemMoeda = new Dinheiro(Moeda.BRL, 20, 20);
		assertEquals("20,20 BRL", formatacaoSemMoeda.formatado());
	}
}
