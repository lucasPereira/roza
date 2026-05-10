import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void criarBancoNomeVazio() {
		SistemaBancario sistBanc = new SistemaBancario();
		Banco bb = sistBanc.criarBanco("", Moeda.BRL);
		fail("Não implementado o tratamento de exceção ao gerar banco com nome vazio");
	}
}
