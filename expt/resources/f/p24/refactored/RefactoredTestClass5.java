import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void umFuncionarioNaoPodeTerUmNomeNulo() {
		String nomeFuncionario = null;
		new Funcionario(nomeFuncionario);
	}
}
