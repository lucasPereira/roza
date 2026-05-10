import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass19 {

	private String nomeFuncionario;

	@Before()
	public void setup() {
		nomeFuncionario = "Luiz Fernando";
	}

	@Test()
	public void contratarFuncionarioComNome() {
		empresaBrasil.contratarFuncionario(nomeFuncionario);
		assertEquals(2, empresaBrasil.getNumeroDeFuncionarios());
	}

	@Test()
	public void criarFuncionarioComNome() {
		assertEquals(nomeFuncionario, funcionarioLuiz.getNome());
	}
}
