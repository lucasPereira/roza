import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void projetoComUmaOcorrencia() {
		Projeto projeto = new Projeto();
		Funcionario joao = new Funcionario();
		projeto.novaOcorrencia(joao);
		assertEquals(1, projeto.obtemListaDeOcorrencias().size());
	}
}
