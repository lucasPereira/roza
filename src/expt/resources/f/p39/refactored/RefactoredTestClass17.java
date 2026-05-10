import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass17 {

	private String nomeFuncionario;

	@Before()
	public void setup() {
		nomeFuncionario = "José";
	}

	@Test()
	public void contratarFuncionarioJose() {
		empresaY.contratarFuncionario(nomeFuncionario);
		assertEquals(2, empresaY.pegarNumeroDeFuncionarios());
	}

	@Test()
	public void criarFuncionarioComNome() {
		assertEquals(nomeFuncionario, funcionario.pegarNome());
	}
}
