import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void criarDinheiroFracionadoNegativo() {
		Dinheiro dindin = new Dinheiro(Moeda.BRL, 0, -10);
		String quantia = dindin.formatado();
		fail("Não implementado o tratamento de exceção ao gerar Dinheiro com fracionado negativo");
	}
}
