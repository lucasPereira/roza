import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void umFuncionarioNaoPodeTerUmNomeVazio() {
		String nomeFuncionario = "";
		new Funcionario(nomeFuncionario);
	}
}
